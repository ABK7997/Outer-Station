package party.drones;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import party.Brawler;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Shock_Drone extends Drone {

	public Shock_Drone(Brawler player) {
		this.player = player;
		
		index = 1;
		cost = 1200;
		name = "Shock Drone";
		type = "Shock";
		
		maxBattery = battery = 9; 
		maxRecharge = recharge = 3;
		power = 2;
		
		right = new Sprite(24, 0, 1, SpriteSheet.drones);
		attack = new Sprite(24, 1, 1, SpriteSheet.drones);
		drained = new Sprite(24, 2, 1, SpriteSheet.drones);
		
		description = "Does tech damage for a percentage of the player's TECH";
		effect = "Tech Damage = TECH * ";
		
		sprite = right;
		
		commands = new String[] {
			"Single Enemy",
			"All Enemies"
		};
	}
	
	public void levelUp() {
		maxBattery = 12;
		power = 5;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 15;
		power = 10;
		level = 3;
	}
	
	public void animate() { //ATTACK ONE
		if (animating) anim++;
		
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
			int attack = 0;
			int pwr = player.getTech()*2*power;
			
			if (command == 1) {
				drain = 1;
				int eDef = enemy.getTechDef();
			
				attack = ((pwr/eDef) * enemy.getTechMod()) / 100;
			
				enemy.setHP(-attack);
				enemy.changeState(STATES.HIT);
			}
			else {
				drain = 3;
				for (int i = 0; i < enemy.getParty().size(); i++) {
					Enemy e = enemy.getParty().get(i);
					
					int eDef = e.getTechDef();
					
					attack = ((pwr/eDef) * e.getTechMod()) / 100;
					
					e.setHP(-attack);
					e.changeState(STATES.HIT);
				}
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
