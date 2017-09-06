package party.equip;

import party.Brawler;

public class Heat_Suit extends Suit {

	public Heat_Suit(Brawler p) {
		player = p;
		
		name = "Heat Suit";
		modifier = 1.25;
		modifier2 = 1.5;
		cost = 15000;
		index = 6;
		description = "An old space suit once used by demolitionists";
		effect = "Heatproof";
	}
}
