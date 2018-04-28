package Main;

import java.util.ArrayList;

import GUI.Button;

public abstract class Navigation {

	protected ArrayList<Button> buttons = new ArrayList<Button>();
	
	public ArrayList<Button> getButtons () {
		return buttons;
	}
	
	public abstract void update (double dT);
	
	public abstract void render ();
	
	public abstract void leftClick (int x, int y);
	
	public abstract void rightClick (int x, int y);
	
}
