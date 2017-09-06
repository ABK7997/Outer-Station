package entity.mobs;

import main.Game;
import entity.Entity;
import graphics.Screen;
import graphics.Sprite;

public abstract class Mob extends Entity {
	
	protected Sprite sprite;
	protected int dir = 2; //direction 0 = North, 1 = East, 2 = South, 3 = West
	protected int movement = 4;
	protected int speed = 2;
	protected int anim = 0;
	protected boolean walking = false;
	
	//Sprites
	protected Sprite up, up_1, up_2;
	protected Sprite down, down_1, down_2;
	protected Sprite right, right_1, right_2;
	protected Sprite left, left_1, left_2;
	
	protected Sprite ill, attack_1, attack_2, tech_1, tech_2;
	protected Sprite hit, dead;
	protected Sprite defend, item, skill;
	
	public void update() { //unused	
	}
	
	public void render(Screen screen) { //unused
	}
	
	public void move(int xa, int ya) {//controls mob A.I. movement
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (ya < 0) dir = 0; //up or North
		if (xa > 0) dir = 1; //right or East
		if (ya > 0) dir = 2; //down or South
		if (xa < 0) dir = 3; //left or West
		
			
		if (!collision(xa, ya)) { //enables movement if there is no collision
			x += xa;
			y += ya;
		}
	}
	
	//returns whether or not mob is touching the edge of a tile
	protected boolean collision(int xa, int ya) { 
		boolean solid = false;
		
		for (int c = 0; c < 4; c++) { //detecting corners for precise collision detection
			int xc = (x + xa + c % 2 * 5 + 5) / 24;
			int yc = (y + ya + c % 2 * 5 + 15) / 24;
			if(Game.level.getTile(xc, yc).solid()) solid = true;
		}
		
		return solid;
	}
	
}

