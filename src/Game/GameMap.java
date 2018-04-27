package Game;

import java.awt.image.BufferedImage;

import Helpers.FileManager;

public class GameMap {

	private BufferedImage colMap;
	private BufferedImage shownMap;
	
	public GameMap (String mapName) {
		shownMap = FileManager.loadImage("maps/" + mapName + "-u");
		colMap = FileManager.loadImage("maps/" + mapName + "-c");
	}
	
	public BufferedImage getMapImage () {
		return shownMap;
	}
	
	// Ment to be used for debuging to easier understand 
	// how the current ground-/wall-collision detection 
	// works and what might get wrong 
	public BufferedImage getCollisionImage () {
		return colMap;
	}
	
}
