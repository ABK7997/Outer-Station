package entity.mobs.enemies.multiples;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.machines.Droid;
import entity.mobs.enemies.organics.Rat;
import graphics.Sprite;
import graphics.SpriteSheet;

public class RatDroid extends Enemy {
	
	public RatDroid(int x, int y, int level, int quantity) {
		this.x = x * 24;
		this.y = y * 24;
		
		this.quantity = quantity;
		
		up = new Sprite(24, 3, 0, SpriteSheet.organics_1);
		up_1 = new Sprite(24, 3, 1, SpriteSheet.organics_1);
		up_2 = up;
		
		down = new Sprite(24, 0, 0, SpriteSheet.organics_1);
		down_1 = new Sprite(24, 0, 1, SpriteSheet.organics_1);;
		down_2 = down;
		
		right = new Sprite(24, 2, 0, SpriteSheet.organics_1);
		right_1 = new Sprite(24, 2, 1, SpriteSheet.organics_1);
		right_2 = right;
		
		left = new Sprite(24, 1, 0, SpriteSheet.organics_1);
		left_1 = new Sprite(24, 1, 1, SpriteSheet.organics_1);
		left_2 = left;
		
		multiparty.add(new Rat(0, 0, 1, 1));
		multiparty.add(new Droid(0, 0, 1, 1));
		
		speed = 2;
		
		multiple = true;
		
		expGiven = 15 * (quantity + 1);
	}
	
	public void movement() {
		randomMovement();
		if (multiparty.get(0).getRemoved()) kill(); //Ensures removal after battle
	}
	
}
