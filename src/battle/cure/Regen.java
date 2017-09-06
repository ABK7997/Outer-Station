package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class Regen extends Tech {
	
	public Regen(Brawler p) {
		this.p = p;
		name = "Regen";
		cost = 25;
		price = 2500;
		index = 6;
		type = "Curative";
		description = "Regenerate health automatically for several turns";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		dmg = p.getTech() * 3;
		p.setTP(-cost);
		
		x = w/2;
		y = h*2/5;
		
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim < 42) y++;
		else if (anim < 82) {
			if (anim == 42) {
				p.setMessage("Regen");
				p.setRegen(dmg);
				p.setRegenTimer(10);
			}
		}
		else animating = false;
		
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.CYAN);
		
		if (anim < 42) g.fillOval(x, y, 15, 15);
		else if (anim < 82) g.fillOval(x, y, anim-25, anim-25);
	}
	
	public Regen(Enemy e) {
		this.e = e;
		cost = 25;
		type = "Curative";
	}
	
	public void heal(Enemy f) {
		friend = f;
		
		dmg = e.getTech() * 5;
		e.setTP(-cost);
		
		x = w/2;
		y = h*1/5;
		
		anim = 12;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) y++;
		else if (anim < 82) {
			if (anim == 42) {
				friend.setRegen(dmg);
				friend.setRegenTimer(8);
				friend.setMessage("Regen");
			}
		}
		else animating = false;
	}
	
	public void animate2(Graphics g) {
		g.setColor(Color.CYAN);
		
		if (anim < 12);
		else if (anim < 42) g.fillOval(x, y, 15, 15);
		else if (anim < 82) g.fillOval(x, y, anim-25, anim-25);
	}

}
