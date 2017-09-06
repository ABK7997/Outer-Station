package items;

import party.Brawler;

public class Toolkit extends Item {

	public Toolkit(Brawler p) {
		player = p;
		
		name = "Toolkit";
		index = 11;
		cost = 2500;
		effect = 0;
		description = "Restores active drones to full battery life";
	}
	
	public void useItem() {
		player.getDrone1().refillBattery();
		player.getDrone2().refillBattery();
		stock(-1);
	}
	
}
