package battle.def;

import java.awt.Graphics;

import party.Brawler;
import battle.Tech;

public class Cyber_Shield extends Tech {

	public Cyber_Shield(Brawler p) {
		this.p = p;
		name = "Cyber";
		index = 5;
		cost = 100;
		price = 10000;
		type = "Defensive";
		description = "Prevents drones from being hacked";
	}
	
	public void alter(Brawler p) {
		this.p = p;
		
		p.setTP(-cost);
		
		setAnimating(true);
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim == 80) {
			p.setMessage("Cyber-Shield");
			//Effect here
		}
		
		if (anim > 82) animating = false;
	}
	
	public void animate(Graphics g) {
	}
	
}

