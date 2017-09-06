package items;

import party.Brawler;

public class TP_Plus extends Item {

	public TP_Plus(Brawler p) {
		player = p;
		
		name = "TP Plus";
		index = 15;
		cost = 10000;
		effect = 20;
		description = "Increase maximum TP by 5%";
	}
	
	public void useItem() {
		player.setMaxTP(player.getMaxTP()/effect);
		stock(-1);
	}
	
}
