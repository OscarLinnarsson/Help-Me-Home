package Helpers;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Game.GameMap;

public class helpFunctions {
	


	private static GameMap map = new GameMap("1 Backyard");
	private static BufferedImage colMap = map.getCollisionImage();
	
	
	public helpFunctions() {
	}
	
	/**
	 * 
	 * @param x - Your x value that you want to have the color of
	 * @param y - Your y value that you want to have the color of
	 * @return - The color as an array in RGB, where 0 is red, 1 is green and 2 is blue
	 */
	public int[] collisionColor (int x, int y) {
		int[] color = new int[3];
		Color c = new Color(colMap.getRGB(x, y));
		color[0] = c.getRed();
		color[1] = c.getGreen();
		color[2] = c.getBlue();
		return color;
	}
	
	public static double collisionColorD (int x, int y) {
		double color = 0.0000000001;
		Color c = new Color(colMap.getRGB(x, y));
		color += c.getRed()*0.001;
		color += c.getGreen()*0.000001;
		color += c.getBlue()*0.000000001;
		return color;
	}

}
