package GUI;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Helpers.Const;

public class Animation {

	private boolean alwaysOn;
	private boolean randomAnimate;
	private boolean play;
	private int switchInterval;
	private int showingImage;
	private ArrayList<BufferedImage> images;
	private int requestCount;
	
	/**
	 * 
	 * @param imgs - image on index 0 will be shown if an animation isn't running at the time
	 * @param playProfile - 0 = play on demand; 1 = play always; 2 = play randomly 
	 * @param switchTime - frame updates between switching images when running the animation
	 */
	public Animation (ArrayList<BufferedImage> imgs, int playProfile, int switchTime) {
		initialize(imgs, playProfile, switchTime);
	}
	
	/**
	 * 
	 * @param imgs - image on index 0 will be shown if an animation isn't running at the time
	 * @param playProfile - 0 = play on demand; 1 = play always; 2 = play randomly
	 */
	public Animation (ArrayList<BufferedImage> imgs, int playProfile) {
		initialize(imgs, playProfile, 60);
	}
	
	private void initialize (ArrayList<BufferedImage> imgs, int playProfile, int switchTime) {
		images = imgs;
		alwaysOn = playProfile == 1 ? true : false;
		randomAnimate = playProfile == 2 ? true : false;
		switchInterval = switchTime;
		play = false;
		showingImage = 0;
		requestCount = 0;
	}
	
	public void startAnimation () {
		play = true;
	}
	
	private boolean randomStart () {
		return Math.random() < Const.animationStartChance ? true : false;
	}
	
	public BufferedImage getImage () {
		requestCount++;
		if (requestCount >= switchInterval) {
			requestCount = 0;
			if (randomAnimate && !play) {
				play = randomStart();
			}
			if (alwaysOn || play) {
				showingImage = (showingImage + 1) % images.size();
				if (showingImage == 0) {
					play = false;
				}
			} 
		}
		return images.get(showingImage);
	}
	
}
