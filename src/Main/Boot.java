package Main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import GUI.Button;
import GUI.Draw;
import Helpers.MouseInput;

public class Boot {

	private static double lastUpdate;
	private static Navigation process;
	private static JFrame frame;
	private static Draw canvas = new Draw();
	private static Game game = new Game("1 Backyard");
	private static MainMenu mainMenu = new MainMenu();
	private static Settings settings = new Settings();
	private static Credits credits = new Credits();
	private static Timer timer;
	
	public static void main (String[] args) {
		mainMenu.initialize();
		Settings.initialize();
		Credits.initialize();
		
		frame = new JFrame("Help Me Home");
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		canvas.initialize();
		canvas.addMouseListener(new MouseInput(frame));
		canvas.addMouseMotionListener(new MouseInput(frame));
		
		lastUpdate = System.currentTimeMillis();
		goToMainMenu();
		
		AbstractAction doOneStep = new AbstractAction () {
			@Override
			public void actionPerformed(ActionEvent e) {
				double currentTime = System.currentTimeMillis();
				update((currentTime - lastUpdate) / 1000.0);
				lastUpdate = currentTime;
				repaint();
			}
		};
		timer = new Timer(17, doOneStep);
		timer.setCoalesce(true);
		timer.start();
		
		frame.setVisible(true);
	}
	
	public static void leftClick (int x, int y) {
		boolean buttonClicked = false;
		for (Button b : process.getButtons()) {
			if (b.checkClick(x, y)) {
				buttonClicked = true;
				break;
			}
		}
		if (!buttonClicked) {
			process.leftClick(x, y);
		}
	}
	
	public static void render(){
		game.render();
	}
	
	public static void rightClick (int x, int y) {
		process.rightClick(x, y);
	}
	
	private static void update (double dT) {
		process.update(dT);
	}
	
	private static void repaint () {
		process.render();
		Draw.showFrame();
	}
	
	public static JFrame getFrame () {
		return frame;
	}
	
	public static int getCanvasWidth () {
		return canvas.getWidth();
	}
	
	public static int getCanvasHeight () {
		return canvas.getHeight();
	}
	
	public static boolean goToMainMenu () {
		process = mainMenu;
		return true;
	}
	
	public static void goToSettings () {
		process = settings;
	}
	
	public static void goToCredits () {
		process = credits;
	}
	
	public static void goToGame () {
		//game = new Game("1 Backyard"); 
		//game = new Game("NextLevel");
		game = new Game("WaterPassage");
		process = game;
	}
	
}