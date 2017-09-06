package party.drones;

import graphics.Sprite;
import graphics.SpriteSheet;
import party.Brawler;

public class Shell_Drone extends Drone {

	public Shell_Drone(Brawler player) {
		this.player = player;
		
		index = 3;
		cost = 2000;
		name = "Shell Drone";
		type = "Defense";
		
		maxBattery = battery = 10;
		maxRecharge = recharge = 5;
		power = 10;
		
		right = new Sprite(24, 0, 3, SpriteSheet.drones);
		attack = new Sprite(24, 1, 3, SpriteSheet.drones);
		drained = new Sprite(24, 2, 3, SpriteSheet.drones);
		
		description = "Produces a physical or electromagnetic sheild for protection";
		effect = "Damage % Reduction: ";
		
		sprite = right;
		
		commands = new String[] {
			"Protect",
			"Electromagnet"
		};
	}
	
	public void levelUp() {
		maxBattery = 12;
		power = 17;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 15;
		power = 25;
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
			drain = 1;
			if (command == 1) {
				player.setDefMod(100-power);
				player.setDefModTimer(2);
			}
			else {
				player.setTechDefMod(100-power);
				player.setTechModTimer(2);
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
