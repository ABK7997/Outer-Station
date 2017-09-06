package party.equip;

import party.Brawler;

public class Military_Suit extends Suit {

	public Military_Suit(Brawler p) {
		player = p;
		
		name = "Military Suit";
		modifier = 1.5;
		cost = 10000;
		index = 2;
		description = "An old marine uniform. The bullet-proof vest is still intact";
	}
}
