package party.equip;

import party.Brawler;

public class Hazmat_Suit extends Suit {

	public Hazmat_Suit(Brawler p) {
		player = p;
		
		name = "Hazmat Suit";
		modifier = 1.25;
		modifier2 = 1.25;
		cost = 15000;
		index = 5;
		description = "100% protection against radiation.";
		effect = "Antirad";
	}
	
	public void effect() {
		player.setRadio(false);
	}
}
