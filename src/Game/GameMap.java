package Game;

import java.awt.image.BufferedImage;

import GUI.Button;
import Helpers.FileManager;

public class GameMap {

	//private boolean unlocked = true;
	private Button button;
	private BufferedImage thumbnail;
	private BufferedImage colMap; //collisionMap
	private BufferedImage shownMap; //userMap
	
	public GameMap (String mapName) {
		thumbnail = FileManager.loadImage("maps/" + mapName + "-t");
		shownMap = FileManager.loadImage("maps/" + mapName + "-u");
		colMap = FileManager.loadImage("maps/" + mapName + "-c");
		button = new Button(thumbnail);
	}
	
	public Button getButton () {
		return button;
	}
	
	public BufferedImage getMapImage () {
		return shownMap;
	}
	
	// Ment to be used for debuging to easier understand 
	// how the current ground-/wall-collision detection 
	// works and what might go wrong 
	public BufferedImage getCollisionImage () {
		return colMap;
	}
	
}
