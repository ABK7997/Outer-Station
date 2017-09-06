package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Shell extends Skill {

	public Shell(Brawler p) {
		this.p = p;
		name = "Shell";
		index = 10;
		type = "Defensive";
		range = "Self";
		description = "Become immune to tech damage for the active turn";
		
		x = 0;
		y = 0;
	}
	
	public void alter() {
		p.setTechDef(9999);
		p.setTechDefTimer(1);
		
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("Shockproof");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
	public Shell(Enemy e) {
		this.e = e;
		x = 0;
		y = 0;
		type = "Defensive";
	}
	
	public void alter(Enemy m) {
		eFriend = m;
		
		eFriend.setTechDef(9999);
		eFriend.setTechDefTimer(2);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) eFriend.setMessage("Shockproof");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

