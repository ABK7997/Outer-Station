package items;

import party.Brawler;

public class X_Battery extends Item {

	public X_Battery(Brawler p) {
		player = p;
		
		name = "X-Battery";
		index = 6;
		cost = 3000;
		effect = 350;
		description = "Restores 350 TP";
	}
	
	public void useItem() {
		player.setTP(effect);
		stock(-1);
	}
	
}
