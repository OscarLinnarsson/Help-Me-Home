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
	private boolean isVisible = true;
	
	public Button (BufferedImage _img) {
		img = _img;
		setX(0);
		setY(0);
		onClick = null;
	}
	
	public Button (String buttonName) {
		initialize(buttonName, 0, 0, null);
	}
	
	public Button (String buttonName, int _x, int _y) {
		initialize(buttonName, _x, _y, null);
	}
	
	public Button (String buttonName, int _x, int _y, Runnable run) {
		initialize(buttonName, _x, _y, run);
	}
	
	private void initialize (String buttonName, int _x, int _y, Runnable run) {
		img = FileManager.loadImage("buttons/" + buttonName);
		setX(_x);
		setY(_y);
		onClick = run;
	}
	
	public void setActionCommand (Runnable run) {
		onClick = run;
	}
	
	public void setVisible (boolean showButton) {
		isVisible = showButton;
	}
	
	public void draw () {
		if (isVisible) {
			Draw.drawImg(x, y, img);
		}
	}
	
	public boolean checkClick (int _x, int _y) {
		if (isVisible) {
			if (x <= _x  && _x <= rightBound &&
				y <= _y && _y <= bottomBound) {
				if (onClick != null) {
					onClick.run();
				} else {
					System.out.println("No action Command has been "
										+ "set for this button.");
				}
				return true;
			}
		}
		return false;
	}
	
	public void setX (int _x) {
		x = _x;
		rightBound = x + img.getWidth();
	}
	
	public void setY (int _y) {
		y = _y;
		bottomBound = y + img.getHeight();
	}
	
	
}
