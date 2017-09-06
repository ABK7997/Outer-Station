package party.drones;

import graphics.Sprite;
import graphics.SpriteSheet;
import party.Brawler;

public class Crippler_Drone extends Drone {

	public Crippler_Drone(Brawler player) {
		this.player = player;
		
		index = 5;
		cost = 5000;
		name = "Crippler Drone";
		type = "Nerfer";
		
		maxBattery = battery = 8;
		maxRecharge = recharge = 5;
		power = 15;
		
		right = new Sprite(24, 0, 5, SpriteSheet.drones);
		attack = new Sprite(24, 1, 5, SpriteSheet.drones);
		drained = new Sprite(24, 2, 5, SpriteSheet.drones);
		
		description = "Reduces an enemy's stats by a certain percentage for the active turn";
		effect = "Nerf %:";
		
		sprite = right;
		
		commands = new String[] {
			"PWR",
			"TECH",
			"DEX",
			"EVD",
			"RES",
			"DEF",
			"T-DEF"
		};
	}
	
	public void levelUp() {
		maxBattery = 10;
		power = 33;
		level = 2;
	}
	
	public void levelUp2() {
		maxBattery = 12;
		power = 50;
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
			drain = 1;
			
			switch(command) {
			case 1: 
				drain = 2; 
				enemy.setPwr((enemy.getBasePwr()*power)/100); 
				enemy.setMessage("-PWR");
				break;
			case 2: 
				drain = 2; 
				enemy.setTech((enemy.getBaseTech()*power)/100); 
				enemy.setMessage("-TECH");
				break; //TECH
			case 3: 
				enemy.setDex((enemy.getBaseDex()*power)/100); 
				enemy.setMessage("-DEX");
				break; //DEX
			case 4: 
				enemy.setEvd((enemy.getBaseEvd()*power)/100); 
				enemy.setMessage("-EVD");
				break; //EVD
			case 5: 
				enemy.setRes((enemy.getBaseRes()*power)/100); 
				enemy.setMessage("-RES");
				break; //RES
			case 6: 
				drain = 3; enemy.setDef((enemy.getBaseDef()*power)/100); 
				enemy.setMessage("-DEF");
				break; //DEF
			case 7: 
				drain = 3; enemy.setTechDef((enemy.getBaseTechDef()*power)/100); 
				enemy.setMessage("-T-DEF");
				break; //TECH DEF
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
