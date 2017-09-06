package items;

import party.Brawler;

public class Medkit extends Item {

	public Medkit(Brawler p) {
		player = p;
		
		name = "Medkit";
		index = 0;
		cost = 50;
		effect = 100;
		description = "Restores 100 HP";
	}
	
	public void useItem() {
		player.setHP(effect);
		stock(-1);
	}
	
}
