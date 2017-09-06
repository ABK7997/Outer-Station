package party.equip;

import party.Brawler;

public class Engineer_Suit extends Suit {

	public Engineer_Suit(Brawler p) {
		player = p;
		
		name = "Engineer Suit";
		modifier2 = 2.0;
		cost = 25000;
		index = 9;
		description = "An electrician's suit that halves TECH damage taken.";
	}
}
