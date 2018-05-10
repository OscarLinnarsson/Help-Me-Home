package Main;

import GUI.Button;
import GUI.Draw;

public class MainMenu extends Navigation {
	
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
		Draw.drawBackground();
		Draw.drawButtons(buttons);
	}
	
	public void leftClick (int x, int y) {
		
	}
	
	public void rightClick (int x, int y) {
		
	}
	
}
