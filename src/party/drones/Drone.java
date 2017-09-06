package party.drones;

import java.util.Random;

import main.Game;
import entity.mobs.enemies.Enemy;
import graphics.Screen;
import graphics.Sprite;
import party.Brawler;

public abstract class Drone {

	protected Random random = new Random();
	
	protected Brawler player;
	protected Enemy enemy;
	protected Sprite sprite;
	
	//Screen position
	protected int x = 0;
	protected int y = 0;
	
	protected int w = Game.frame.getWidth();
	protected int h = Game.frame.getHeight();
	
	//Animations
	protected enum STATE {
		NORMAL, ANIMATING, DRAINED
	};
	protected STATE state = STATE.NORMAL;
	protected boolean animating = false;
	protected boolean hasGone = false;
	protected int anim = 0;
	int xa, ya;
	
	//Statistics
	protected String name;
	protected int cost;
	protected int index;
	protected int level = 1;
	protected String type;
	protected String effect;
	protected int battery, maxBattery;
	protected int recharge, maxRecharge;
	protected int drain = 0;
	protected boolean dead = false;
	protected int power;
	
	protected String description;
	
	//Attacks
	protected String[] commands;
	protected int command = 1;
	
	public void update() {
		if (animating) state = STATE.ANIMATING;
		
		switch(state) {
		case NORMAL: sprite = right; break;
		case ANIMATING: animate(); break;
		case DRAINED: sprite = drained; hasGone = true; break;
		default: break;
		}
	}
	
	public void render(Screen screen) {
		screen.renderMob(x, y, sprite);
	}
	
	//Other Sprites
	protected Sprite right, attack, drained;
	
	//STAT Methods
	public String getName() {
		return name;
	}
	public int getCost() {
		return cost;
	}
	public int getLevel() {
		return level;
	}
	public int getIndex() {
		return index;
	}
	public String getType() {
		return type;
	}
	public String getEffect() {
		return effect;
	}
	public int getPower() {
		return power;
	}
	
	public int getBattery() {
		return battery;
	}
	public void startBattery(int num) {
		battery = num;
	}
	public void setBattery() {
		battery -= drain;
		
		if (battery > maxBattery) battery = maxBattery;
		else if (battery <= 0) {
			battery = 0;
			recharge = 0;
			dead = true;
			state = STATE.DRAINED;
		}
	}
	public void chargeBattery() {
		battery++;
		if (battery > maxBattery) {
			battery = maxBattery;
			state = STATE.NORMAL;
			dead = false;
		}
	}
	public int getMaxBattery() {
		return maxBattery;
	}
	public void refillBattery() {
		battery = maxBattery;
		recharge = maxRecharge;
		state = STATE.NORMAL;
		dead = false;
	}
	public void drainBattery() {
		battery = 0;
		recharge = 0;
		state = STATE.DRAINED;
		hasGone = true;
	}
	
	public int getRecharge() {
		return recharge;
	}
	public void setRecharge(int num) {
		recharge += num;
		
		if (recharge >= maxRecharge) {
			recharge = maxRecharge;
			battery = maxBattery;
			dead = false;
			state = STATE.NORMAL;
		}
		else if (recharge < 0) recharge = 0;
	}
	public void restoreRecharge(int num) {
		recharge = num;
	}
	public int getMaxRecharge() {
		return maxRecharge;
	}
	
	public String getDescription() {
		return description;
	}
	//END STAT VALUES/////////////////////////////////////////////////////////////////////
	
	//Techniques
	public void attack(Enemy e) {
	}
	public void alter() {	
	}
	
	//Miscellaneous
	public boolean dead() {
		return dead;
	}
	public void setDead(boolean bool) {
		dead = bool;
	}
	
	//Location
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Target
	public void setTarget(Enemy e) {
		enemy = e;
	}
	
	//Animating
	public void animate() {	
	}
	
	public boolean getAnimating() {
		return animating;
	}
	public void setAnimating(boolean bool) {
		animating = bool;
		if (!animating) {
			hasGone = true;
			anim = 0;
		}
	}
	public boolean hasGone() {
		return hasGone;
	}
	public void setGone(boolean bool) {
		hasGone = bool;
	}
	
	//Drone move
	public Enemy getEnemy() {
		return enemy;
	}
	public String[] getCommands() {
		return commands;
	}
	public void setCommand(int num) {
		command = num;
	}
	public int getCommand() {
		return command;
	}
	
	//Drainage
	public int getDrain() {
		return drain;
	}
	public void setDrain(int num) {
		drain = num;
	}
	
	//Potency
	public void levelUp() {
	}
	
	public void levelUp2() {
	}
	
	//Assign to player
	public void setBrawler(Brawler b) {
		player = b;
	}
}
