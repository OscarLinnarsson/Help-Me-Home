package Game;
import java.awt.Color;
import java.util.ArrayList;

import GUI.Animation;
import GUI.Draw;
import Helpers.Const;
import Helpers.FileManager;
import Helpers.helpFunctions;
import Main.Boot;
import Main.Game;

public class Particle {
	
	private boolean staticPos = false;
	private double r = 32;
	private double Fc = 0.95;
	
	// i = initial
	// f = final
	private double m = 7;
	private double xRi = 0;
	private double xRf = 0;
	private double xVi = 0;
	private double xVf = 0;
	private double yRi = 0;
	private double yRf = 0;
	private double yVi = 0;
	private double yVf = 0;
	
	private ArrayList<Vector> f;
	private Vector fNet;
	
	private Animation animation;
	
	public Particle (int xPos, int yPos, double mass) {
		xRi = xPos;
		yRi = yPos;
		m = mass;
		f = new ArrayList<Vector>();
		animation = new Animation(
						FileManager.loadParticleImgArr(), 2, 10
					);
	}
	
	public void setStaticPos (boolean isStatic) {
		staticPos = isStatic;
	}
	
	public void update (double dT) {
		if (!staticPos) {
			calcFNet();
			calcSpeed(dT);
			calcNewPos(dT);
			xRi = xRf;
			yRi = yRf;
			xVi = xVf;
			yVi = yVf;
		}
	}
	
	private void calcFNet () {
		double tmpX = 0;
		double tmpY = 0;
		// f is an array of vectors
		for (int i = 0; i < f.size(); i++) {
			tmpX = tmpX + f.get(i).getX();
			tmpY = tmpY + f.get(i).getY();
		}
		fNet = new Vector(tmpX, tmpY);
		f.clear();
	}
	
	private void calcSpeed (double dT) {
		// Vf = Vi + (a * dT)
		// To simulate energy loss in the system without 
		// calculating it, Vi + (a * dT) is multiplied with
		// a percentage conservative force constant
		xVf = (xVi + (fNet.getX()/m) * dT) * Fc;
		yVf = (yVi + (fNet.getY()/m) * dT) * Fc;
	}
	
	private boolean calcNewPos (double dT) {
		// Assuming constant acc. the new position
		// would be calculated as
		// xRf = xRi + ((xVi + xVf) / 2) / dT
		// since dT is very small we decided to approximate 
		// this, as seen bellow, as this greatly reduces the 
		// number of calculations needed in every update.
		xRf = xRi + xVf * dT;
		yRf = yRi + yVf * dT;
		
		// Code to simulate normal force and friction
		// As the normal force isn't actually calculated
		// friction won't be accurate
		boolean colDetected = false;
		if (Game.checkGroundCol((int)xRf, (int)yRf)) {
			//MOVE UP
			if (helpFunctions.collisionColorD((int)xRf, (int)yRf, 
					Game.getColMap()) == Const.RED) {
				colDetected = true;
				yRf = helpFunctions.getNewYPos((int)xRf, (int)yRf, -1, 
						Game.getColMap());
				yVf = 0;
				if (Math.abs(xRf-xRi) > Const.fricThreshold) {
					xVf = xVf * Const.fricLoss;
					xRf = xRi + xVf * dT;
				} else {
					xVf = 0;
					xRf = xRi;
				}
			} 
			//MOVE RIGHT
			else if (helpFunctions.collisionColorD((int)xRf, (int)yRf, 
					Game.getColMap()) == Const.BLUE) {
				colDetected = true;
				xRf = helpFunctions.getNewXPos((int)xRf, (int)yRf, 1, 
						Game.getColMap());
				xVf = 0;
				if (Math.abs(xRf-xRi) > Const.fricThreshold) {
					yVf = yVf * Const.fricLoss;
					yRf = yRi + yVf * dT;
				} else {
					yVf = 0;
					yRf = yRi;
				}
			}
			//MOVE DOWN
			else if (helpFunctions.collisionColorD((int)xRf, (int)yRf, 
					Game.getColMap()) == Const.GREEN) {
				colDetected = true;
				yRf = helpFunctions.getNewYPos((int)xRf, (int)yRf, 1, 
						Game.getColMap());
				yVf = 0;
				if (Math.abs(xRf-xRi) > Const.fricThreshold) {
					xVf = xVf * Const.fricLoss;
					xRf = xRi + xVf * dT;
				} else {
					xVf = 0;
					xRf = xRi;
				}
			}
			//MOVE LEFT
			else if (helpFunctions.collisionColorD((int)xRf, (int)yRf, 
					Game.getColMap()) == Const.YELLOW) {
				colDetected = true;
				xRf = helpFunctions.getNewXPos((int)xRf, (int)yRf, -1, 
						Game.getColMap());
				xVf = 0;
				if (Math.abs(xRf-xRi) > Const.fricThreshold) {
					yVf = yVf * Const.fricLoss;
					yRf = yRi + yVf * dT;
				} else {
					yVf = 0;
					yRf = yRi;
				}
			}
			//Caused stack overflow
			/*if (Game.checkGroundCol((int)xRf, (int)yRf)) {
				colDetected = calcNewPos(dT);
			}*/
		} 
		
		if(xRf >= Boot.getCanvasWidth())
			xRf = Boot.getCanvasWidth();
		else if (xRf <= 0)
			xRf = 0;
		if(yRf >= Boot.getCanvasHeight())
			yRf = Boot.getCanvasHeight();
		else if (yRf <= 0)
			yRf = 0;
		
		return !colDetected;
		//return Game.checkGroundCol((int)xRf, (int)yRf)? false : true; 
		//if no collision true is returned
	}
	
	public void addForce (Vector v) {
		f.add(v);
	}
	
	public double getXPos () {
		return xRi;
	}
	
	public double getYPos () {
		return yRi;
	}
	
	public void render() {
		paintMeLikeOneOfYourFrenchGirls();
	}
	
	private void paintMeLikeOneOfYourFrenchGirls () {
		Draw.drawImg((int)(xRi-r), (int)(yRi-r), animation.getImage());
	}
	
}
