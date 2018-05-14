package Game;
import java.awt.Color;
import java.util.ArrayList;

import GUI.Animation;
import GUI.Draw;
import Helpers.Const;
import Helpers.FileManager;
import Main.Game;

public class Particle {
	
	private boolean staticPos = false;
	private double r = 32;
	private Color color = new Color(0,255,0);
	private double Fc = 0.8;
	
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
	private Vector fRes;
	
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
		if (staticPos) {
			color = new Color(255, 0, 0);
		}
	}
	
	public void update (double dT) {
		if (!staticPos) {
			calcFRes();
			calcSpeed(dT);
			if (calcNewPos(dT)) { // no collision
				xRi = xRf;
				yRi = yRf;
				xVi = xVf;
				yVi = yVf;
			} else {
				xVi = 0;
				yVi = 0;
			}
		}
	}
	
	private void calcFRes () {
		double tmpX = 0;
		double tmpY = 0;
		for (int i = 0; i < f.size(); i++) {
			tmpX = tmpX + f.get(i).getX();
			tmpY = tmpY + f.get(i).getY();
		}
		fRes = new Vector(tmpX, tmpY);
		f.clear();
	}
	
	private void calcSpeed (double dT) {
		xVf = (xVi + (fRes.getX()/m) * dT) * Fc;
		yVf = (yVi + (fRes.getY()/m) * dT) * Fc;
	}
	
	private boolean calcNewPos (double dT) {
		xRf = xRi + xVf * dT;
		yRf = yRi + yVf * dT;
		return Game.checkGroundCol((int)xRf, (int)yRf)? false : true; 
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
		if (Const.showFaces) {
			int radius = 32;
			Draw.drawImg((int)xRi-radius, (int)yRi-radius, animation.getImage());
		} else {
			Draw.drawCircle((int)xRi, (int)yRi, (int)r, color);
		}
		
	}
	
}
