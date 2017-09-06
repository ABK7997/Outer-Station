package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;

public class Aim extends Skill {

	public Aim(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 0;
		name = "Aim";
		dmg = 99999;
		type = "Defensive";
		range = "Self";
		description = "Maximize DEX for 5 turns";
	}
	
	public void alter() {
		p.setDex(dmg);
		p.setDexTimer(6);
		
		anim = 12;
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("DEX+");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
		
	}
	
}

