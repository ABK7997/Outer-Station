package battle;

import java.awt.Graphics;
import java.util.Random;

import party.Brawler;
import main.Game;
import entity.mobs.enemies.Enemy;

public abstract class Tech {

	protected int x, y, index;
	protected Random random = new Random();
	protected int anim = 0;
	protected int w = Game.frame.getWidth();
	protected int h = Game.frame.getHeight();
	protected String name;
	protected int dmg, cost, price;
	protected String type;
	protected String range = "Single";
	protected Brawler p;
	protected Enemy e;
	protected Enemy friend;
	protected boolean animating = false;
	protected boolean usable = false;
	protected String description = "Spell";
	
	//Player Commands
	public void heal(Brawler p) {
	}
	public void attack(Enemy e) {
	}
	public void alter(Brawler p) {
	}
	
	//Enemy Commands
	public void heal(Enemy e) {
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
	//Player animation
	public void animate(Graphics g) {
	}
	//Enemy animation
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
	
	public int getCost() {
		return cost;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getDmg() {
		return dmg;
	}
	
	public void setAnimating(boolean bool) {
		animating = bool;
		anim = 12;
	}
	
	public boolean isAnimating() {
		return animating;
	}
	
	public boolean usable() {
		return usable;
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
