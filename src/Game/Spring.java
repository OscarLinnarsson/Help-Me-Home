package Game;
import java.awt.Color;

import GUI.Draw;
import Main.Game;

public class Spring {

	private Particle p1;
	private Particle p2;
	private double restL;
	private double currentL;
	private double deltaL;
	private int snappLength;
	private double k;
	private double f;
	
	public Spring (Particle p1, Particle p2, double k) {
		double restL;
		int x1 = (int) p1.getXPos();
		int y1 = (int) p1.getYPos();
		int x2 = (int) p2.getXPos();
		int y2 = (int) p2.getYPos();
		int deltaX = x1 - x2;
		int deltaY = y1 - y2;
		restL = Math.pow(Math.pow(deltaX, 2) + Math.pow(deltaY, 2), 0.5);
		initialize(p1, p2, restL, k);
	}
	
	public Spring (Particle p1, Particle p2, double restL, double k) {
		initialize(p1, p2, restL, k);
	}
	
	private void initialize (Particle p1, Particle p2, double restL, double k) {
		this.p1 = p1;
		this.p2 = p2;
		this.restL = restL;
		snappLength = (int) (restL * 1.2);
		snappLength = snappLength > 120 ? 120 : snappLength;
		this.k = k;
	}
	
	public void calcF () {// F = -k * deltaS
		double dX = p1.getXPos()-p2.getXPos();
		double dY = p1.getYPos()-p2.getYPos();
		currentL = Math.pow((Math.pow(dX, 2)+Math.pow(dY, 2)), 0.5);
		deltaL = currentL - restL;
		f = deltaL * -k;
		if (deltaL > snappLength || deltaL < -snappLength) {
			Game.removeSpring(this);
		} else {
			double angle = Math.atan2(dY, dX);
			double xF = f * Math.cos(angle);
			double yF = f * Math.sin(angle);
			//Force xF and yF is with regard to particle p1
			//particle p2 will be affected by the opposite force
			p1.addForce(new Vector(xF, yF));
			p2.addForce(new Vector(-xF, -yF));
		}
		
	}
	
	public void render() {
		int thickness = (int) ( 10 - ((deltaL/snappLength) * 10) );
		thickness = thickness < 1 ? 1 : thickness;
		Draw.drawLine(	(int)p1.getXPos(), (int)p1.getYPos(), 
						(int)p2.getXPos(), (int)p2.getYPos(), 
						Color.BLACK, thickness);
	}
}
