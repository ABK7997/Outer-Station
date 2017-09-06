package battle;

import java.awt.Graphics;
import java.util.Random;

import party.Brawler;
import entity.mobs.enemies.Enemy;
import main.Game;

public class Skill {

	protected Random random = new Random();
	
	protected int x, y, index;
	protected int w = Game.frame.getWidth();
	protected int h = Game.frame.getHeight();
	protected String name;
	protected String effect;
	protected int dmg, anim;
	protected String type;
	protected String range = "Single";
	protected Brawler p;
	protected Enemy e;
	protected boolean animating = false;
	protected String description = "Skill";
	protected Enemy eFriend;
	
	//Player Commands
	public void heal() {
	}
	public void attack(Enemy e) {
	}
	public void alter() {
	}
	
	//Enemy Commands
	public void heal(Enemy m) {
	}
	public void attack(Brawler p) {
	}
	public void alter(Enemy e) {
	}
	
	public void update() {
		if (animating) anim++;
	}
	public void update2() {
		if (animating) anim++;
	}
	public void animate(Graphics g) {
	}
	public void animate2(Graphics g) {
	}
	
	public String getName() {
		return name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getType() {
		return type;
	}
	
	public int getDmg() {
		return dmg;
	}
	
	public boolean isAnimating() {
		return animating;
	}
	
	public void setAnimation(boolean bool) {
		animating = bool;
	}
	
	public String getRange() {
		return range;
	}
	
	public String getDescription() {
		return description;
	}
	
	//Assign to player
	public void setBrawler(Brawler b) {
		p = b;
	}
	
}
