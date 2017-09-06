package party.equip;

import party.Brawler;

public class Iron_Suit extends Suit {

	public Iron_Suit(Brawler p) {
		player = p;
		
		name = "Iron Suit";
		modifier = 1.75;
		cost = 17500;
		index = 3;
		description = "A specially-crafted suit of iron carefully put together by Duff";
	}
}
