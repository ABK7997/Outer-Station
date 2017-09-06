package battle.off;

import java.awt.Color;
import java.awt.Graphics;

import party.Brawler;
import battle.Tech;
import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;

public class Hack extends Tech {

	public Hack(Brawler p) {
		this.p = p;
		name = "Hack";
		cost = 5;
		price = 500;
		index = 8;
		type = "Offensive";
		description = "Attempt to hack one enemy; only affects machines";
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
			if (anim == 70) {
				int chance = random.nextInt(100);
				int eRes = e.getRes();
				
				if (chance < 75 - eRes) {
					e.setHacked(true);
				}
				
				e.setHP(-dmg);
				e.changeState(STATES.HIT);
			}
		}
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		
		if (anim < 12);
		else if (anim < 42) g.fillOval(x, y, anim-12, anim-12);
		else if (anim < 82) g.fillOval(x, y, anim-12, anim-12);
	}
	
}
