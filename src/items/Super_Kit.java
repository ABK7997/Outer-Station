package items;

import party.Brawler;

public class Super_Kit extends Item {

	public Super_Kit(Brawler p) {
		player = p;
		
		name = "Super Medkit";
		index = 1;
		cost = 500;
		effect = 1000;
		description = "Restores 1000 HP";
	}
	
	public void useItem() {
		player.setHP(effect);
		stock(-1);
	}
	
}
