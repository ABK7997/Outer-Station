package items;

import party.Brawler;

public class AntiHack extends Item {

	public AntiHack(Brawler p) {
		player = p;
		
		name = "Anti-Hack";
		index = 12;
		cost = 1000;
		effect = 0;
		description = "Prevents enemies who would otherwise do so from attacking your drones";
	}
	
	public void useItem() {
		stock(-1);
	}
	
}
