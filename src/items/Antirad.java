package items;

import party.Brawler;
import party.Brawler.STATE;

public class Antirad extends Item {

	public Antirad(Brawler p) {
		player = p;
		
		name = "Antirad";
		index = 10;
		cost = 250;
		effect = 0;
		description = "Cures Radiation Poisoning";
	}
	
	public void useItem() {
		player.setRadio(false);
		player.changeState(STATE.NORMAL);
		stock(-1);
	}
	
}
