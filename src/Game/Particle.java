package Game;
import java.awt.Color;
import java.util.ArrayList;

import GUI.Draw;
import Main.Game;

public class Particle {
	
	private boolean staticPos = false;
	private double r = 10;
	private Color color = new Color(0,255,0);
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
	private Vector fRes;
	
	public Particle (int xPos, int yPos, double mass) {
		xRi = xPos;
		yRi = yPos;
		m = mass;
		f = new ArrayList<Vector>();
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
		
		if(xRf >= Boot.getCanvasWidth())
			xRf = Boot.getCanvasWidth();
		else if (xRf <= 0)
			xRf = 0;
		if(yRf >= Boot.getCanvasHeight())
			yRf = Boot.getCanvasHeight();
		else if (yRf <= 0)
			yRf = 0;
		
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
		Draw.drawCircle((int)xRi, (int)yRi, (int)r, color);
	}
	
	/*public void col() {
		help = new helpFunctions();
		double color = 0;
		color = help.collisionColorD((int) xRi, (int) yRi);
		
		//System.out.println(color);
		
		if(color == Const.RED) {
			addForce(new Vector(0, -fRes.getY()));			
		}		
	}*/
	
}
