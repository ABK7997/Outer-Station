package graphics.tiles;

import graphics.Screen;
import graphics.Sprite;

public class Tile {

	public int mapX, mapY;
	public int x, y;
	public Sprite sprite;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public boolean solid() {
		return true;
	}
	
	public void render(int x, int y, Screen screen) {
	}
	
	public int getX() {
		return mapX;
	}
	
	public int getY() {
		return mapY;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}
