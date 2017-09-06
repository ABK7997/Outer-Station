package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import party.Brawler;
import battle.Tech;

public class Recharge extends Tech {
	
	private int y2;
	
	public Recharge(Brawler p) {
		this.p = p;
		name = "Recharge";
		cost = 100;
		price = 10000;
		index = 8;
		type = "Curative";
		description = "Regenerate health automatically for several turns";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		dmg = p.getTech() * 3;
		p.setTP(-cost);
		
		x = w*2/5;
		y = h/2;
		y2 = h/2;
		
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) x--;
		else if (anim < 82) {
			y += 4;
			y2 -= 4;
			if (anim == 80) {
				p.getDrone1().refillBattery();
				p.getDrone2().refillBattery();
				p.setMessage("Recharged");
			}
		}
		else animating = false;
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.YELLOW);
		
		if (anim < 42) g.fillOval(x, y, 40, 40);
		else if (anim < 82) {
			g.fillOval(x, y, 25, 25);
			g.fillOval(x, y2, 25, 25);
		}
	}
}
