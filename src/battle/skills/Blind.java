package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Blind extends Skill {

	public Blind(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 3;
		name = "Blind";
		type = "Offensive";
		description = "Severely reduce an enemy's DEX";
		range = "Single";
	}
	
	public void attack(Enemy m) {
		this.e = m;
		
		e.setDex(0);
		e.setDexTimer(7);
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42)	e.setMessage("DEX-");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
	public Blind(Enemy m) {
		this.e = m;
		x = 0;
		y = 0;
		type = "Offensive";
		range = "Single";
	}
	
	public void attack(Brawler p) {
		this.p = p;
		
		p.setDex(0);
		p.setDexTimer(5);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("Blind");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

