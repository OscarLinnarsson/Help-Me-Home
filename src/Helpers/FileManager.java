package Helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class FileManager {

	public static void initialize() {
		
	}
	
	public static String loadTxtFile () {
		StringBuilder txt = new StringBuilder();
		
		return txt.toString();
	}
	
	public static BufferedImage loadImage (String name) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File("./res/" + name + ".png"));
		} catch (IOException e) {
			System.out.println("Image not found");
			img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
		}
		return img;
	}
	
	public static ArrayList<BufferedImage> loadParticleImgArr () {
		ArrayList<BufferedImage> arr = new ArrayList<BufferedImage>();
		arr.add(loadImage(Const.neutralFace));
		for (int i = 0; i < Const.particleFaces.length; i++) {
			arr.add(loadImage(Const.particleFaces[(int)(Math.random()*Const.particleFaces.length)]));
		}
		return arr;
	}
	
	public static ArrayList<String> getAllMapNames() {
		ArrayList<String> maps = new ArrayList<String>();
		maps.add("Backyard");
		maps.add("WaterPassage");
		maps.add("Escalator");
		maps.add("NextLevel");
		return maps;
	}
	
}
