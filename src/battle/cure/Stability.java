package battle.cure;

import java.awt.Color;
import java.awt.Graphics;

import party.Brawler;
import battle.Tech;

public class Stability extends Tech {
	
	public Stability(Brawler p) {
		this.p = p;
		name = "Stability";
		cost = 10;
		price = 1000;
		index = 5;
		type = "Curative";
		description = "Cures Radiation";
	}
	
	public void heal(Brawler p) {
		this.p = p;
		p.setTP(-cost);
		
		x = w*2/5;
		y = h*2/5;
		
		anim = 12;
	}
	
	public void update() {
		if (animating) {
			y++;
			anim++;
		}
		
		if (anim == 42) {
			p.setMessage("Cleaned");
			p.setPoisoned(false);
		}
		
		if (anim > 82) animating = false;
	}
	
	public void animate(Graphics g) {
		g.setColor(Color.PINK);
		
		if (anim < 12);
		else if (anim < 20) g.fillOval(x, y, 20, 20);
		else if (anim < 30) {
			g.fillOval(x, y, 20, 20);
			g.fillOval(x + 20, y + 15, 16, 16);
		}
		else if (anim < 40) {
			g.fillOval(x, y, 20, 20);
			g.fillOval(x + 20, y + 15, 16, 16);
			g.fillOval(x-15, y - 20, 22, 22);
		}
		else if (anim < 50) {
			g.fillOval(x, y, 20, 20);
			g.fillOval(x + 20, y + 15, 16, 16);
			g.fillOval(x-15, y - 20, 22, 22);
			g.fillOval(x, y+25, 18, 18);
		}
		else if (anim < 82) {
			g.fillOval(x, y, 20, 20);
			g.fillOval(x + 20, y + 15, 16, 16);
			g.fillOval(x-15, y - 20, 22, 22);
			g.fillOval(x, y+25, 18, 18);
			g.fillOval(x+17, y - 18, 20, 20);
		}
	}

}
