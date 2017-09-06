package assets;

import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;
import items.Item;
import party.drones.Drone;
import party.equip.Suit;
import party.weapons.Weapon;
import battle.Skill;
import battle.Tech;

public class Chest {

	public Chest(int x, int y, int money) {
		this.x = x * 24;
		this.y = y * 24;
		contents = "Money";
		sprite = unopened;
		dialogue[0] = "Found $" + money;
	}
	
	public Chest(int x, int y, Item item) {
		this.x = x * 24;
		this.y = y * 24;
		contents = "Item";
		this.item = item;
		sprite = unopened;
		dialogue[0] = "Found Item: " + item.getName();
	}
	
	public Chest(int x, int y, Weapon weapon) {
		this.x = x * 24;
		this.y = y * 24;	
		contents = "Weapon";	
		this.weapon = weapon;	
		sprite = unopened;
		dialogue[0] = "Found Weapon: " + weapon.getName();
	}
	
	public Chest(int x, int y, Suit suit) {
		this.x = x * 24;
		this.y = y * 24;
		contents = "Suit";
		this.suit = suit;
		sprite = unopened;
		dialogue[0] = "Found Suit: " + suit.getName();
	}
	
	public Chest(int x, int y, Drone drone) {
		this.x = x * 24;
		this.y = y * 24;
		contents = "Drone";
		this.drone = drone;
		sprite = unopened;
		dialogue[0] = "Found Drone: " + drone.getName();
	}
	
	public Chest(int x, int y, Tech tech) {
		this.x = x * 24;
		this.y = y * 24;
		contents = "Tech";
		this.tech = tech;
		sprite = unopened;
		dialogue[0] = "Found New Tech: " + tech.getName();
	}
	
	public Chest(int x, int y, Skill skill) {
		this.x = x * 24;
		this.y = y * 24;
		contents = "Skill";
		this.skill = skill;
		sprite = unopened;
		dialogue[0] = "Found New Skill: " + skill.getName();
	}
	
	private int x, y;
	private String contents;
	private boolean open = false;
	
	private String[] dialogue = {""};
	
	private Sprite sprite;
	private Sprite unopened = new Sprite(24, 0, 0, SpriteSheet.assets);
	private Sprite empty = new Sprite(24, 1, 0, SpriteSheet.assets);
	
	private int money = 0;
	private Weapon weapon;
	private Suit suit;
	private Drone drone;
	private Item item;
	private Tech tech;
	private Skill skill;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public String getContents() {
		return contents;
	}
	public String[] getDialogue() {
		return dialogue;
	}
	
	public boolean getOpened() {
		return open;
	}
	public void open() {
		open = true;
		sprite = empty;
	}
	
	public int getMoney() {
		return money;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public Suit getSuit() {
		return suit;
	}
	public Drone getDrone() {
		return drone;
	}
	public Item getItem() {
		return item;
	}
	public Tech getTech() {
		return tech;
	}
	public Skill getSkill() {
		return skill;
	}
	
	public void render(Screen screen) {
		screen.renderMob(x, y, sprite);
	}
	
}
