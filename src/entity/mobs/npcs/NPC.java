package entity.mobs.npcs;

import input.Player;
import main.Game;
import graphics.Screen;
import entity.mobs.Mob;

public abstract class NPC extends Mob {

	protected String[] dialogue;
	protected String name;
	protected char direction; //direction
	
	//Movement patterns
	protected int point1 = 0;
	protected int point2 = 0;
	
	protected boolean p1 = false;
	protected boolean p2 = false;
	
	protected int distance = 0;
	
	protected enum STATE {
		ANIMATING, DIALOGUE, IMMOBILE
	};
	
	protected STATE state = STATE.IMMOBILE;
	
	public void update() {
		switch(state) {
		case ANIMATING:
			behavior();
			move(xa, ya);
			break;
			
		case IMMOBILE:
			if (Player.y < y) dir = 0;
			else if (Player.y > y) dir = 2;
			else if (Player.x < x) dir = 3;
			else if (Player.y > x) dir = 1;
			break;
		case DIALOGUE: break;
		}
		
		if (anim > 9999) anim = 0;
		anim+=2;
	}
	
	public void render(Screen screen) {
		if (Game.state == Game.STATE.GAME || Game.state == Game.STATE.CUTSCENE) {
			if (dir == 0) { //Up
				sprite = up;
				if (walking) {
					if (anim % 40 > 20) {
						sprite = up_1;
					}
					else if (anim % 40 > 10){
						sprite = up_2;
					}
				}
			}
			
			else if (dir == 2) { //Down
				sprite = down;
				if (walking) {
					if (anim % 40 > 20) {
						sprite = down_1;
					}
					else if (anim % 40 > 10) {
						sprite = down_2;
					}
				}
			}
		
			else if (dir == 1) { //Right
				sprite = right;
				if (walking) {
					if (anim % 40 > 20) {
						sprite = right_1;
					}
					else if (anim % 40 > 10) {
						sprite = right_2;
					}
				}
			}
		
			else if (dir == 3) { //Left
				sprite = left;
				if (walking) {
					if (anim % 40 > 20) {
						sprite = left_1;
					}
					else if (anim % 40 > 10) {
						sprite = left_2;
					}
				}
			}
		
			xa = 0; ya = 0;
		}
		
		screen.renderMob(x, y, sprite);
	}
	
	protected void behavior() {
		if (direction == 'x') xBehavior(point1, point2);
		else if (direction == 'y') yBehavior(point1, point2);
		else walking = false;
	}
	
	public String[] getDialogue() {
		return dialogue;
	}
	
	protected void xBehavior(int w1, int w2) {
		walking = true;
		
		int x1 = w1 << 5; int x2 = w2 << 5;
		
		if (!p1) { //leftMost coordinate
			if (x > x1) xa = -speed;
			else {
				p2 = false;
				p1 = true;
			}
		}
		else if (!p2) { //rightMost coordinate
			if (x < x2) xa = speed;
			else {
				p1 = false;
				p2 = true;
			}
		}
	}
	
	protected void yBehavior(int z1, int z2) {
		walking = true;
		
		int y1 = z1 << 5; int y2 = z2 << 5;
		
		if (!p1) { //topMost coordinate
			if (y > y1) ya = -speed;
			else {
				p2 = false;
				p1 = true;
			}
		}
		else if (!p2) { //bottomtMost coordinate
			if (y < y2) ya = speed;
			else {
				p1 = false;
				p2 = true;
			}
		}
	}
	
	public boolean getShop() {
		return false;
	}
	
	//Move during cutscenes
	
	public void moveX(int x) {
		xa = x;
		walking = true;
		if (x == 0) walking = false;
		move(xa, ya);
	}
	public void moveY(int y) {
		ya = y;
		walking = true;
		if (y == 0) walking = false;
		move(xa, ya);
	}
	
	//Cutscene Movement
	public void moveX(int x, int dist) {
		if (distance >= dist << 5) xa = 0;
		xa = x;
		walking = true;
		if (x == 0) walking = false;
		distance++;
		move(xa, ya);
	}
	
	public void moveY(int y, int dist) {
		if (distance >= dist << 5) ya = 0;
		ya = y;
		walking = true;
		if (y == 0) walking = false;
		distance++;
		move(xa, ya);
	}
	
	public void turn(int dir) {
		switch(dir) {
		case 0: sprite = up; break;
		case 1: sprite = right; break;
		case 2: sprite = down; break;
		case 3: sprite = left; break;
		}
	}
}
