package party.drones;

import graphics.Sprite;
import graphics.SpriteSheet;
import party.Brawler;

public class Buff_Drone extends Drone {

	public Buff_Drone(Brawler player) {
		this.player = player;
		
		cost = 5000;
		index = 6;
		name = "Buff Drone";
		type = "Stat Booster";
		
		maxBattery = battery = 8;
		maxRecharge = recharge = 10;
		power = 15;
		
		right = new Sprite(24, 0, 6, SpriteSheet.drones);
		attack = new Sprite(24, 1, 6, SpriteSheet.drones);
		drained = new Sprite(24, 2, 6, SpriteSheet.drones);
		
		description = "Applies status buffs for the next turn";
		effect = "Modify %: ";
		
		sprite = right;
		
		commands = new String[] {
			"PWR",
			"TECH",
			"DEX",
			"EVD",
			"RES"
		};
	}
	
	public void levelUp() {
		maxBattery = 10;
		maxRecharge = 9;
		power = 20;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 12;
		maxRecharge = 8;
		power = 33;
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
			
			switch(command) {
			case 1: drain = 2;
				player.setPwr((player.getBasePwr()*power)/100); 
				player.setPwrTimer(2);
				player.setMessage("PWR+");
				break;
			case 2: drain = 2;
				player.setTech((player.getBaseTech()*power)/100); 
				player.setTechTimer(2);
				player.setMessage("TECH+");
				break;
			case 3: 
				player.setDex((player.getBaseDex()*power)/100); 
				player.setDexTimer(2);
				player.setMessage("DEX+");
				break;
			case 4: 
				player.setEvd((player.getBaseEvd()*power)/100); 
				player.setEvdTimer(2);
				player.setMessage("EVD+");
				break;
			case 5: 
				player.setRes((player.getBaseRes()*power)/100); 
				player.setResTimer(2);
				player.setMessage("RES+");
				break;
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
