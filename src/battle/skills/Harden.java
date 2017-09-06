package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Harden extends Skill {

	public Harden(Brawler p) {
		this.p = p;
		name = "Harden";
		index = 6;
		type = "Defensive";
		range = "Self";
		description = "Become immune to physical damage for the active turn";
		
		x = 0;
		y = 0;
	}
	
	public void alter() {
		p.setDef(9999);
		p.setDefTimer(1);
		
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("Invincible");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
	public Harden(Enemy e) {
		this.e = e;
		x = 0;
		y = 0;
		type = "Defensive";
	}
	
	public void alter(Enemy m) {
		e = m;
		
		e.setDef(9999);
		e.setDefTimer(2);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) e.setMessage("Invincible");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

