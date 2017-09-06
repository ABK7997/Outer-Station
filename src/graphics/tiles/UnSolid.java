package graphics.tiles;

import graphics.Screen;
import graphics.Sprite;

public class UnSolid extends Tile {

	public UnSolid(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x*24, y*24, this);
	}
	
	public boolean solid() {
		return false;
	}
	
}
