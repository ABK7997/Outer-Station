package entity.mobs.npcs;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Trader extends NPC {
	
	public Trader(int x, int y, String[] dialogue) {
		this.x = x * 24;
		this.y = y * 24;
		this.dialogue = dialogue;
		name = "Trader";
		direction = 'i';
		
		up = new Sprite(24, 3, 0, SpriteSheet.npcs);
		down = new Sprite(24, 0, 0, SpriteSheet.npcs);
		right = new Sprite(24, 2, 0, SpriteSheet.npcs);
		left = new Sprite(24, 1, 0, SpriteSheet.npcs);
		
		sprite = down;
		
		state = STATE.IMMOBILE;
	}
	
	public Trader(int x, int y, int p, String[] dialogue, char c) {
		this.x = x * 24;
		this.y = y * 24;
		this.dialogue = dialogue;
		direction = c;
		if (c == 'x') this.point1 = x;
		else this.point1 = y;
		this.point2 = p*24;
		name = "Worker";
		
		up = new Sprite(24, 3, 0, SpriteSheet.npcs);
		up_1 = new Sprite(24, 3, 1, SpriteSheet.npcs);
		up_2 = new Sprite(24, 3, 2, SpriteSheet.npcs);
		
		down = new Sprite(24, 0, 0, SpriteSheet.npcs);
		down_1 = new Sprite(24, 0, 1, SpriteSheet.npcs);
		down_2 = new Sprite(24, 0, 2, SpriteSheet.npcs);
		
		right = new Sprite(24, 2, 0, SpriteSheet.npcs);
		right_1 = new Sprite(24, 2, 1, SpriteSheet.npcs);
		right_2 = new Sprite(24, 2, 2, SpriteSheet.npcs);
		
		left = new Sprite(24, 1, 0, SpriteSheet.npcs);
		left_1 = new Sprite(24, 1, 1, SpriteSheet.npcs);
		left_2 = new Sprite(24, 1, 2, SpriteSheet.npcs);
		
		sprite = down;
		
		state = STATE.ANIMATING;
	}
	
	public static String[] dialogue1 = new String[] {
		"I am an NPC",
		"This is a demo",
		"Goodbye"
	};

}
