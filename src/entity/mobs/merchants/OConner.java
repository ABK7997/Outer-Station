package entity.mobs.merchants;

import graphics.Sprite;
import graphics.SpriteSheet;

public class OConner extends Merchant {

	public OConner(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		
		name = "Weaponsmith OConner";
		service = "Weapons";
		
		dialogue = new String[] {"Dialogue", "dialogue", "dialogue"};
		inquiry = new String[] {"Inquiry", "inquiry", "inquiry"};
		
		down = new Sprite(24, 0, 4, SpriteSheet.mainChars);
		
		sprite = down;
	}
	
}
