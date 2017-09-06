package party.drones;

import entity.mobs.enemies.Enemy.STATES;
import graphics.Sprite;
import graphics.SpriteSheet;
import party.Brawler;

public class Status_Drone extends Drone {

	public Status_Drone(Brawler player) {
		this.player = player;
		
		index = 4;
		cost = 3000;
		name = "Status Drone";
		type = "Status Ailments";
		
		maxBattery = battery = 12;
		maxRecharge = recharge = 8;
		power = 10;
		
		right = new Sprite(24, 0, 4, SpriteSheet.drones);
		attack = new Sprite(24, 1, 4, SpriteSheet.drones);
		drained = new Sprite(24, 2, 4, SpriteSheet.drones);
		
		description = "Attemps to inflict status ailments on enemies";
		effect = "Boosted Chance: ";
		
		sprite = right;
		
		commands = new String[] {
			"Poison",
			"Hack",
			"Burn",
			"Radiation"
		};
	}
	
	public void levelUp() {
		maxBattery = 15;
		maxRecharge = 6;
		power = 20;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 20;
		maxRecharge = 4;
		power = 30;
		level = 3;
	}
	
	public void animate() { //POISON
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
			int res = enemy.getRes();
			int chance = random.nextInt(100);
			
			switch(command) {
			case 1: //Poison
				drain = 1;
				if (chance <= 75 - res + power) {
					if (enemy.getType() == "Organics" || enemy.getType() == "Cyborg") {
						enemy.setPoisoned(true);
						enemy.setMessage("Poisoned");
					}
				} break;
			case 2: //Hack
				drain = 1;
				if (chance <= 75 - res + power) {
					if (enemy.getType() == "Machine" || enemy.getType() == "Cyborg") {
						enemy.setHacked(true);
						enemy.setMessage("Hacked");
					}
				} break;
			case 3: //Burn
				drain = 3;
				if (chance <= 60 - res + power) {
					if (enemy.getType() != "Alien") {
						enemy.setBurned(true);
						enemy.setMessage("Burned");
					}
				} break;
			case 4: //Radiation
				drain = 3;
				if (chance <= 60 - res + power) {
					if (enemy.getType() != "Alien") {
						enemy.setRadio(true);
						enemy.setMessage("Irradiated");
					}
				} break;
			}
			
			enemy.changeState(STATES.HIT);
			
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
