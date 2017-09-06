package items;

import party.Brawler;

public class X_Medkit extends Item {

	public X_Medkit(Brawler p) {
		player = p;
		
		name = "X-Kit";
		index = 2;
		cost = 1500;
		effect = 2500;
		description = "Restores 2500 HP";
	}
	
	public void useItem() {
		player.setHP(effect);
		stock(-1);
	}
	
}
