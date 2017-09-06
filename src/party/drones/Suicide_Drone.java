package party.drones;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import party.Brawler;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Suicide_Drone extends Drone {

	public Suicide_Drone(Brawler player) {
		this.player = player;
		
		index = 7;
		cost = 10000;
		name = "Suicide Drone";
		type = "Explosive";
		
		maxBattery = battery = 15;
		maxRecharge = recharge = 20;
		power = 3;
		
		right = new Sprite(24, 0, 7, SpriteSheet.drones);
		attack = new Sprite(24, 1, 7, SpriteSheet.drones);
		drained = new Sprite(24, 2, 7, SpriteSheet.drones);
		
		description = "Explodes, harming all enemies but also draining its own battery completely";
		effect = "PWR x";
		
		sprite = right;
		
		commands = new String[] {
			"Standby",
			"Self-Destruct"
		};
	}
	
	public void levelUp() {
		maxRecharge = 18;
		power = 4;
		level = 2;
	}
	
	public void levelUp2() {
		maxRecharge = 15;
		power = 5;
		level = 3;
	}
	
	public void animate() { 
		if (animating) anim++;
		drain = 1;
		
		if (command == 1) {
			setAnimating(false);
			return;
		}
		
		if (anim <= 12) {
			x+=2;
			xa = x;
			ya = y;
		}
		else if (anim < 40) {
			x = enemy.getX() - 36;
			y = enemy.getY();
		}
		else if (anim == 40) {
			drain = maxBattery;
			
			int attack = 0;
			int pwr = player.getBasePwr();
			
			for (int i = 0; i < enemy.getParty().size(); i++) {
				Enemy e = enemy.getParty().get(i);
				
				int eDef = e.getDef();
				
				attack = ((pwr/eDef) * e.getDefMod()) / 100;
				
				e.setHP(-attack);
				e.changeState(STATES.HIT);
			}
		}
		else if (anim < 65) {
			sprite = attack;
		}
		else if (anim < 75) {
			sprite = right;
			x = xa; y = ya;
		}
		else if (anim <= 87) {
			x-= 2;
		}
		else {
			setBattery();
			state = STATE.NORMAL;
			setAnimating(false);
		}
	}
}
