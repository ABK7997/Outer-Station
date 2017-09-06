package party.equip;

import party.Brawler;

public class Rugged_Suit extends Suit {

	public Rugged_Suit(Brawler p) {
		player = p;
		
		name = "Rugged Suit";
		modifier = 1.25;
		cost = 5000;
		index = 1;
		description = "Dirty station suit. Offers just a little extra protection.";
	}
}
