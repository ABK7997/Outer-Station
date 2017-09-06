package items;

import party.Brawler;

public class Tech_Plus extends Item {

	public Tech_Plus(Brawler p) {
		player = p;
		
		name = "TECH Plus";
		index = 17;
		cost = 10000;
		effect = 2;
		description = "Increase base TECH by 2 points";
	}
	
	public void useItem() {
		player.setBaseTech(effect);
		stock(-1);
	}
	
}
