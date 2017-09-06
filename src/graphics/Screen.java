package graphics;

import graphics.tiles.Tile;

public class Screen {

	//Dimensions (Game class dimensions)
	public int width, height;
	
	//Map and pixel rendering
	public int[] tiles = new int[0];
	public int[] pixels;
	
	//Offsets / camera control
	public int xOffset, yOffset;
	
	public Screen(int w, int h) {
		width = w;
		height = h;
		pixels = new int[w*h];
	}
	
	//Clear Screen method
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	//Render individual TILE (not used by Screen object itself)
		public void renderTile(int xp, int yp, Tile tile) {
			xp -= xOffset; //map moves in the opposite direction of the player
			yp -= yOffset;
			
			tile.mapX = xp;
			tile.mapY = yp;
			
			for (int y = 0; y < tile.sprite.SIZE; y++) {
				int ya = y + yp; //ya = absolute position relative to the whole map
				
				for (int x = 0; x < tile.sprite.SIZE; x++) {
					int xa = x + xp;
					//stops RENDERING beyond the edges of the screen
					if (xa < -tile.sprite.SIZE || xa >= width || ya < -tile.sprite.SIZE || ya >= height) continue;
					if (xa < 0) xa = 0;
					if (ya < 0) ya = 0;
					pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE]; //image of tile does not use offsets
				}
			}
		}
			
		//Render any MOB
		public void renderMob(int xp, int yp, Sprite sprite) { //renders player
			xp -= xOffset;
			yp -= yOffset;
			for (int y = 0; y < sprite.SIZE; y++) {
				int ya = y + yp;
				for (int x = 0; x < sprite.SIZE; x++) {
					int xa = x + xp;
					if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
					if (xa < 0) xa = 0;
					int col = sprite.pixels[x + y * sprite.SIZE];
					if (col!= 0xffFF00DC) pixels[xa + ya * width] = col;
				}
			}
		}
	
	//Set Offsets
	public void setOffsets(int xOff, int yOff) {
		xOffset = xOff;
		yOffset = yOff;
	}
	
}
