package party.equip;

import party.Brawler;

public class Standard_Suit extends Suit {

	public Standard_Suit(Brawler p) {
		player = p;
		
		name = "Standard Suit";
		modifier = 1.0;
		cost = 900;
		index = 0;
		description = "Your standard-issue DSIS space suit. No perks or benefits included.";
	}
}
