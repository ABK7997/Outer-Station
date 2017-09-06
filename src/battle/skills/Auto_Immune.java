package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Auto_Immune extends Skill {

	public Auto_Immune(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 1;
		name = "Auto-Immunity";
		type = "Offensive";
		description = "Severely reduce an enemy's RES";
		range = "Single";
	}
	
	public void attack(Enemy m) {
		this.e = m;
		
		e.setRes(e.getBaseRes()-40);
		e.setResTimer(7);
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) e.setMessage("RES-");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
}

