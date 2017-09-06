package items;

import party.Brawler;

public class Evd_Plus extends Item {

	public Evd_Plus(Brawler p) {
		player = p;
		
		name = "EVD Plus";
		index = 19;
		cost = 7500;
		effect = 2;
		description = "Increase base EVD by 2 points";
	}
	
	public void useItem() {
		player.setBaseEvd(effect);
		stock(-1);
	}
	
}
