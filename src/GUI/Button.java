package GUI;

import java.awt.image.BufferedImage;

import Helpers.FileManager;

public class Button {

	private int x;
	private int rightBound;
	private int y;
	private int bottomBound;
	private BufferedImage img;
	private Runnable onClick;
	
	public Button (String buttonName, int _x, int _y) {
		initialize(buttonName, _x, _y, null);
	}
	
	public Button (String buttonName, int _x, int _y, Runnable run) {
		initialize(buttonName, _x, _y, run);
	}
	
	private void initialize (String buttonName, int _x, int _y, Runnable run) {
		x = _x;
		y = _y;
		img = FileManager.loadImage("buttons/" + buttonName);
		rightBound = x + img.getWidth();
		bottomBound = y + img.getHeight();
		onClick = run;
	}
	
	public void setActionCommand (Runnable run) {
		onClick = run;
	}
	
	public void draw () {
		Draw.drawImg(x, y, img);
	}
	
	public boolean checkClick (int _x, int _y) {
		if (	x <= _x  && _x <= rightBound &&
				y <= _y && _y <= bottomBound) {
			if (onClick != null) {
				onClick.run();
			} else {
				System.out.println("No action Command has been "
									+ "set for this button.");
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
