package entity.mobs.merchants;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Circuit extends Merchant {

	public Circuit(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		
		name = "Circuit";
		service = "Drones";
		
		dialogue = new String[] {"Dialogue", "dialogue", "dialogue"};
		inquiry = new String[] {"Inquiry", "inquiry", "inquiry"};
		
		down = new Sprite(24, 0, 5, SpriteSheet.mainChars);
		
		sprite = down;
	}
	
}
