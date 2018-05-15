package Main;

import java.awt.image.BufferedImage;

import GUI.Button;
import GUI.Draw;
import Helpers.FileManager;

public class MainMenu extends Navigation {
	
	BufferedImage menu;

	public MainMenu () {
		this.menu = FileManager.loadImage("MainMenu");	
	}
	
	public void initialize () {
		buttons.add(new Button("Play", 1280, 580, new Runnable() {
			@Override
			public void run() {
				Boot.goToGame();
			}
		}));
		/*buttons.add(new Button("Credits", 600, 450, new Runnable() {
			@Override
			public void run() {
				Boot.goToGame();
			}
		}));*/
		buttons.add(new Button("Quit", 1280, 830, new Runnable() {
			@Override
			public void run() {
				System.exit(0);
			}
		}));
	}
	
	public void update (double dT) {
		
	}
	
	public void render () {
		MainMenu background = new MainMenu();
		Draw.drawBackground(background.menu);
		Draw.drawButtons(buttons);
	}
	
	public void leftClick (int x, int y) {
		
	}
	
	public void rightClick (int x, int y) {
		
	}
	
}
