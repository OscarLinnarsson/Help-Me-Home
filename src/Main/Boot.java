package Main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import GUI.Draw;
import Helpers.MouseInput;
//HEJ GUSTAV HAR
public class Boot {

	private static double lastUpdate;
	private static Navigation process;
	private static JFrame frame;
	private static Draw canvas = new Draw();
	private static Game game = new Game();
	private static MainMenu mainMenu = new MainMenu();
	private static Settings settings = new Settings();
	private static Credits credits = new Credits();
	
	public static void main (String[] args) {
		MainMenu.initialize();
		Settings.initialize();
		Credits.initialize();
		
		frame = new JFrame("Help Me Home");
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		canvas.initialize();
		canvas.addMouseListener(new MouseInput(frame));
		
		lastUpdate = System.currentTimeMillis();
		goToGame();
		
		AbstractAction doOneStep = new AbstractAction () {
			@Override
			public void actionPerformed(ActionEvent e) {
				double currentTime = System.currentTimeMillis();
				update((currentTime - lastUpdate) / 1000.0);
				lastUpdate = currentTime;
				repaint();
			}
		};
		Timer timer = new Timer(17, doOneStep);
		timer.setCoalesce(true);
		timer.start();
		
		frame.setVisible(true);
	}
	
	public static void leftClick (int x, int y) {
		System.out.println("x,y : " + x + "," + y);
		process.leftClick(x, y);
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
	
	public static void goToMainMenu () {
		process = mainMenu;
	}
	
	public static void goToSettings () {
		process = settings;
	}
	
	public static void goToCredits () {
		process = credits;
	}
	
	public static void goToGame () {
		process = game;
	}
	
}
