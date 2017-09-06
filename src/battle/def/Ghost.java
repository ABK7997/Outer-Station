package battle.def;

import java.awt.Color;
import java.awt.Graphics;

import party.Brawler;
import battle.Tech;
import entity.mobs.enemies.Enemy;

public class Ghost extends Tech {

	public Ghost(Brawler p) {
		this.p = p;
		name = "Ghost";
		index = 3;
		cost = 100;
		price = 10000;
		dmg = 9999;
		type = "Defensive";
		description = "Immune to physical attacks for a short period";
	}
	
	public void alter(Brawler p) {
		this.p = p;
		
		p.setTP(-cost);
		
		x = w*2/5 - 20;
		y = h/2;
		anim = 12;
		
		setAnimating(true);
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 42) {
			p.setEvd(dmg);
			p.setEvdTimer(4);
		}
		
		if (anim > 82) {
			p.setMessage("Untouchable");
			animating = false;
		}
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.BLACK);
		
		if (anim > 42) g.fillOval(x, y, anim, anim);
	}
	
	public Ghost(Enemy e) {
		this.e = e;
		cost = 100;
		dmg = 9999;
		type = "Defensive";
	}
	
	public void alter(Enemy f) {
		friend = f;
		
		e.setTP(-cost);
		
		anim = 12;
		x = w*3/5;
		h = h*2/5;
		
		setAnimating(true);
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim == 42) {
			friend.setEvd(dmg);
			friend.setEvdTimer(4);
			friend.setMessage("Untouchable");
		}
		
		if (anim > 82) animating = false;
	}
	
	public void animate2(Graphics g) {
		g.setColor(Color.BLACK);
		
		if (anim > 42) g.fillOval(x, y, anim+400, anim+400);
	}
	
}

