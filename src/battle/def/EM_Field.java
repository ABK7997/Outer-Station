package battle.def;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class EM_Field extends Tech {

	public EM_Field(Brawler p) {
		this.p = p;
		name = "EM Field";
		index = 1;
		cost = 15;
		price = 1500;
		dmg = 67;
		type = "Defensive";
		description = "Creates an electromagnetic field that temporarily increases TECH-DEF";
	}
	
	public void alter(Brawler p) {
		this.p = p;
		
		p.setTP(-cost);
		
		setAnimating(true);
		
		x = w/2;
		y = h/3;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) y++;
		else if (anim < 82) {
			if (anim == 80) {
				p.setTechDefMod(dmg);
				p.setTechModTimer(8);
				
				p.setMessage("EM Field");
			}
		}
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.CYAN);
		
		if (anim < 12);
		else if (anim < 42) g.fillOval(x, y, 30, 30);
		else if (anim < 82) g.fillOval(x, y, 30, anim + (anim-42));
	}
	
	public EM_Field(Enemy e) {
		this.e = e;
		cost = 15;
		dmg = 67;
		type = "Defensive";
	}
	
	public void alter(Enemy f) {
		friend = f;
		
		e.setTP(-cost);
		
		setAnimating(true);
		
		x = w*3/5;
		y = h/4;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) y++;
		else if (anim < 82) {
			if (anim == 80) {
				friend.setTechMod(dmg);
				friend.setTechModTimer(7);
				
				friend.setMessage("EM Field");
			}
		}
	}
	
	public void animate2(Graphics g) {
		g.setColor(Color.CYAN);
		
		if (anim < 42);
		else if (anim < 42) g.fillOval(x, y, 30, 30);
		else if (anim < 82) g.fillOval(x, y, 30, anim - 70);
	}
	
}

