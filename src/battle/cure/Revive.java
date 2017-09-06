package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class Revive extends Tech {
	
	public Revive(Brawler p) {
		this.p = p;
		name = "Revive";
		cost = 50;
		price = 5000;
		index = 2;
		type = "Curative";
		description = "Heal yourself for a huge amount";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		
		dmg = p.getTech() * 75;
		p.setTP(-cost);
		
		x = w/2;
		y = h/2;
		
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) x--;
		else if (anim < 82) {
			if (anim == 80) p.setHP(dmg);
		}
		else animating = false;
	}
	
	public void animate(Graphics g) {
		g.setColor(new Color(0xff00AAAA));
		
		if (anim < 42) {
			g.fillOval(x, y, 75, 75);
			g.fillOval(x-30, y-90, 35, 35);
			g.fillOval(x-30, y+110, 35, 35);
			g.fillOval(x+45, y-90, 15, 15);
			g.fillOval(x+45, y+110, 15, 15);
		}
		else if (anim < 82) {
			g.fillOval(x, y, anim+35, anim+35);
			g.fillOval(x-30, y-90, anim+5, anim+5);
			g.fillOval(x-30, y+110, anim+5, anim+5);
			g.fillOval(x+45, y-90, anim-25, anim-25);
			g.fillOval(x+45, y+110, anim-25, anim-25);
		}
	}
	
	public Revive(Enemy e) {
		this.e = e;
		name = "Heal";
		cost = 5;
		type = "Curative";
	}
	
	public void heal(Enemy f) {
		friend = f;
		
		dmg = e.getTech() * 50;
		e.setTP(-cost);
		
		x = w/2;
		y = 0;
		
		anim = 12;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim < 42) y += 5;
		if (anim < 82) {
			if (anim == 80) friend.setHP(dmg);
		}
		else animating = false;
	}
	
	public void animate2(Graphics g) {
		g.setColor(new Color(0xff00AAAA));
		
		if (anim < 42) {
			g.fillOval(x, y, 75, 75);
			g.fillOval(x+30, y-90, 35, 35);
			g.fillOval(x+30, y+110, 35, 35);
			g.fillOval(x-20, y-90, 15, 15);
			g.fillOval(x-20, y+110, 15, 15);
		}
		else if (anim < 82) {
			g.fillOval(x, y, anim+35, anim+35);
			g.fillOval(x+50, y-90, anim+5, anim+5);
			g.fillOval(x+50, y+110, anim+5, anim+5);
			g.fillOval(x-20, y-90, anim-25, anim-25);
			g.fillOval(x-20, y+110, anim-25, anim-25);
		}
	}

}
