package party.drones;

import graphics.Sprite;
import graphics.SpriteSheet;
import party.Brawler;

public class Medic_Drone extends Drone {

	public Medic_Drone(Brawler player) {
		this.player = player;
		
		cost = 1500;
		index = 2;
		name = "Medical Drone";
		type = "Heal";
		
		maxBattery = battery = 10;
		maxRecharge = recharge = 5;
		power = 2;
		
		right = new Sprite(24, 0, 2, SpriteSheet.drones);
		attack = new Sprite(24, 1, 2, SpriteSheet.drones);
		drained = new Sprite(24, 2, 2, SpriteSheet.drones);
		
		description = "Heals for a small amount every turn";
		effect = "Heal: TECH x";
		
		sprite = right;
		
		commands = new String[] {
			"Heal",
			"Cure Status"
		};
	}
	
	public void levelUp() {
		maxBattery = 14;
		power = 8;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 18;
		power = 15;
		level = 3;
	}
	
	public void animate() { //HEAL
		if (animating) anim++;
		
		if (anim <= 12) {
			x+=2;
			xa = x;
			ya = y;
		}
		else if (anim < 40) {
			x = player.getX() + 36;
			y = player.getY();
		}
		else if (anim == 40) {
			if (command == 1) {
				drain = 1;
				player.setHP(player.getBaseTech() * power);
			}
			else {
				drain = maxBattery;
				
				player.setPoisoned(false);
				player.setBurned(false);
				player.setRadio(false);
				player.setStatusEffected();
				
				player.setMessage("Cured");
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
