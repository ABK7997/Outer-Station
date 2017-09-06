package entity.mobs.merchants;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Troy extends Merchant {

	public Troy(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		
		name = "Engineer Jen Troy";
		service = "Suits";
		
		dialogue = new String[] {"Dialogue", "dialogue", "dialogue"};
		inquiry = new String[] {"Inquiry", "inquiry", "inquiry"};
		
		down = new Sprite(24, 0, 9, SpriteSheet.mainChars);
		
		sprite = down;
	}
	
}
