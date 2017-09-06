package entity.mobs.merchants;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Gyro extends Merchant {

	public Gyro(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		
		name = "Amnesiac Gyro";
		service = "Techs";
		
		dialogue = new String[] {"Dialogue", "dialogue", "dialogue"};
		inquiry = new String[] {"Inquiry", "inquiry", "inquiry"};
		
		down = new Sprite(24, 0, 8, SpriteSheet.mainChars);
		
		sprite = down;
	}
	
}
