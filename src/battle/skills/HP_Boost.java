package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;

public class HP_Boost extends Skill {

	public HP_Boost(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 7;
		name = "HP Boost";
		type = "Defensive";
		range = "Self";
		description = "Increase maximum HP by 50% for a moderate time";
	}
	
	public void alter() {
		if (p.getPreviousHP() >= p.getMaxHP()){
			p.setMaxHP2((p.getMaxHP())/2);
			p.setHPTimer(12);
			animating = true;
		}
		else p.setHPTimer(12);
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("HP+");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
}

