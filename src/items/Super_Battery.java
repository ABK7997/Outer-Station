package items;

import party.Brawler;

public class Super_Battery extends Item {

	public Super_Battery(Brawler p) {
		player = p;
		
		name = "Super Battery";
		index = 5;
		cost = 750;
		effect = 100;
		description = "Restores 100 TP";
	}
	
	public void useItem() {
		player.setTP(effect);
		stock(-1);
	}
	
}
