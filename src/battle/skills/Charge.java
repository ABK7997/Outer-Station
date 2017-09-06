package battle.skills;

import java.awt.Graphics;

import party.Brawler;
import battle.Skill;
import entity.mobs.enemies.Enemy;

public class Charge extends Skill {

	public Charge(Brawler p) {
		this.p = p;
		x = 0;
		y = 0;
		index = 12;
		name = "Charge";
		//dmg = base tech * 2;
		type = "Defensive";
		range = "Self";
		description = "Double tech strength for the next turn";
	}
	
	public void alter() {
		dmg = p.getBaseTech()*2;
		
		p.setTech(dmg);
		p.setTechTimer(1);
		
		animating = true;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) p.setMessage("Charged");
		
		if (anim > 43) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
	public Charge(Enemy e) {
		this.e = e;
		x = 0;
		y = 0;
		dmg = e.getBaseTech() * 2;
		type = "Defensive";
	}
	
	public void alter(Enemy e) {
		e.setTech(dmg);
		e.setTechTimer(2);
		
		animating = true;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) e.setMessage("Charged");
		
		if (anim > 43) animating = false;
	}
	
	public void animate2(Graphics g) {
	}
	
}

