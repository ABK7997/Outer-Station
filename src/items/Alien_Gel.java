package items;

import party.Brawler;

public class Alien_Gel extends Item {

	public Alien_Gel(Brawler p) {
		player = p;
		
		name = "Alien Gel";
		index = 3;
		cost = 10000;
		effect = 9999;
		description = "Restores all HP";
	}
	
	public void useItem() {
		player.setHP(effect);
		stock(-1);
	}
	
}
