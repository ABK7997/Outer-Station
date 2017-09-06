package items;

import party.Brawler;

public class Res_Plus extends Item {

	public Res_Plus(Brawler p) {
		player = p;
		
		name = "RES Plus";
		index = 20;
		cost = 7500;
		effect = 1;
		description = "Increase base RES by 1 point";
	}
	
	public void useItem() {
		player.setBaseRes(effect);
		stock(-1);
	}
	
}
