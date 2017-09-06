package battle.def;

import java.awt.Color;
import java.awt.Graphics;

import party.Brawler;
import battle.Tech;
import entity.mobs.enemies.Enemy;

public class Phantom extends Tech {

	public Phantom(Brawler p) {
		this.p = p;
		name = "Phantom";
		index = 4;
		cost = 100;
		price = 10000;
		dmg = 9999;
		type = "Defensive";
		description = "Immune to TECH attacks for a short period";
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) {
			p.setTechDef(dmg);
			p.setTechDefTimer(4);
		}
		
		if (anim > 82) {
			p.setMessage("Phantom");
			animating = false;
		}
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.BLUE);
		
		if (anim > 42) g.fillOval(x, y, anim, anim);
	}
	
	public Phantom(Enemy e) {
		this.e = e;
		cost = 100;
		dmg = 9999;
		type = "Defensive";
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) {
			friend.setEvd(dmg);
			friend.setEvdTimer(4);
			friend.setMessage("Phantom");
		}
		
		if (anim > 82) animating = false;
	}
	
	public void animate2(Graphics g) {
		g.setColor(Color.BLUE);
		
		if (anim > 42) g.fillOval(x, y, anim+400, anim+400);
	}
	
}

