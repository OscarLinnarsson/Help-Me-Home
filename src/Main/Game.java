package Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GUI.Draw;
import Game.Particle;
import Game.Spring;
import Game.Vector;
import Helpers.FileManager;

public class Game implements Navigation {

	private static ArrayList<Particle> particles;
	private static ArrayList<Particle> particlesToAdd;
	private static ArrayList<Spring> springs;
	private static ArrayList<Spring> brokenSprings;
	public static double gOnP = 500;
	
	private static BufferedImage background;
	
	public Game () {
		particles = new ArrayList<Particle>();
		particlesToAdd = new ArrayList<Particle>();
		springs = new ArrayList<Spring>();
		brokenSprings = new ArrayList<Spring>();
		
		addStaticParticle(700, 700); // 0
		addStaticParticle(900, 700); // 1
		addStaticParticle(1100, 700); // 2
		
		background = FileManager.loadImage("partikeltriangel");
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
		Draw.drawBackground();
		Draw.drawImg(100, 200, background);
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
