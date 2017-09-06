package items;

import party.Brawler;
import party.Brawler.STATE;

public class Ointment extends Item {

	public Ointment(Brawler p) {
		player = p;
		
		name = "Ointment";
		index = 9;
		cost = 250;
		effect = 0;
		description = "Cures Burn";
	}
	
	public void useItem() {
		player.setBurned(false);
		player.changeState(STATE.NORMAL);
		stock(-1);
	}
	
}
