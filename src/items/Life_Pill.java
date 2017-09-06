package items;

import party.Brawler;

public class Life_Pill extends Item {

	public Life_Pill(Brawler p) {
		player = p;
		
		name = "Life Pill";
		index = 13;
		cost = 25000;
		effect = 9999;
		description = "Restores all HP, all TP, both drones, and heals any status effects. Warning: ADDICTIVE";
	}
	
	public void useItem() {
		player.setHP(effect);
		player.setTP(effect);
		player.setPoisoned(false);
		player.setBurned(false);
		player.setRadio(false);
		player.getDrone1().refillBattery();
		player.getDrone2().refillBattery();
		stock(-1);
	}
	
}
