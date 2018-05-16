package Main;

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
import Helpers.MouseInput;
import Helpers.helpFunctions;

public class Game extends Navigation {
	
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
	private static BufferedImage GuideCircle;
	private static boolean gameOver = false;
	private static boolean hoverCircle = false;

	public Game(GameMap _map) {
		initialize(_map);
	}
	
	public Game(String mapName) {
		initialize(new GameMap(mapName));
	}
	
	private void initialize (GameMap _map) {
		map = _map;
		pauseIndicator = FileManager.loadImage("PauseIndicator");
		GuideCircle = FileManager.loadImage("GuideCircle");
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
				hoverCircle = false;
				isPaused = true;
			}
		});
		play = new Button("unPause", 1550, 30, new Runnable() {
			@Override
			public void run() {
				hoverCircle = true;
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
				Boot.goToSelectMap();
			}
		});
	}

	private static void restart() {
		hoverCircle = true;
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
		startParticle();
	}

	private static void startParticle() {
		double rgb;
		for (int h = 0; h < map.getCollisionImage().getHeight(); h++) {
			for (int w = 0; w < map.getCollisionImage().getWidth(); w++) {
				rgb = helpFunctions.collisionColorD(w, h, map.getCollisionImage());
				if (rgb == Const.PINK) {
					addParticle(w, h, false);
					break;
				}
			}
		}
	}

	private static void addParticle(int x, int y, boolean isStatic) {
		// check conditions for adding
		// a particle here
		if(	helpFunctions.collisionColorD(x, y,
			map.getCollisionImage()) == Const.WHITE
			|| helpFunctions.collisionColorD(x, y,
			map.getCollisionImage()) == Const.BLACK
			|| helpFunctions.collisionColorD(x, y,
			map.getCollisionImage()) == Const.PINK) {
			
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

		return 	color != Const.WHITE 
				&& color != Const.BLACK 
				&& color != Const.ORANGE
				&& color != Const.PINK;
	}
	
	public static BufferedImage getColMap () {
		return map.getCollisionImage();
	}

	public void update(double dT) {
			addParticles();
			removeBrokenSprings();

			for (Particle p : particles) {
				p.addForce(new Vector(0, gOnP));
			}
			for (Spring sp : springs) {
				sp.calcF();
			}
			for (Particle p : particles) {
				p.update(dT);
				if (helpFunctions.collisionColorD((int) p.getXPos(), (int) p.getYPos(),
						map.getCollisionImage()) == Const.ORANGE && !gameOver) {
					gameOver = true;
					pause.setVisible(false);
					play.setVisible(false);
				}
				else if (helpFunctions.collisionColorD((int) p.getXPos(), (int) p.getYPos(),
						map.getCollisionImage()) == Const.BLACK && !gameOver) {
					if (winSec == 10) {
						win = true;
						pause.setVisible(false);
						play.setVisible(false);
					}
					winSec++;
				}
			}
		}

	public void render() {
		Draw.drawImg(0, 0, map.getMapImage());
		//Draw.drawImg(0, 0, map.getCollisionImage());
		Draw.drawButtons(buttons);
		Draw.drawTextM(20, 50, "Balls left: " + ballsLeft);
		for (Spring sp : springs) {
			sp.render();
		}
		for (Particle p : particles) {
			p.render();
		}
		
		if (win) {
			Draw.drawTextL(750, 500, "You won!");
			Draw.drawTextM(765, 575, "You used: " + Integer.toString(Const.nbrOfBalls-ballsLeft) + " balls");
		} else if (gameOver) {
			Draw.drawTextL(750, 500, "Game over");
		} else if (isPaused) {
			Draw.drawImg(850, 350, pauseIndicator);
		} else if(hoverCircle){
			Draw.drawImg(MouseInput.x-240,MouseInput.y-240, GuideCircle);
		}
	}

	public void leftClick(int x, int y) {
		if (ballsLeft() && !isPaused && 
			!gameOver && !win) {
			addParticle(x, y, false);
		}
	}
	public void rightClick(int x, int y) {
		if (ballsLeft() && !isPaused && 
			!gameOver && !win) {
			addParticle(x, y, true);
		}
	}

}