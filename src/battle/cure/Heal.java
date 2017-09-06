package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class Heal extends Tech {
	
	public Heal(Brawler p) {
		this.p = p;
		name = "Heal";
		cost = 5;
		price = 500;
		index = 0;
		type = "Curative";
		description = "Heal yourself for a small amount";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		dmg = p.getTech() * 8;
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
		
		if (anim < 42) g.fillOval(x, y, 15, 15);
		else if (anim < 82) g.fillOval(x, y, anim-20, anim-20);
	}
	
	public Heal(Enemy e) {
		this.e = e;
		cost = 5;
		type = "Curative";
	}
	
	public void heal(Enemy f) {
		friend = f;
		
		dmg = e.getTech() * 5;
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
		
		if (anim < 42) g.fillOval(x, y, 15, 15);
		else if (anim < 82) g.fillOval(x, y, anim-20, anim-20);
	}

}
