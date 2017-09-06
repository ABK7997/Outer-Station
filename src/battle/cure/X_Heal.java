package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class X_Heal extends Tech {
	
	public X_Heal(Brawler p) {
		this.p = p;
		name = "X-Heal";
		cost = 20;
		price = 2000;
		index = 1;
		type = "Curative";
		description = "Heal yourself for a moderate amount";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		
		dmg = p.getTech() * 30;
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
			g.fillOval(x, y, 30, 30);
			g.fillOval(x-15, y-30, 15, 15);
			g.fillOval(x-15, y+40, 15, 15);
		}
		else if (anim < 82) {
			g.fillOval(x, y, anim-5, anim-5);
			g.fillOval(x-15, y-30, anim-25, anim-25);
			g.fillOval(x-15, y+40, anim-25, anim-25);
		}
	}
	
	public X_Heal(Enemy e) {
		this.e = e;
		name = "Heal";
		cost = 20;
		type = "Curative";
	}
	
	public void heal(Enemy f) {
		friend = f;
		
		dmg = e.getTech() * 20;
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
			g.fillOval(x, y, 30, 30);
			g.fillOval(x-15, y-30, 15, 15);
			g.fillOval(x-15, y+40, 15, 15);
		}
		else if (anim < 82) {
			g.fillOval(x, y, anim-5, anim-5);
			g.fillOval(x-15, y-30, anim-25, anim-25);
			g.fillOval(x-15, y+40, anim-25, anim-25);
		}
	}

}
