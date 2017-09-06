package battle.def;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class Barrier extends Tech {

	public Barrier(Brawler p) {
		this.p = p;
		name = "Barrier";
		index = 2;
		cost = 40;
		price = 4000;
		dmg = 70;
		type = "Defensive";
		description = "Creates both a physical and EM protection field to reduce damage";
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
				p.setDefMod(dmg);
				p.setDefModTimer(8);
				p.setTechDefMod(dmg);
				p.setTechModTimer(8);
				
				p.setMessage("Barrier");
			}
		}
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.WHITE);
		
		if (anim < 12);
		else if (anim < 42) g.fillOval(x, y, 50, 50);
		else if (anim < 82) g.fillOval(x, y, 50, anim+8 + (anim-20));
	}
	
	public Barrier(Enemy e) {
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
				friend.setDefMod(dmg);
				friend.setDefModTimer(7);
				friend.setTechMod(dmg);
				friend.setTechModTimer(7);
				
				friend.setMessage("Barrier");
			}
		}
	}
	
	public void animate2(Graphics g) {
		g.setColor(Color.WHITE);
		
		if (anim < 42);
		else if (anim < 42) g.fillOval(x, y, 50, 50);
		else if (anim < 82) g.fillOval(x, y, 50, anim+8 + (anim-20));
	}
	
}