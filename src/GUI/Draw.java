package GUI;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;

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
		
	public static void drawBackground (BufferedImage img) {
		drawImg(0, 0, img);
	}
	
	public static void drawImg (int x, int y, BufferedImage img) {
		AffineTransform at=new AffineTransform();
		BufferedImageOp bio;
		bio=new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(img, bio, x, y);
	}
	
	public static void drawTextM (int x, int y, String text) {
		drawText(x, y, text, 50);
	}
	
	public static void drawTextL (int x, int y, String text) {
		drawText(x, y, text, 100);
	}
	
	private static void drawText (int x, int y, String text, int size) {
		g.setFont(new Font("TimesRoman", Font.BOLD, size)); 
		g.setColor(Color.black);
		g.drawString(text, x, y);
	}
	
	public static void drawLine (int x1, int y1, int x2, int y2, Color c, int thickness) {
		g.setColor(c);
		g.setStroke(new BasicStroke(thickness));
		g.drawLine(x1, y1, x2, y2);
	}
	
	public static void drawCircle (int x, int y, int r, Color c) {
		g.setColor(c);
		g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
	}
	
	public static void drawButtons (ArrayList<Button> buttons) {
		for (Button b : buttons) {
			b.draw();
		}
	}
	
	public static void showFrame () {
		bs.show();
	}
	
}
