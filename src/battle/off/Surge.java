package battle.off;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import party.Brawler;
import states.Battle;
import battle.Tech;

public class Surge extends Tech {

	public Surge(Brawler p) {
		this.p = p;
		name = "Surge";
		cost = 75;
		price = 7500;
		index = 5;
		type = "Offensive";
		description = "Electrocute all enemies for a large amount of damage";
	}
	
	public void attack(Enemy e) {
		this.e = e;
	
		p.setTP(-cost);
		
		//Starting coordinates
		x = w/2;
		y = 0;
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim < 42);
		else {
			x += 12;
			y += 20;
			
			if (anim == 60) {
				for (int i = 0; i < Battle.eParty.size(); i++) {
					Enemy en = Battle.eParty.get(i);
					
					dmg = p.getTech()*20;
					dmg = ((dmg/en.getTechDef()) * en.getTechMod()) / 100;
					en.setHP(-dmg);
					en.changeState(STATES.HIT);
				}
			}
			
			if (y > h) animating = false;
		}
		
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.YELLOW);
		
		if (anim < 12);
		else if (anim < 42) {
			g.fillOval(x, y, 120, 120);
			g.fillOval(x-150, y, 120, 120);
			g.fillOval(x+150, y, 120, 120);
		}
		else {
			g.fillOval(x, y, 120, 120);
			g.fillOval(x-150, y, 120, 120);
			g.fillOval(x+150, y, 120, 120);
		}
	}
}