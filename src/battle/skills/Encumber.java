package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Encumber extends Skill {

	public Encumber(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 5;
		name = "Encumber";
		type = "Offensive";
		description = "Severely reduce an enemy's EVD";
		range = "Single";
	}
	
	public void attack(Enemy m) {
		this.e = m;
		
		e.setEvd(-50);
		e.setEvdTimer(7);
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) e.setMessage("EVD-");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
}

