package Game;

public class Vector {

	private double xSize = 0;
	private double ySize = 0;
	
	public Vector (double size, int angle) {
		xSize = size * Math.cos(angle);
		ySize = size * Math.sin(angle);
	}
	
	public Vector (double x, double y) {
		xSize = x;
		ySize = y;
	}
	
	public double getX () {
		return xSize;
	}
	
	public double getY () {
		return ySize;
	}
}
