package entity.mobs.merchants;

import entity.mobs.Mob;
import graphics.Screen;

public class Merchant extends Mob {
	
	protected String name;
	protected String service;
	protected String[] dialogue;
	protected String[] inquiry;
	
	public void update() {
	}
	
	public void render(Screen screen) {
		screen.renderMob(x, y, sprite);
	}
	
	public String getName() {
		return name;
	}
	
	public String getService() {
		return service;
	}
	
	public String[] getDialogue() {
		return dialogue;
	}
	
	public String[] getInquiry() {
		return inquiry;
	}
	
}
