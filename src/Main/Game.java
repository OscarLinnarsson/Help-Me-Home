package Main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import GUI.Button;
import GUI.Draw;
import Game.GameMap;
import Game.Particle;
import Game.Spring;
import Game.Vector;
import Helpers.Const;
import Helpers.FileManager;
import Helpers.helpFunctions;

public class Game extends Navigation {

	private static int startColor = new Color(255, 0, 255).getRGB();
	// private static int finishColor = new Color(0, 0, 0).getRGB();

	private static boolean win = false;
	private static int winSec;

	public static ArrayList<Particle> particles;
	private static ArrayList<Particle> particlesToAdd;
	private static ArrayList<Spring> springs;
	private static ArrayList<Spring> brokenSprings;
	public static double gOnP = 500;

	private static GameMap map;
	private static int ballsLeft;

	private static Button pause;
	private static Button play;
	private static Button restart;
	private static Button goBack;
	// private static Button options;
	private static boolean isPaused = false;
	private static BufferedImage pauseIndicator;
	private static boolean gameOver = false;

	public Game(String mapName) {
		map = new GameMap(mapName);
		pauseIndicator = FileManager.loadImage("PauseIndicator");
		createButtons();
		buttons.add(pause);
		buttons.add(play);
		buttons.add(restart);
		buttons.add(goBack);
		restart();
	}

	private static void createButtons() {
		pause = new Button("Pause", 1750, 30, new Runnable() {
			@Override
			public void run() {
				isPaused = true;
			}
		});
		play = new Button("unPause", 1550, 30, new Runnable() {
			@Override
			public void run() {
				isPaused = false;
			}
		});
		restart = new Button("Restart", 1350, 30, new Runnable() {
			@Override
			public void run() {
				restart();
			}
		});
		goBack = new Button("Exit", 1150, 30, new Runnable() {
			@Override
			public void run() {
				Boot.goToMainMenu();
			}
		});
	}

	private static void restart() {
		win = false;
		winSec = 0;
		ballsLeft = Const.nbrOfBalls;
		particles = new ArrayList<Particle>();
		particlesToAdd = new ArrayList<Particle>();
		springs = new ArrayList<Spring>();
		brokenSprings = new ArrayList<Spring>();
		gameOver = false;
		pause.setVisible(true);
		play.setVisible(true);
		startpunkt();
	}

	private static void startpunkt() { // vi kan byta namn p� metoden om vi vill
		int rgb;
		for (int h = 0; h < map.getCollisionImage().getHeight(); h++) {
			for (int w = 0; w < map.getCollisionImage().getWidth(); w++) {
				rgb = map.getCollisionImage().getRGB(w, h);
				if (rgb == startColor) {
					addParticle(w, h, false);
					break;
				}
			}
		}
	}

	private static void addParticle(int x, int y, boolean isStatic) {
		// check conditions for adding
		// a particle here
		if(helpFunctions.collisionColorD(x, y,
				map.getCollisionImage()) == Const.WHITE) {
			
			Particle newP = new Particle(x, y, 1);
			newP.setStaticPos(isStatic);
			particlesToAdd.add(newP);
		}
	}

	public boolean ballsLeft() {
		return ballsLeft > 0 ? true : false;
	}

	private static void addSpring(Particle p1, Particle p2) {
		springs.add(new Spring(p1, p2, 40));
	}

	public static void removeSpring(Spring sp) {
		brokenSprings.add(sp);
	}

	private void addParticles() {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		HashMap<Integer, Particle> map = new HashMap<>();
		int x;
		int y;
		boolean connected;
		int diffX;
		int diffY;
		int dist;
		int maxConections;
		for (Particle newP : particlesToAdd) {
			connected = false;
			x = (int) newP.getXPos();
			y = (int) newP.getYPos();
			diffX = 0;
			diffY = 0;
			dist = 0;
			for (Particle p : particles) {
				diffX = Math.abs((int) p.getXPos() - x);
				diffY = Math.abs((int) p.getYPos() - y);
				dist = (int) Math.pow(Math.pow(diffX, 2) + Math.pow(diffY, 2), 0.5);
				distances.add(dist);
				map.put(dist, p);
			}
			Collections.sort(distances);
			maxConections = distances.size() > Const.maxIniSpr ? Const.maxIniSpr : distances.size();
			for (int i = 0; i < maxConections; i++) {
				if (distances.get(i) < 240 && distances.get(i) > 20 && i < distances.size()) {
					addSpring(newP, map.get(distances.get(i)));
					connected = true;
				}
			}
			if (connected || particles.size() == 0) {
				particles.add(newP);
				ballsLeft--;
			}
		}
		particlesToAdd.clear();
	}

	private void removeBrokenSprings() {
		for (Spring s : brokenSprings) {
			springs.remove(s);
		}
		brokenSprings.clear();
	}

	public static boolean checkGroundCol(int x, int y) {
		double color = 0;
		if (x == Boot.getCanvasWidth())
			x--;
		if (y == Boot.getCanvasHeight())
			y--;
		color = helpFunctions.collisionColorD(x, y, map.getCollisionImage());

		return color != Const.WHITE && color != Const.BLACK && color != Const.ORANGE;
	}

	public void update(double dT) {
		if (!isPaused) {
			addParticles();
			removeBrokenSprings();

			for (Particle p : particles) {
				p.addForce(new Vector(0, gOnP));
				if (helpFunctions.collisionColorD((int) p.getXPos(), (int) p.getYPos(),
						map.getCollisionImage()) == Const.ORANGE && !gameOver) {
					gameOver = true;
					pause.setVisible(false);
					play.setVisible(false);
				}
				if (helpFunctions.collisionColorD((int) p.getXPos(), (int) p.getYPos(),
						map.getCollisionImage()) == Const.BLACK) {

					if (winSec == 10) {
						win = true;
						pause.setVisible(false);
						play.setVisible(false);
					}
					winSec++;
				}
			}
			for (Spring sp : springs) {
				sp.calcF();
			}
			for (Particle p : particles) {
				p.update(dT);
			}
		}
	}

	public void render() {
		Draw.drawImg(0, 0, map.getMapImage());
		// Draw.drawImg(0, 0, map.getCollisionImage());
		Draw.drawButtons(buttons);
		Draw.drawTextM(20, 50, "Balls left: " + ballsLeft);
		for (Spring sp : springs) {
			sp.render();
		}
		for (Particle p : particles) {
			p.render();

		}
		
		if (win == true) {
			Draw.drawTextL(750, 500, "You won!");
			Draw.drawTextM(765, 575, "You used: " + Integer.toString(Const.nbrOfBalls-ballsLeft) + " balls");
		} else if (gameOver == true) {
			Draw.drawTextL(750, 500, "Game over");
		} else if (isPaused) {
			Draw.drawImg(850, 350, pauseIndicator);
		}
	}

	public void leftClick(int x, int y) {
		if (ballsLeft() && !isPaused && !gameOver) {
			addParticle(x, y, false);
		}
	}

	public void rightClick(int x, int y) {
		if (ballsLeft() && !isPaused && !gameOver) {
			addParticle(x, y, true);
		}

	}

}