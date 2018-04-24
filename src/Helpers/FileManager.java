package Helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
			img = ImageIO.read(new File("./Res/" + name));
		} catch (IOException e) {
			System.out.println("Image not found");
			img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
		}
		return img;
	}
	
}
