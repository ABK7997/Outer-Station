package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Booster extends Skill {

	public Booster(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 2;
		name = "Booster";
		type = "Defensive";
		range = "Self";
		description = "Increase EVD for 4 turns";
	}
	
	public void alter() {
		p.setEvd(25 + p.getBaseEvd()*3);
		p.setEvdTimer(5);
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("EVD+");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
	public Booster(Enemy e) {
		this.e = e;
		x = 0;
		y = 0;
		dmg = e.getBaseEvd()*3;
		type = "Defensive";
	}
	
	public void alter(Enemy e) {
		this.e = e;
		
		e.setEvd(dmg);
		e.setEvdTimer(5);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) e.setMessage("EVD+");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

