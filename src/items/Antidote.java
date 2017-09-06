package items;

import party.Brawler;

public class Antidote extends Item {

	public Antidote(Brawler p) {
		player = p;
		
		name = "Antidote";
		index = 8;
		cost = 100;
		effect = 0;
		description = "Cures Poison";
	}
	
	public void useItem() {
		player.setPoisoned(false);
		stock(-1);
	}
	
}
