package battle.off;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import party.Brawler;
import party.Brawler.STATE;
import battle.Tech;

public class Radiation extends Tech {

	public Radiation(Brawler p) {
		this.p = p;
		name = "Gamma";
		cost = 10;
		price = 1000;
		index = 9;
		type = "Offensive";
		description = "Attempt to irradiate one enemy";
	}
	
	public void attack(Enemy e) {
		this.e = e;
		
		dmg = p.getTech() * 2;
		dmg = ((dmg/e.getTechDef()) * e.getTechMod()) / 100;
	
		p.setTP(-cost);
		
		//Starting coordinates
		x = w/2;
		y = h/2;
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) x++;
		else if (anim < 82) {
			x++;
			if (anim == 70) {
				int chance = random.nextInt(100);
				int eRes = e.getRes();
				
				if (chance < 60 - eRes) {
					e.setRadio(true);
				}
				
				e.setHP(-dmg);
				e.changeState(STATES.HIT);
			}
		}
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.MAGENTA);
		
		if (anim < 12);
		else if (anim < 42) {
			g.fillOval(x, y, anim/2, anim/2);
			g.fillOval(x + 25, y + 30, anim-12, anim-12);
			g.fillOval(x - 30, y-50, anim-5, anim-5);
			g.fillOval(x + 15, y-80, anim+5, anim+5);
		}
		else if (anim < 82){
			g.fillOval(x, y, anim/2, anim/2);
			g.fillOval(x + 25, y + 30, anim-12, anim-12);
			g.fillOval(x - 30, y-50, anim-5, anim-5);
			g.fillOval(x + 15, y-80, anim+5, anim+5);
		}
	}
	
	public Radiation(Enemy e) {
		this.e = e;
		cost = 5;
		type = "Offensive";
	}
	
	public void attack(Brawler p) {
		this.p = p;
		
		dmg = e.getTech() * 2;
		dmg = (int) (((dmg/p.getTechDef())/p.getSuit().getModifier2()) * p.getTechMod()) / 100;
	
		e.setTP(-cost);
		
		//Starting coordinates
		x = w/2;
		y = h/2;
		anim = 12;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) x--;
		else if (anim < 82) {
			x--;
			if (anim == 70) {
				int chance = random.nextInt(100);
				int eRes = p.getRes();
				
				if (chance < 60 - eRes) {
					p.setRadio(true);
				}
				
				p.setHP(-dmg);
				p.changeState(STATE.HIT);
			}
		}
	}
	
	public void animate2(Graphics g) {
		g.setColor(Color.MAGENTA);
		
		if (anim < 12);
		else if (anim < 64) {
			g.fillOval(x, y, anim/2, anim/2);
			g.fillOval(x + 25, y + 30, anim-12, anim-12);
			g.fillOval(x - 30, y-50, anim-5, anim-5);
			g.fillOval(x + 15, y-80, anim+5, anim+5);
		}
		else if (anim < 95){
			g.fillOval(x, y, anim/2, anim/2);
			g.fillOval(x + 25, y + 30, anim-12, anim-12);
			g.fillOval(x - 30, y-50, anim-5, anim-5);
			g.fillOval(x + 15, y-80, anim+5, anim+5);
		}
	}
	
}
