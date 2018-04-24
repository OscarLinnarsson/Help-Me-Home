package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import Main.Boot;

public class Draw extends Canvas {

	private static Dimension dim;
	private static BufferStrategy bs; 
	private static Graphics2D g;
	
	public Draw () {
		dim = new Dimension(1920, 1080);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
	}
	
	public void initialize () {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			bs = getBufferStrategy();
		}
		g = (Graphics2D) bs.getDrawGraphics();
	}
	
	public static void drawBackground () {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Boot.getCanvasWidth(), Boot.getCanvasHeight());
	}
	
	/*
	public static void drawBackground () {
		
	}
	*/
	
	public static void drawLine (int x1, int y1, int x2, int y2, Color c) {
		g.setColor(c);
		g.drawLine(x1, y1, x2, y2);
	}
	
	public static void drawCircle (int x, int y, int r, Color c) {
		g.setColor(c);
		g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
	}
	
	/*
	public static void drawImg () {
		
	}
	*/
	
	public static void showFrame () {
		bs.show();
	}
	
}