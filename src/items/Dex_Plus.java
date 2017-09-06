package items;

import party.Brawler;

public class Dex_Plus extends Item {

	public Dex_Plus(Brawler p) {
		player = p;
		
		name = "DEX Plus";
		index = 18;
		cost = 7500;
		effect = 2;
		description = "Increase base DEX by 2 points";
	}
	
	public void useItem() {
		player.setBaseDex(effect);
		stock(-1);
	}
	
}
