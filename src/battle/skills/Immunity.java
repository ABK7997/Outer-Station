package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Immunity extends Skill {

	public Immunity(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 8;
		name = "Immunity";
		type = "Defensive";
		range = "Self";
		description = "Greatly increase RES for a few turns";
	}
	
	public void alter() {
		p.setRes(p.getBaseRes() + 30);
		p.setResTimer(10);
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("Immune");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
	public Immunity(Enemy e) {
		this.e = e;
		x = 0;
		y = 0;
		type = "Defensive";
	}
	
	public void alter(Enemy e) {
		e.setTech(e.getBaseRes() + 50);
		e.setResTimer(7);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("Immune");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

