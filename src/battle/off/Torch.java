package battle.off;

import java.awt.Color;
import java.awt.Graphics;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import party.Brawler;
import party.Brawler.STATE;
import battle.Tech;

public class Torch extends Tech {

	public Torch(Brawler p) {
		this.p = p;
		name = "Torch";
		cost = 10;
		price = 1000;
		index = 7;
		type = "Offensive";
		description = "Attempt to burn one enemy";
	}
	
	public void attack(Enemy e) {
		this.e = e;
		
		dmg = p.getTech() * 3;
		dmg = ((dmg/e.getTechDef()) * e.getTechMod()) / 100;
	
		p.setTP(-cost);
		
		//Starting coordinates
		x = w/2;
		y = h/3;
		anim = 12;
	}
	
	public void update() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) {
			x++;
			y++;
		}
		else if (anim < 82) {
			if (anim == 70) {
				int chance = random.nextInt(100);
				int eRes = e.getRes();
				
				if (chance < 60 - eRes) {
					e.setBurned(true);
				}
				
				e.setHP(-dmg);
				e.changeState(STATES.HIT);
			}
		}
		else animating = false;
	}
	
	public void animate(Graphics g) {
		g.setColor(new Color(0xffC14A00));
		
		if (anim < 12);
		else if (anim < 42) {
			for (int i = 0; i < anim - 12; i++) {
				int chance = random.nextInt(50);
				int chance2 = random.nextInt(50);
				g.fillOval(x+chance, y+chance2, anim+35, anim+35);
			}
		}
		else if (anim < 82){
			for (int i = 0; i < anim - 12; i++) {
				int chance = random.nextInt(50);
				int chance2 = random.nextInt(50);
				g.fillOval(x+chance, y+chance2, anim+35, anim+35);
			}
		}
	}
	
	public Torch(Enemy e) {
		this.e = e;
		cost = 5;
		type = "Offensive";
	}
	
	public void attack(Brawler p) {
		this.p = p;
		
		dmg = e.getTech() * 3;
		dmg = (int) (((dmg/p.getTechDef())/p.getSuit().getModifier2()) * p.getTechMod()) / 100;
	
		e.setTP(-cost);
		
		//Starting coordinates
		x = w/2;
		y = h*2/5;
		anim = 12;
	}
	
	public void update2() {
		if (animating) anim++;
		
		if (anim < 12);
		else if (anim < 42) x--;
		else if (anim < 82) {
			if (anim == 70) {
				int chance = random.nextInt(100);
				int eRes = p.getRes();
				
				if (chance < 60 - eRes) {
					p.setBurned(true);
				}
				
				p.setHP(-dmg);
				p.changeState(STATE.HIT);
			}
		}
		else animating = false;
	}
	
	public void animate2(Graphics g) {
		g.setColor(new Color(0xffC14A00));
		
		if (anim < 12);
		else if (anim < 64) {
			for (int i = 0; i < anim - 12; i++) {
				int chance = random.nextInt(50);
				int chance2 = random.nextInt(50);
				g.fillOval(x+chance, y+chance2, anim+35, anim+35);
			}
		}
		else if (anim < 95){
			for (int i = 0; i < anim - 12; i++) {
				int chance = random.nextInt(50);
				int chance2 = random.nextInt(50);
				g.fillOval(x+chance, y+chance2, anim+35, anim+35);
			}
		}
	}
	
}
