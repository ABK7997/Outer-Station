package battle.off;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import party.Brawler;
import party.Brawler.STATE;
import battle.Tech;

public class Super_Shock extends Tech {

	public Super_Shock(Brawler p) {
		this.p = p;
		name = "X-Shock";
		cost = 15;
		price = 1500;
		index = 1;
		type = "Offensive";
		description = "Electrocute one enemy for a moderate amount of damage";
	}
	
	public void attack(Enemy e) {
		this.e = e;
		
		dmg = p.getTech() * 10;
		dmg = ((dmg/e.getTechDef()) * e.getTechMod()) / 100;
	
		p.setTP(-cost);
		
		//Starting coordinates
		x = w/2;
		y = 0;
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim >= 42) {
			x += 20;
			y += 24;
			if (anim == 51) {
				e.setHP(-dmg);
				e.changeState(STATES.HIT);
			}
		}
		
		if (x > w) animating = false;
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.YELLOW);
		
		if (anim < 12);
		else if (anim < 42) g.fillOval(x, y, 45, 45);
		else g.fillOval(x, y, 45, 45);
	}
	
	public Super_Shock(Enemy e) {
		this.e = e;
		cost = 2;
		type = "Offensive";
	}
	
	public void attack(Brawler p) {
		this.p = p;
		
		dmg = e.getTech() * 10;
		
		dmg = (int) (((dmg/p.getTechDef())/p.getSuit().getModifier2()) * p.getTechMod()) / 100;
		
		e.setTP(-cost);
		
		//Starting coordinates
		x = w/2;
		y = h/2;
		anim = 12;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim >= 42) {
			x -= 15;
			if (anim == 51) {
				p.setHP(-dmg);
				p.changeState(STATE.HIT);
			}
		}
		
		if (x < 0) animating = false;
	}
	
	public void animate2(Graphics g) {
		g.setColor(Color.YELLOW);
		
		if (anim < 12);
		else if (anim < 42) g.fillOval(x, y, 45, 45);
		else g.fillOval(x, y, 45, 45);
	}
	
}
