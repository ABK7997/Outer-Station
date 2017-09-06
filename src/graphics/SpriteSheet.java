package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private String path;
	public final int SIZE;
	public int[] pixels;
	
	//Environment
	public static SpriteSheet basic = new SpriteSheet("Environment/basic.png", 576);
	public static SpriteSheet assets = new SpriteSheet("Environment/assets.png", 576);
	
	//Mobs
	public static SpriteSheet mainChars = new SpriteSheet("Mobs/mainChars.png", 576);
	public static SpriteSheet npcs = new SpriteSheet("Mobs/npcs.png", 576);
	public static SpriteSheet drones = new SpriteSheet("Mobs/drones.png", 576);
	
	//Enemies
	public static SpriteSheet machines_1 = new SpriteSheet("Mobs/machines_1.png", 576);
	public static SpriteSheet organics_1 = new SpriteSheet("Mobs/organics_1.png", 576);
	
	//Bosses
	public static SpriteSheet organics_2 = new SpriteSheet("Mobs/organics_2.png", 576);
	public static SpriteSheet machines_2 = new SpriteSheet("Mobs/machines_2.png", 576);
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void load() throws IOException {
		BufferedImage image = ImageIO.read(getClass().getResource(path));
		int width = SIZE;
		int height = SIZE;
		image.getRGB(0, 0, width, height, pixels, 0, width); //convert image into the pixel array
	}
}
