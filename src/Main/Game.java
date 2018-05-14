package Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

import GUI.Animation;
import GUI.Draw;
import GUI.Button;
import Game.GameMap;
import Game.Particle;
import Game.Spring;
import Game.Vector;
import Helpers.Const;
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
	
	public boolean ballsLeft(){ 
		System.out.println("Antal partiklar: " + particles.size());
		if(particles.size() < Const.nbrOfBalls){
			return true;
		}
		return false;
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
			maxConections = distances.size() > 3 ? 3 : distances.size();
			for(int i = 0; i < maxConections; i++){
				if (distances.get(i) < 240 && distances.get(i) > 20 && i < distances.size()) {
					addSpring(newP, map.get(distances.get(i)));
					connected = true;
				}
			}
			if(connected || particles.size() == 0){
				particles.add(newP);
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
	
	public void toMainMenu () {
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
		if (ballsLeft()) {
			addParticle(x, y, false);
		}
	}
	
	public void rightClick (int x, int y) {
		addParticle(x, y, true);
	}
}