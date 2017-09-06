package party.drones;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import party.Brawler;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Assault_Drone extends Drone {

	public Assault_Drone(Brawler player) {
		this.player = player;
		
		index = 0;
		cost = 900;
		name = "Assault Drone";
		type = "Assault";
		
		maxBattery = battery = 12;
		maxRecharge = recharge = 3;
		power = 3;
		
		right = new Sprite(24, 0, 0, SpriteSheet.drones);
		attack = new Sprite(24, 1, 0, SpriteSheet.drones);
		drained = new Sprite(24, 2, 0, SpriteSheet.drones);
		
		description = "Does physical damage for a fraction of the player's PWR";
		effect = "Physical Damage = PWR / ";
		
		sprite = right;
		
		commands = new String[] {
			"Single Enemy",
			"All Enemies"
		};
	}
	
	public void levelUp() {
		maxBattery = 15;
		power = 2;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 20;
		power = 1;
		level = 3;
	}
	
	public void animate() { 
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
			int pwr = player.getBasePwr()/power;
			int dex = player.getBaseDex();
			
			if (command == 1) {
				drain = 1;
				
				int eDef = enemy.getDef();
				int eEvd = enemy.getEvd();
			
				attack = ((pwr/eDef) * enemy.getDefMod()) / 100;
			
				int chance = random.nextInt(95);
			
				if (chance + (eEvd - dex) < 95) {
					enemy.changeState(STATES.HIT);
					enemy.setHP(-attack);
				}
				else enemy.setMessage("Attack Missed");
			}
			
			else {
				drain = 3;
				
				for (int i = 0; i < enemy.getParty().size(); i++) {
					Enemy e = enemy.getParty().get(i);
					
					int eDef = e.getDef();
					int eEvd = e.getEvd();
					
					attack = ((pwr/eDef) * e.getDefMod()) / 100;
					
					int chance = random.nextInt(95);
					
					if (chance + (eEvd - dex) < 95) {
						e.setHP(-attack);
						e.changeState(STATES.HIT);
					}
					else e.setMessage("Attack Missed");
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
