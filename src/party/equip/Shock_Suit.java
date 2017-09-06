package party.equip;

import party.Brawler;

public class Shock_Suit extends Suit {

	public Shock_Suit(Brawler p) {
		player = p;
		
		name = "Shock Suit";
		modifier2 = 1.5;
		cost = 10000;
		index = 8;
		description = "A sponge-like suit that absorbs some TECH damage.";
	}
}
