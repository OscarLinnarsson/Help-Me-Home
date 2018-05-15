package Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import GUI.Button;
import Main.Game;

public class FinishLine {

	private ArrayList<Integer> xArea = new ArrayList<Integer>();
	private ArrayList<Integer> yArea = new ArrayList<Integer>();

	private int finishColor = new Color(0,0,0).getRGB();
	
	public FinishLine(int x, int y) {
		xArea.add(x);
		yArea.add(y);
	}

	public void startTimer() {

		Timer timer = new Timer();

		int delay = 10000; // milliseconds

		timer.schedule(new TimerTask() {

			public void run() {
				checkFinishLine();
			}

		}, delay);

		timer.cancel();

	}

	// Look at the RGB value for each particle
	public void checkFinishLine() {

//		ArrayList<Particle> particles = new ArrayList<Particle>();

		for (int x = 0; x < xArea.size(); x++) {
			for (int y = 0; y < yArea.size(); y++) {

//				particles = Game.particles;

//				for (Particle p : particles) {
				for (Particle p : Game.particles) {
					int color = Game.map.getCollisionImage().getRGB((int)p.getXPos(), (int)p.getYPos());

					if (finishColor == color) {

						// G�r en bild f�r n�r man vinner, d�p den till completed
						// BufferedImage eller n�gon form av textruta!
						
						// g�r en knapp f�r mainmenu
						new Button("mainmenu", 1000, 1000); // justera s� den hamnar i mitten
//						break;
					}
				}
			}
		}
	}

}
