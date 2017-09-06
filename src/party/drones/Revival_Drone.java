package party.drones;

import graphics.Sprite;
import graphics.SpriteSheet;
import party.Brawler;
import states.Battle;

public class Revival_Drone extends Drone {

	public Revival_Drone(Brawler player) {
		this.player = player;
		
		cost = 10000;
		index = 8;
		name = "Revival Drone";
		type = "Resurrect";
		
		maxBattery = battery = 5;
		maxRecharge = recharge = 20;
		power = 25;
		
		right = new Sprite(24, 0, 8, SpriteSheet.drones);
		attack = new Sprite(24, 1, 8, SpriteSheet.drones);
		drained = new Sprite(24, 2, 8, SpriteSheet.drones);
		
		description = "Restores player to life after death";
		effect = "Revive with HP%: ";
		
		sprite = right;
		
		commands = new String[] {
			"Standby",
		};
	}
	
	public void levelUp() {
		maxBattery = 7;
		maxRecharge = 18;
		power = 50;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 10;
		maxRecharge = 15;
		power = 100;
		level = 3;
	}
	
	public void animate() { //HEAL
		if (animating) anim++;
		
		if (command == 1) {
			setAnimating(false);
			return;
		}
		
		if (anim <= 40);
		else if (anim <= 52) {
			x+=2;
			xa = x;
			ya = y;
		}
		else if (anim < 80) {
			x = player.getX() + 36;
			y = player.getY();
		}
		else if (anim == 81) {
			battery = 0;
			player.reviveHP((player.getMaxHP()*power)/100);
		}
		else if (anim < 145) {
			sprite = attack;
		}
		else if (anim < 150) {
			sprite = right;
			x = xa; 
			y = ya;
		}
		else if (anim < 162) {
			x-=2;
		}
		else {
			setBattery();
			sprite = right;
			Battle.phase = Battle.PHASE.SELECTION;
			setAnimating(false);
			command = 1;
			state = STATE.DRAINED;
			sprite = drained;
		}
	}
}
