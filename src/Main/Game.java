package Main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

import GUI.Animation;
import GUI.Draw;
import Game.FinishLine;
import Game.GameMap;
import Game.Particle;
import Game.Spring;
import Game.Vector;
import Helpers.FileManager;

public class Game extends Navigation {

	private int startColor = new Color(255,0,255).getRGB();
	private int finishColor = new Color(0,0,0).getRGB();
	
	public static ArrayList<Particle> particles;
	private static ArrayList<Particle> particlesToAdd;
	private static ArrayList<Spring> springs;
	private static ArrayList<Spring> brokenSprings;
	public static double gOnP = 500;
	
	public static GameMap map;
	private static Animation animation;
	private static FinishLine finishLine;
	
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
	
	public void startpunkt() { //vi kan byta namn på metoden om vi vill
		int rgb;
		for (int h = 0; h < map.getCollisionImage().getHeight(); h++) {
//			for (int w = map.getCollisionImage().getWidth(); w>0; w--) {
			for (int w = 0; w < map.getCollisionImage().getWidth(); w++) {
				rgb = map.getCollisionImage().getRGB(w, h);
				if (rgb == startColor) {
					addParticle(w, h, true);
					addParticle(w+100, h, true); 
					addParticle(w+50, h-50, true);
				}
				else if (rgb == finishColor) {
					new FinishLine(w, h);
				}
			}
		}
	}
	
	private static void addParticle (int x, int y, boolean isStatic) {
		//check conditions for adding 
		//a particle here
		Particle newP = new Particle(x, y, 1);
		newP.setStaticPos(isStatic);
		particlesToAdd.add(newP);
	}
	
	public boolean ballsLeft(){
		if(particles.size()< 20){
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
		boolean connected = false;
		for (Particle newP : particlesToAdd) {
			x = (int) newP.getXPos();
			y = (int) newP.getYPos();
			int diffX = 0;
			int diffY = 0;
			int dist = 0;
			for (Particle p : particles) {
				diffX = Math.abs((int) p.getXPos() - x);
				diffY = Math.abs((int) p.getYPos() - y);
				dist = (int) Math.pow(Math.pow(diffX, 2) + Math.pow(diffY, 2), 0.5);
				distances.add(dist);
				map.put(dist, p);
				//if (diffX < 200 && diffY < 200) {
			}
			Collections.sort(distances);
			for(int i=0; i<3; i++){
				if (dist < 240 && dist > 20 && i < distances.size()) {
					addSpring(newP, map.get(distances.get(i)));
					connected = true;
				}
			}
			if(connected || particles.size()==0){
				connected = false;
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
	
	public static boolean checkGroundCol(int x, int y) {
		return false;
	}
	
	public void update (double dT) {
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
		}
	}
	
	public void render () {
		Draw.drawImg(0, 0, map.getMapImage());
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