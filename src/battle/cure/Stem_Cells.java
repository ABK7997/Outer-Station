package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import party.Brawler;
import battle.Tech;

public class Stem_Cells extends Tech {
	
	public Stem_Cells(Brawler p) {
		this.p = p;
		name = "Stem Cell";
		cost = 75;
		price = 7500;
		index = 7;
		type = "Curative";
		description = "Regenerate moderate health automatically for several turns";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		dmg = p.getTech() * 9;
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
		
		if (anim < 42) g.fillOval(x, y, 60, 60);
		else if (anim < 82) g.fillOval(x, y, anim+20, anim+20);
	}
	
	public Stem_Cells(Enemy e) {
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
		else if (anim < 42) g.fillOval(x, y, 60, 60);
		else if (anim < 82) g.fillOval(x, y, anim+40, anim+40);
	}

}
