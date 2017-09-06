package entity.mobs.merchants;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Wattson extends Merchant {

	public Wattson(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		
		name = "Rejuvinator Wattson";
		service = "Rest";
		
		dialogue = new String[] {"Dialogue", "dialogue", "dialogue"};
		inquiry = new String[] {"Inquiry", "inquiry", "inquiry"};
		
		down = new Sprite(24, 0, 7, SpriteSheet.mainChars);
		
		sprite = down;
	}
	
}
