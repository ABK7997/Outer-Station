package party.drones;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Null_Drone extends Drone {

	public Null_Drone() {
		
		index = 9;
		name = "";
		type = "Cannot";
		
		maxBattery = battery = 0;
		maxRecharge = recharge = 0;
		
		right = new Sprite(24, 23, 23, SpriteSheet.drones);
		attack = new Sprite(24, 23, 23, SpriteSheet.drones);
		drained = new Sprite(24, 23, 23, SpriteSheet.drones);
		
		description = "";
		dead = true;
		
		sprite = right;
		
		command = 0;
		hasGone = true;
		
		commands = new String[] {
		};
	}
	
	public void update() {
		hasGone = true;
		command = 0;
	}
}
