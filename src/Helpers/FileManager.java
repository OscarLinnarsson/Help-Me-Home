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
		File directory = new File("./res/maps");
		File[] tmpFileList = directory.listFiles();
		ArrayList<String> maps = new ArrayList<String>();
		for (File f : tmpFileList) {
			//System.out.println(f.getName());
			if (f.getName().endsWith("-c.png")) {
				maps.add(f.getName().substring(0, 
						f.getName().length()-6));// -6 to remove "-c.png"
				//System.out.println(maps.get(maps.size()-1));
			}
			
		}
		return maps;
	}
	
}
