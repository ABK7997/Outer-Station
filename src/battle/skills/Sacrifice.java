package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;

public class Sacrifice extends Skill {

	public Sacrifice(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 11;
		name = "Sacrifice";
		type = "Defensive";
		range = "Self";
		description = "Drain your active two drones to restore all of your HP";
	}
	
	public void alter() {
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) {
			p.setMessage("Sacrificed");
			p.getDrone1().drainBattery();
			p.getDrone2().drainBattery();
			
			p.setHP(9999);
		}
		
		if (anim > 43) {
			anim = 0;
			animating = false;
		}
	}
	
	public void animate(Graphics g) {
	}
	
}

