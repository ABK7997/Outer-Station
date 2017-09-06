package graphics;

public class Sprite {

	public final int SIZE;
	private int x, y; //coordinates of sprite on the SpriteSheet
	public int[] pixels;
	private SpriteSheet sheet;
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[size * size];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	protected void load() { //setting the pixels of the sprite equal to the pixels of a section on the spritesheet (1 sprite)
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}
