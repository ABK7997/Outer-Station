package party.weapons;

public class Hack_Beam extends Weapon {

	public Hack_Beam() {
		name = "Hacking Beam";
		cost = 25000;
		index = 14;
		description = "An electric cannon which fries machine circuits and mechanical organs";
		modifier = 1.5;
		type = "Cannon";
		effect = "Hack";
	}
	
}
