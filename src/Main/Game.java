package Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GUI.Animation;
import GUI.Draw;
import GUI.Button;
import Game.GameMap;
import Game.Particle;
import Game.Spring;
import Game.Vector;
import Helpers.FileManager;


public class Game extends Navigation {

	private static ArrayList<Particle> particles;
	private static ArrayList<Particle> particlesToAdd;
	private static ArrayList<Spring> springs;
	private static ArrayList<Spring> brokenSprings;
	public static double gOnP = 500;
	
	private static GameMap map;
	private static Animation animation;
	
	public Game () {
		particles = new ArrayList<Particle>();
		particlesToAdd = new ArrayList<Particle>();
		springs = new ArrayList<Spring>();
		brokenSprings = new ArrayList<Spring>();
		
		/*
		addStaticParticle(700, 700); // 0
		addStaticParticle(900, 700); // 1
		addStaticParticle(1100, 700); // 2
		*/
		
		map = new GameMap("1 Backyard");
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		imgs.add(FileManager.loadImage("partikeltriangel"));
		imgs.add(FileManager.loadImage("partikeltriangel2"));
		imgs.add(FileManager.loadImage("partikeltriangel3"));
		imgs.add(FileManager.loadImage("partikeltriangel4"));
		animation = new Animation(imgs, 2, 30);
	}
	
	private static void addParticle (int x, int y, boolean isStatic) {
		//check conditions for adding 
		//a particle here
		Particle newP = new Particle(x, y, 1);
		newP.setStaticPos(isStatic);
		particlesToAdd.add(newP);
	}
	
	private static void addStaticParticle (int x, int y) {
		particles.add(new Particle(x, y, 1));
		particles.get(particles.size()-1).setStaticPos(true);
	}
	
	private static void addSpring (Particle p1, Particle p2) {
		springs.add(new Spring(p1, p2, 40));
	}
	
	public static void removeSpring (Spring sp) {
		brokenSprings.add(sp);
	}
	
	private void addParticles () {
		int x;
		int y;
		for (Particle newP : particlesToAdd) {
			x = (int) newP.getXPos();
			y = (int) newP.getYPos();
			particles.add(newP);
			int diffX = 0;
			int diffY = 0;
			int dist = 0;
			for (Particle p : particles) {
				diffX = Math.abs((int) p.getXPos() - x);
				diffY = Math.abs((int) p.getYPos() - y);
				dist = (int) Math.pow(Math.pow(diffX, 2) + Math.pow(diffY, 2), 0.5);
				//if (diffX < 200 && diffY < 200) {
				if (dist < 240 && dist > 20) {
						addSpring(newP, p);
				}
			}
		}
		particlesToAdd.clear();
	}
	
	private void removeBrokenSprings () {
		for (Spring s : brokenSprings) {
			springs.remove(s);
		}
		brokenSprings.clear();
	}
	
	private void toMainMenu () {
		buttons.add(new Button("pauseGame", 1750, 30, new Runnable() {
			@Override
			public void run() {
				Boot.goToMainMenu();
			}
		}));}
	
	public static boolean checkGroundCol(int x, int y) {
		return false;
	}
	
	public void update (double dT) {
		addParticles();
		removeBrokenSprings();
		toMainMenu();
		
		for (Particle p : particles) {
			p.addForce(new Vector(0, gOnP));
		}
		for (Spring sp : springs) {
			sp.calcF();
		}
		for (Particle p : particles) {
			p.update(dT);
		}
	}
	
	public void render () {
		Draw.drawImg(0, 0, map.getMapImage());
		Draw.drawButtons(buttons);
		//Draw.drawImg(0, 0, map.getCollisionImage());
		//Draw.drawImg(10, 20, animation.getImage());
		for (Spring sp : springs) {
			sp.render();
		}
		for (Particle p : particles) {
			p.render();
		}
	}
	
	public void leftClick (int x, int y) {
		addParticle(x, y, false);
	}
	
	public void rightClick (int x, int y) {
		addParticle(x, y, true);
	}
}
