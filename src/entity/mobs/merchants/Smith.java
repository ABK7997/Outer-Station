package entity.mobs.merchants;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Smith extends Merchant {

	public Smith(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		
		name = "Smuggler Smith";
		service = "Items";
		
		dialogue = new String[] {"Dialogue", "dialogue", "dialogue"};
		inquiry = new String[] {"Inquiry", "inquiry", "inquiry"};
		
		down = new Sprite(24, 0, 6, SpriteSheet.mainChars);
		
		sprite = down;
	}
	
}
