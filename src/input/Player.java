package input;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.EnemyParty;
import entity.mobs.merchants.Merchant;
import entity.mobs.npcs.NPC;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics;
import java.util.ArrayList;

import level.Level;
import main.Game;
import party.Brawler;
import states.Battle;
import states.Dialogue;
import states.Merchant_State;
import assets.Chest;
import cutscenes.Cutscene;

public class Player {
	
	//Position and Movement
	public static int x, y;
	public static int xa, ya;
	private Keyboard input;
	public static Level level;
	
	//Animations
	private static Sprite sprite;
	private int anim = 0;
	public static int dir = 0;
	private int speed = 4;
	private static boolean walking = false;
	private boolean collision = false;
	public static boolean chosen = false;
	
	//Flee from battle
	public static boolean fleeing = false;
	public static int fleeTimer = 66;
	
	//MISC
	private Brawler player;
	public static int money = 50;
	public static int droneParts = 0;
	
	//Sprites
	private static Sprite up = new Sprite(24, 3, 0, SpriteSheet.mainChars);
	private static Sprite up_1 = new Sprite(24, 3, 1, SpriteSheet.mainChars);
	private static Sprite up_2 = new Sprite(24, 3, 2, SpriteSheet.mainChars);
	
	private static Sprite down = new Sprite(24, 0, 0, SpriteSheet.mainChars);
	private static Sprite down_1 = new Sprite(24, 0, 1, SpriteSheet.mainChars);
	private static Sprite down_2 = new Sprite(24, 0, 2, SpriteSheet.mainChars);
	
	private static Sprite right = new Sprite(24, 2, 0, SpriteSheet.mainChars);
	private static Sprite right_1 = new Sprite(24, 2, 1, SpriteSheet.mainChars);
	private static Sprite right_2 = new Sprite(24, 2, 2, SpriteSheet.mainChars);
	
	private static Sprite left = new Sprite(24, 1, 0, SpriteSheet.mainChars);
	private static Sprite left_1 = new Sprite(24, 1, 1, SpriteSheet.mainChars);
	private static Sprite left_2 = new Sprite(24, 1, 2, SpriteSheet.mainChars);
	
	public Player(int x, int y, Keyboard input, Level level, Brawler b) {
		Player.x = x * 24;
		Player.y = y * 24;
		this.input = input;
		Player.level = level;
		
		player = b;
		
		sprite = down;
		fleeing = true;
	}
	
	public void update() {
		if (input.up) {
			ya -= speed;
			dir = 0;
		}
		if (input.down) {
			ya += speed;
			dir = 2;
		}
		if (input.left) {
			xa -= speed;
			dir = 3;
		}
		if (input.right) {
			xa += speed;
			dir = 1;
		}
		
		if (xa > 0) dir = 1;
		else if (xa < 0) dir = 3;
		else if (ya > 0) dir = 2;
		else if (ya < 0) dir = 0;
		
		if (anim > 9999) anim = 0;
		
		if (fleeing) {
			fleeTimer++;
			if (fleeTimer > 100) fleeing = false;
		}
		
		tileCollision(xa, ya);
		
		walking = false;
		
		if (xa != 0 || ya != 0) {
			walking = true;
			if (!collision) move(xa, ya);
			anim+=2;
		}
		
		else {
			anim = 0;
		}
		
		if (!fleeing && Game.state == Game.STATE.GAME) enemyCollision();
		if (Game.state == Game.STATE.GAME) {
			merchantRange();
			npcRange();
			chestRange();
			triggerCutscene();
		}
		
		if (chosen) chosen = false;
		
		xa = 0; ya = 0;
		
	}
	
	public void render(Screen screen, Graphics g) {
		if (dir == 0) { //Up
			sprite = up;
			if (walking) {
				if (anim % 40 > 20) {
					sprite = up_1;
				}
				else if (anim % 40 > 10){
					sprite = up_2;
				}
			}
		}
		
		else if (dir == 2) { //Down
			sprite = down;
			if (walking) {
				if (anim % 40 > 20) {
					sprite = down_1;
				}
				else if (anim % 40 > 10) {
					sprite = down_2;
				}
			}
		}
		
		else if (dir == 1) { //Right
			sprite = right;
			if (walking) {
				if (anim % 40 > 20) {
					sprite = right_1;
				}
				else if (anim % 40 > 10) {
					sprite = right_2;
				}
			}
		}
		
		else if (dir == 3) { //Left
			sprite = left;
			if (walking) {
				if (anim % 40 > 20) {
					sprite = left_1;
				}
				else if (anim % 40 > 10) {
					sprite = left_2;
				}
			}
		}
		
		screen.renderMob(x, y, sprite);
	}
	
	private static void move(int xa, int ya) {
		x += xa;
		y += ya;
	}
	
	private void tileCollision(int xa, int ya) { 
		for (int c = 0; c < 4; c++) { //detecting corners for precise collision detection
			int xc = (x + xa + c % 2 * 5 + 5) / 24;
			int yc = (y + ya + c % 2 * 5 + 15) / 24;
			if(level.getTile(xc, yc).solid()) collision = true;
			else collision = false;
		}
	}
	
	private void enemyCollision() {
		ArrayList<Enemy> en = Level.enemies;
		fleeTimer = 0;
		
		//player coordinates
		int px = x;
		int py = y;
		
		for (int i = 0; i < en.size(); i++) {
			Enemy e = en.get(i);
			int mx = e.getX();
			int my = e.getY();
			
			int dx = px - mx;
			int dy = py - my;
		
			int dist = (int) Math.sqrt((dx*dx) + (dy*dy)); 
		
			if (dist <= en.get(i).getEncounterRange()) { //In range
				if (!e.getMultiple()) {
					if (en.get(i).getNum() == 1) EnemyParty.changeParty(e);
					else EnemyParty.changeParty(e, e.getNum());
				}
				else {
					ArrayList<Enemy> eParty = e.getMultiparty();
					
					switch(e.getQuantity()) {
					case 0: //1 - 1
						EnemyParty.changeParty(eParty.get(0), 1);
						EnemyParty.addEnemy(eParty.get(1));
						break;
					case 1: //2 - 1
						EnemyParty.changeParty(eParty.get(0), 2);
						EnemyParty.addEnemy(eParty.get(1));
						break;
					case 2: //1 - 2
						EnemyParty.changeParty(eParty.get(0), 1);
						EnemyParty.addEnemy(eParty.get(1));
						EnemyParty.addEnemy(eParty.get(1));
						break;
					case 3: //1 - 1 - 1
						EnemyParty.changeParty(eParty.get(0), 1);
						EnemyParty.addEnemy(eParty.get(1));
						EnemyParty.addEnemy(eParty.get(2));
						break;
					default: 
						EnemyParty.changeParty(eParty.get(0), 1);
						break;
					}
				}
				Game.battle = new Battle(Game.brawler);
				Game.state = Game.STATE.BATTLE;
			}
		}
	}
	
	private void merchantRange() {
		//player coordinates
		int px = x;
		int py = y;
			
		for (int i = 0; i < Level.merchants.size(); i++) {
			Merchant m = Level.merchants.get(i);
				
			int mx = m.getX();
			int my = m.getY();
			
			int dx = px - mx;
			int dy = py - my;
			
			int dist = (int) Math.sqrt((dx*dx) + (dy*dy)); 
			
			if (dist <= 24 && chosen) {
				Game.state = Game.STATE.MERCHANT;
				Merchant_State.setMerchant(m, Game.brawler);
			}
		}
	}
	
	private void npcRange() {
		//player coordinates
		int px = x;
		int py = y;
			
		for (int i = 0; i < Level.npcs.size(); i++) {
			NPC n = Level.npcs.get(i);
				
			int mx = n.getX();
			int my = n.getY();
			
			int dx = px - mx;
			int dy = py - my;
			
			int dist = (int) Math.sqrt((dx*dx) + (dy*dy)); 
			
			if (dist <= 24 && chosen) {
				Game.state = Game.STATE.DIALOGUE;
				
				//Set Dialogue
				Dialogue.dialogue = n.getDialogue();	
			}
		}
	}
	
	private void chestRange() {
		int px = x;
		int py = y;
		
		for (int i = 0; i < Level.chests.size(); i++) {
			Chest c = Level.chests.get(i);
			
			int cx = c.getX();
			int cy = c.getY();
			
			int dx = px - cx;
			int dy = py - cy;
			
			int dist = (int) Math.sqrt((dx*dx) + (dy*dy));
			
			if (dist <= 24 && chosen && !c.getOpened()) {
				openChest(c);
				c.open();
				Game.state = Game.STATE.DIALOGUE;
				Dialogue.dialogue = c.getDialogue();
			}
		}
	}
	
	private void triggerCutscene() {
		int px = x;
		int py = y;
					
		for (int i = 0; i < Level.cutscenes.size(); i++) {
			Cutscene c = Level.cutscenes.get(i);
			
			int mx = c.getX();
			int my = c.getY();
				
			int dx = px - mx;
			int dy = py - my;
				
			int dist = (int) Math.sqrt((dx*dx) + (dy*dy)); 
				
			if (dist <= c.getRange()) {
				if (c.getPre() && !c.isFinished()) {
					xa = 0; ya = 0;
					c.startAnimation();
					input.update();
				}
			}
		}
	}
	
	//Open Chest
	private void openChest(Chest c) {
		switch(c.getContents()) {
		case "Money": Player.money += c.getMoney(); break;
		case "Item": player.addItem(c.getItem()); Merchant_State.items.add(c.getItem()); break;
		case "Weapon": if (!player.hasWeapon(c.getWeapon())) player.addWeapon(c.getWeapon()); Merchant_State.weapons.add(c.getWeapon()); break;
		case "Suit": if (!player.hasSuit(c.getSuit()))player.addSuit(c.getSuit()); Merchant_State.suits.add(c.getSuit()); break;
		case "Tech": player.learnTech(c.getTech()); Merchant_State.addTech(c.getTech()); break;
		case "Skill": player.learnSkill(c.getSkill()); Merchant_State.skills.add(c.getSkill()); break;
		case "Drone": player.addDrone(c.getDrone()); Merchant_State.drones.add(c.getDrone()); break;
		}
	}
}
