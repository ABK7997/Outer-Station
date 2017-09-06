package party.equip;

import party.Brawler;

public class Plague_Suit extends Suit {

	public Plague_Suit(Brawler p) {
		player = p;
		
		name = "Plague Suit";
		modifier = 1.25;
		modifier2 = 1.25;
		cost = 10000;
		index = 8;
		description = "A medical suit once used by doctors dealing with alien pathogens.";
		effect = "Poisonproof";
	}
}
