package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Bulk_Up extends Skill {

	public Bulk_Up(Brawler p) {
		this.p = p;
		name = "Bulk Up";
		index = 4;
		type = "Defensive";
		range = "Self";
		description = "Triple PWR for one turn";
		
		x = 0;
		y = 0;
	}
	
	public void alter() {
		dmg = p.getBasePwr()*3;
		
		p.setPwr(dmg);
		p.setPwrTimer(2);
		
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("PWR x3");
		
		if (anim > 43) animating = false;
	}
	
	public void animate() {
	}
	
	public Bulk_Up(Enemy e) {
		this.e = e;
		x = 0;
		y = 0;
		dmg = e.getBasePwr() * 3;
		type = "Defensive";
	}
	
	public void alter(Enemy m) {
		e = m;
		dmg = e.getBasePwr() * 3;
		
		e.setPwr(e.getBasePwr()*3);
		e.setPwrTimer(2);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) e.setMessage("PWR x3");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

