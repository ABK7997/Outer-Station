package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Nerf extends Skill {

	public Nerf(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 9;
		name = "Nerf";
		type = "Offensive";
		description = "Cut an enemy's physical PWR in half";
		range = "Single";
	}
	
	public void attack(Enemy m) {
		this.e = m;
		
		e.setPwr(e.getBasePwr()/2);
		e.setPwrTimer(7);
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) e.setMessage("Nerfed");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
	public Nerf(Enemy m) {
		this.e = m;
		x = 0;
		y = 0;
		index = 9;
		name = "Nerf";
		type = "Offensive";
		description = "Cut an enemy's physical PWR in half";
		range = "Single";
	}
	
	public void attack(Brawler p) {
		this.p = p;
		
		p.setPwr(p.getBasePwr()/2);
		p.setPwrTimer(5);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("Nerfed");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

