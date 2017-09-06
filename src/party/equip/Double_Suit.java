package party.equip;

import party.Brawler;

public class Double_Suit extends Suit {

	public Double_Suit(Brawler p) {
		player = p;
		
		name = "Double Suit";
		modifier = 1.5;
		modifier2 = 1.5;
		cost = 25000;
		index = 10;
		description = "A hybrid suit that can defend against physical and TECH damage.";
	}
}
