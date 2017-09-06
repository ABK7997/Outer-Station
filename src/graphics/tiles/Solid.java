package graphics.tiles;

import graphics.Screen;
import graphics.Sprite;

public class Solid extends Tile {

	public Solid(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x*24, y*24, this);
	}
}
