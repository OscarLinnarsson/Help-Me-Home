package Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GUI.Button;
import GUI.Draw;
import Game.GameMap;
import Helpers.FileManager;

public class SelectMap extends Navigation {

	private static BufferedImage backgroundImg;
	private static int pageNum = 0;
	private static ArrayList<GameMap> maps;
	private static Button next;
	private static Button previous;
	
	
	
	public SelectMap () {
		backgroundImg = FileManager.loadImage("BackgroundLevels");
		next = new Button("next", 1730, 500, new Runnable() {
			@Override
			public void run() {
				if ((pageNum+1)*4 < maps.size()) {
					showPage(pageNum+1);
				}
			}
		});
		previous = new Button("previous", 25, 500, new Runnable() {
			@Override
			public void run() {
				if (pageNum > 0) {
					showPage(pageNum-1);
				}
			}
		});
		buttons.add(next);
		buttons.add(previous);
		maps = new ArrayList<GameMap>();
		for (String mapName : FileManager.getAllMapNames()) {
			addMap(mapName);
		}
		showPage(0);
		
	}
	
	private void showPage (int pageToShow) {
		pageNum = pageToShow;
		for (GameMap m : maps) {
			m.getButton().setVisible(false);
		}
		for (int i = pageToShow*4; 
				i < pageToShow*4+4 && i < maps.size(); 
				i++) {
			maps.get(i).getButton().setVisible(true);
		}
	}
	
	private void addMap (String mapName) {
		GameMap map = new GameMap(mapName);
		map.getButton().setActionCommand(new Runnable () {
			@Override
			public void run() {
				Boot.goToGame(map);
			}
		});
		maps.add(map);
		buttons.add(map.getButton());
		int x = 0;
		int y = 0;
		if (maps.size() % 4 == 1) {
			x = 530;
			y = 250;
		} else if (maps.size() % 4 == 2) {
			x = 1030;
			y = 600;
		} else if (maps.size() % 4 == 3) {
			x = 1030;
			y = 250;
		} else if (maps.size() % 4 == 0) {
			x = 0;
			y = 0;
		}
		map.getButton().setX(x);
		map.getButton().setY(y);
	}
	
	@Override
	public void update(double dT) {
		
	}

	@Override
	public void render() {
		//Draw.drawBackground();
		Draw.drawBackground(backgroundImg);
		Draw.drawButtons(buttons);
	}

	@Override
	public void leftClick(int x, int y) {
		
	}

	@Override
	public void rightClick(int x, int y) {
		
	}

}
