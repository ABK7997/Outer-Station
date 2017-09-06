package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class Rejuvenate extends Tech {
	
	public Rejuvenate(Brawler p) {
		this.p = p;
		name = "Rejuvenate";
		cost = 100;
		price = 10000;
		index = 9;
		type = "Curative";
		description = "Completely heal yourself and cure all status ailments";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		dmg = 9999;
		p.setTP(-cost);
		
		x = w/3 + 50;
		y = h*1/5;
		
		anim = 12;
	}
	
	public void update(){
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) y+=2;
		else if (anim < 82) {
			y += 3;
			if (anim == 80) {
				p.setHP(dmg);
				p.setPoisoned(false);
				p.setBurned(false);
				p.setRadio(false);
			}
		}
		else animating = false;
	}
	
	public void animate(Graphics g) {
		g.setColor(new Color(0xffFFA723));
		
		if (anim < 42) g.fillOval(x, y, 100, 100);
		else if (anim < 82) g.fillOval(x, y, anim+60 + (anim - 42), anim+60 + (anim - 42));
	}
	
	public Rejuvenate(Enemy e) {
		this.e = e;
		cost = 100;
		type = "Curative";
	}
	
	public void heal(Enemy f) {
		friend = f;
		
		dmg = 99999;
		e.setTP(-cost);
		
		x = w*3/5;
		y = h/7;
		
		anim = 12;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) y++;
		else if (anim < 82) {
			y+=3;
			if (anim == 80) {
				e.setHP(dmg);
				e.setPoisoned(false);
				e.setBurned(false);
				e.setRadio(false);
			}
		}
		else animating = false;
	}
	
	public void animate2(Graphics g) {
		g.setColor(new Color(0xffFFA723));
		
		if (anim < 12);
		else if (anim < 42) g.fillOval(x, y, 100, 100);
		else if (anim < 82) g.fillOval(x, y, anim+60 + (anim - 40), anim+60 + (anim - 40));
	}

}
