package items;

import party.Brawler;

public class Pwr_Plus extends Item {

	public Pwr_Plus(Brawler p) {
		player = p;
		
		name = "PWR Plus";
		index = 16;
		cost = 10000;
		effect = 4;
		description = "Increase base PWR by 4 points";
	}
	
	public void useItem() {
		player.setBasePwr(effect);
		stock(-1);
	}
	
}
