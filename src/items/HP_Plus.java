package items;

import party.Brawler;

public class HP_Plus extends Item {

	public HP_Plus(Brawler p) {
		player = p;
		
		name = "HP Plus";
		index = 14;
		cost = 10000;
		effect = 20;
		description = "Increase maximum HP by 5%";
	}
	
	public void useItem() {
		player.setMaxHP(player.getMaxHP()/effect);
		stock(-1);
	}
	
}
