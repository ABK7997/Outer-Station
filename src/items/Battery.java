package items;

import party.Brawler;

public class Battery extends Item {

	public Battery(Brawler p) {
		player = p;
		
		name = "Battery";
		index = 4;
		cost = 200;
		effect = 25;
		description = "Restores 25 TP";
	}
	
	public void useItem() {
		player.setTP(effect);
		stock(-1);
	}
	
}
