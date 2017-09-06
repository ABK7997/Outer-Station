package items;

import party.Brawler;

public class Alien_Battery extends Item {

	public Alien_Battery(Brawler p) {
		player = p;
		
		name = "Alien Battery";
		index = 7;
		cost = 10000;
		effect = 999;
		description = "Restores All TP";
	}
	
	public void useItem() {
		player.setTP(effect);
		stock(-1);
	}
	
}
