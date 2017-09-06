package party;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.Player;
import items.Item;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import party.drones.Drone;
import party.drones.Null_Drone;
import party.equip.Standard_Suit;
import party.equip.Suit;
import party.weapons.DSIS_Pistol;
import party.weapons.Weapon;
import battle.Skill;
import battle.Tech;
import battle.cure.Heal;
import battle.def.Shield;
import battle.off.Shock;
import battle.skills.Aim;
import battle.skills.Bulk_Up;
import battle.skills.Charge;

public class Brawler {

	public Brawler() {
		sprite = right;
		
		drone1 = new Null_Drone();
		drone2 = new Null_Drone();
		
		/* Gives the playe all abilities immediately for testing purposes
		for (int i = 0; i < Storage.offTechs.size(); i++) {
			learnTech(Storage.offTechs.get(i));
		}
		for (int i = 0; i < Storage.cureTechs.size(); i++) {
			learnTech(Storage.cureTechs.get(i));
		}
		for (int i = 0; i < Storage.defTechs.size(); i++) {
			learnTech(Storage.defTechs.get(i));
		}
		for (int i = 0; i < Storage.allSkills.size(); i++) {
			skills.add(Storage.allSkills.get(i));
			skills.get(i).setBrawler(this);
		}
		*/
		
		skills.add(new Bulk_Up(this));
		skills.add(new Aim(this));
		skills.add(new Charge(this));
		
		weapons.add(new DSIS_Pistol());
		weapon = weapons.get(0);
		
		offTechs.add(new Shock(this));
		cureTechs.add(new Heal(this));
		defTechs.add(new Shield(this));
		
		suits.add(new Standard_Suit(this));
		suit = suits.get(0);
	}

	private static Sprite sprite;
	
	//Screen position
	private int x = 0;
	private int y = 0;
	
	//Animations
	private int xa, ya;
	private Random random = new Random();
	private int anim = 0;
	private boolean animating = false;
	private boolean hasGone = false;
	
	public enum STATE {
		NORMAL, ILL, ATTACK, TECH, SKILL, HIT, DEAD, ITEM, DEFEND, VICTORY
	};
	
	private STATE state = STATE.NORMAL;
	
	//Statistics
	private int lv = 5;
	private int priorLevel = 5;
	private int exp = 0;
	private int nextLevel = 100;
	private int priorExp = 0;
	
	private int previousHP = 200;
	private int maxHP = 200; private int hp = maxHP;
	private int maxTP = 30; private int tp = maxTP;

	private int basePwr = 25; private int pwr = basePwr;
	private int baseTech = 10; private int tech = baseTech;
	private int baseDex = 5; private int dex = baseDex;
	private int baseEvd = 5; private int evd = baseEvd;
	private int baseRes = 0; private int res = baseRes;

	private int baseDef = 1; private int def = baseDef;
	private int baseTechDef = 1; private int techDef = baseTechDef;
		
	private int defMod = 100;
	private int techMod = 100;
	private int regen;
		
	//Status Effects
	private boolean statusEffected = false;
	private boolean poisoned = false;
	private boolean burned = false;
	private boolean radio = false;
	
	//Attack
	protected Enemy target;
	protected int choice;
	protected int dp = 0;
	protected int cp = 0;
	protected int rp = 0;
	protected int sp = 0;
	
	//Timers
	private int hpTimer = 0;
	
	private int pwrTimer = 0;
	private int techTimer = 0;
	private int dexTimer = 0;
	private int evdTimer = 0;
	private int resTimer = 0;
	private int defTimer = 0;
	private int techDefTimer = 0;
		
	private int dModTimer = 0;
	private int tModTimer = 0;
	private int regenTimer = 0;
	
	//Battle Message
	private String message = "";
	
	//TECHS and Skills
	protected ArrayList<Tech> offTechs = new ArrayList<Tech>();
	protected ArrayList<Tech> cureTechs = new ArrayList<Tech>();
	protected ArrayList<Tech> defTechs = new ArrayList<Tech>();
	protected int typeChosen = 0;
	protected Tech techChosen = new Shock(this);
	protected int[] techCatalogue;
	
	protected ArrayList<Skill> skills = new ArrayList<Skill>();
	protected Skill skillChosen = new Bulk_Up(this);
	protected int[] skillCatalogue;
	
	//DRONES
	public ArrayList<Drone> drones = new ArrayList<Drone>();
	private Drone drone1; //active drones
	private Drone drone2;
	
	//Inventory
	private ArrayList<Item> items = new ArrayList<Item>();
	private Item itemChosen;
	
	//Weapons
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	private Weapon weapon;
	
	//Armor
	private ArrayList<Suit> suits = new ArrayList<Suit>();
	private Suit suit;
	
	//Weapon Sprites
	private static Sprite gun_1 = new Sprite(24, 4, 0, SpriteSheet.mainChars);
	private static Sprite gun_2 = new Sprite(24, 4, 1, SpriteSheet.mainChars);
	
	private static Sprite sword_1 = new Sprite(24, 5, 0, SpriteSheet.mainChars);
	private static Sprite sword_2 = new Sprite(24, 5, 1, SpriteSheet.mainChars);
	
	private static Sprite cannon_1 = new Sprite(24, 6, 0, SpriteSheet.mainChars);
	private static Sprite cannon_2 = new Sprite(24, 6, 1, SpriteSheet.mainChars);
	
	//SPRITES
	private static Sprite ill = new Sprite(24, 7, 1, SpriteSheet.mainChars);
	private static Sprite attack_1 = gun_1;
	private static Sprite attack_2 = gun_2;
	private static Sprite tech_1 = new Sprite(24, 4, 2, SpriteSheet.mainChars);
	private static Sprite tech_2 = new Sprite(24, 5, 2, SpriteSheet.mainChars);
	private static Sprite hit = new Sprite(24, 8, 0, SpriteSheet.mainChars);
	private static Sprite dead = new Sprite(24, 7, 2, SpriteSheet.mainChars);	
			
	private static Sprite defend = new Sprite(24, 7, 0, SpriteSheet.mainChars);
	private static Sprite item = new Sprite(24, 6, 2, SpriteSheet.mainChars);
	
	private static Sprite right = new Sprite(24, 2, 0, SpriteSheet.mainChars);

	//UPDATE
	public void update() {
		if (animating) anim++;
		
		switch(state) {
		case ATTACK:
			if (anim <= 12) {
				sprite = right;
				x+=2;
				xa = x;
				ya = y;
			}
			else if (anim < 40) {
				x = target.getX() - 36; y = target.getY();
				sprite = attack_1;
			}
			else if (anim == 40) {
				weaponEffect();
			}
			else if (anim <= 65) {
				sprite = attack_2;
			}
			else if (anim <= 75) {
				sprite = right;
				x = xa; y = ya;
			}
			else if (anim <= 87) {
				sprite = right;
				x-= 2;
			}
			else {
				state = STATE.NORMAL;
				setAnimation(false);
			}
			break;
		 
		case TECH: //Also works for skills
			if (anim < 12) {
				sprite = right;
				x+= 2;
			}
			else if (anim == 12) {
				techChosen.setAnimating(true);
			}
			else if (anim < 42) { //30 Frames
				sprite = tech_1;
			}
			else if (anim < 82) { //40 Frames
				sprite = tech_2;
			}
			else if (anim < 94) {
				sprite = right;
				x-=2;
			}
			else {
				state = STATE.NORMAL;
				setAnimation(false);
			}
			break;
			
		case SKILL: //Also works for skills
			if (anim < 12) {
				sprite = right;
				x+= 2;
			}
			else if (anim == 12) {
				skillChosen.setAnimation(true);
			}
			else if (anim <= 40) {
				sprite = tech_1;
			}
			else if (anim <= 64) {
				sprite = tech_2;
			}
			else if (anim <= 76) {
				sprite = right;
				x-=2;
			}
			else {
				state = STATE.NORMAL;
				setAnimation(false);
			}
			break;
			
		case ITEM:
			if (anim <= 12) {
				sprite = right;
				x+= 2;
			}
			else if (anim <= 40) {
				sprite = item;
			}
			else if (anim == 41) {
				itemChosen.useItem();
			}
			else if (anim <= 64) {
				sprite = tech_2;
			}
			else if (anim <= 78) {
				sprite = right;
				x-=2;
			}
			else {
				state = STATE.NORMAL;
				setAnimation(false);
			}
			break;
			
		case HIT: anim++;
			if (anim < 25) sprite = hit;
			else {
				setAnimation(false);
				state = STATE.NORMAL;
			}
			break;
			
		case VICTORY: anim++;
			if (anim > 30) {
				anim = 0;
			}
			else if (anim > 15) {
				sprite = tech_2;
			}
			else sprite = right;
			break;
		
		case DEAD: sprite = dead; break;
		case DEFEND: 
			if (anim < 30) sprite = defend; 
			else {
				setAnimation(false);
				state = STATE.NORMAL;
			}
			break;
		case ILL: sprite = ill; 
			setStatusEffected();
			if (!getStatusEffected()) sprite = right;
			break;
		case NORMAL: 
			sprite = right;
			if (hp == 0) state = STATE.DEAD;
			else if (statusEffected) state = STATE.ILL;
			anim = 0;
			break;
			
		default: sprite = right; break;
		}
			
	}
	
	public void render(Graphics g, Screen screen) {
		screen.renderMob(x, y, sprite);
	}
	
	//On-Screen location
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int num) {
		x = num;
	}
	public void setY(int num) {
		y = num;
	}
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//STAT VALUES//////////////////////////////////////////////////////////////////
	public int getHP() {
		return hp;
	}
	public int getPreviousHP() {
		return previousHP;
	}
	public int getTP() {
		return tp;
	}
	public int getPwr() {
		return pwr;
	}
	public int getDex() {
		return dex;
	}
	public int getEvd() {
		return evd;
	}
	public int getRes() {
		return res;
	}
	public int getTech() {
		return tech;
	}
	public int getDef() {
		return def;
	}
	public int getTechDef() {
		return techDef;
	}
		
	public int getDefMod() {
		return defMod;
	}
	public int getTechMod() {
		return techMod;
	}
	public int getRegen() {
		return regen;
	}
	
	public int getExp() {
		return exp;
	}
	public void restoreExp(int num) {
		exp = num;
	}
	public int getLevel() {
		return lv;
	}
	public void restoreLevel(int num) {
		lv = num;
	}
	public int getNextLv() {
		return nextLevel;
	}
	public void restoreNextLevel(int num) {
		nextLevel = num;
	}
	public int getPriorLv() {
		return priorLevel;
	}
	public int getPriorExp() {
		return priorExp;
	}
	
	//STATUS EFFECT GETTERS
	public boolean getStatusEffected() {
		return statusEffected;
	}
	public boolean getPoisoned() {
		return poisoned;
	}
	public boolean getBurned() {
		if (burned) {
			poisoned = false;
		}
		return burned;
	}
	public boolean getRadio() {
		return radio;
	}
	
	//Stat Effect Setters
	public void setStatusEffected() {
		if (!poisoned && !burned && !radio) statusEffected = false;
		else statusEffected = true;
	}
	public void setPoisoned(boolean bool) {
		poisoned = bool;
	}
	public void setBurned(boolean bool) {
		burned = bool;
		if (burned) {
			poisoned = false;
		}
	}
	public void setRadio(boolean bool) {
		radio = bool;
	}
				
	//BASE STATS Base
	public int getMaxHP() {
		return maxHP;
	}
	public void restoreMaxHP(int num) {
		maxHP = num;
	}
	public int getMaxTP() {
		return maxTP;
	}
	public void restoreMaxTP(int num) {
		maxTP = num;
	}
	public int getBasePwr() {
		return basePwr;
	}
	public void restorePwr(int num) {
		basePwr = num;
	}
	public int getBaseDex() {
		return baseDex;
	}
	public void restoreDex(int num) {
		baseDex = num;
	}
	public int getBaseEvd() {
		return baseEvd;
	}
	public void restoreEvd(int num) {
		baseEvd = num;
	}
	public int getBaseRes() {
		return baseRes;
	}
	public void restoreRes(int num) {
		baseRes = num;
	}
	public int getBaseTech() {
		return baseTech;
	}
	public void restoreTech(int num) {
		baseTech = num;
	}
	public int getBaseDef() {
		return baseDef;
	}
	public int getBaseTechDef() {
		return baseTechDef;
	}
	
	//SETTER METHODS
	public void reviveHP(int num) {
		hp = num;
		state = STATE.NORMAL;
	}
	public void setHP(int num) {
		hp += num;
		
		if (num < 0) setDP(num);
		else if (num > 0) setCP(num);
		
		if (hp <= 0) {
			hp = 0;
			state = STATE.DEAD;
		}
		else if (hp > maxHP) hp = maxHP;
		}
	public void setTP(int num) {
		tp += num;
		
		if (num < 0) setSP(num);
		else if (num > 0) setRP(num);
		
		if (tp < 0) tp = 0;
		else if (tp > maxTP) tp = maxTP;
	}
	public void restoreTP(int num) {
		tp = num;
	}
	public void setPwr(int num) {
		pwr = num;
	}
	public void setDex(int num) {
		dex = num;
	}
	public void setEvd(int num) {
		evd = num;
	}
	public void setRes(int num) {
		res = num;
	}
	public void setTech(int num) {
		tech = num;
	}
	public void setDef(int num) {
		def = num;
	}
	public void setTechDef(int num) {
		techDef = num;
	}
	public void setDefMod(int num) {
		defMod = num;
	}
	public void setTechDefMod(int num) {
		techMod = num;
	}
	public void setRegen(int num) {
		regen = num;
	}
				
	//SET BASE STATS for LEVEL-UP
	public void setMaxHP(double num) {
		maxHP *= num;
	}
	public void setMaxTP(double num) {
		maxTP *= num;
	}
	public void setMaxHP2(int num) {
		previousHP = maxHP;
		maxHP += num;
	}
	public void setMaxTP2(int num) {
		maxTP += num;
	}
	public void setBasePwr(int num) {
		basePwr += num;
	}
	public void setBaseDex(int num) {
		baseDex += num;
	}
	public void setBaseEvd(int num) {
		baseEvd += num;
	}
	public void setBaseRes(int num) {
		baseRes += num;
	}
	public void setBaseTech(int num) {
		baseTech += num;
	}
	public void setBaseDef(int num) {
		baseDef += num;
	}
	public void setBaseTechDef(int num) {
		baseTechDef += num;
	}
	
	public void gainExp(int num) {
		exp += num;
		int extra = 0;
		
		while (exp >= nextLevel) {
			if (exp >= nextLevel) {
				lv++;
				extra = (exp - nextLevel);
				setNextLv();
				if (extra < 0) extra = -extra;
				levelUp();
			}
			
			exp = 0 + extra;
		}
	}
	
	public void setPriorLv() {
		priorLevel = lv;
	}
	public void setPriorExp() {
		priorExp = exp;
	}
				
	public void setNextLv() {
		nextLevel *= 1.15;
	}
	public void levelUp() {
		maxHP *= 1.0545;
		maxTP *= 1.0769;
		if (maxHP > 9999) maxHP = 9999;
		if (maxTP > 999) maxTP = 999;
		basePwr = (int) Math.ceil(basePwr * 1.075);
		baseTech = (int) Math.ceil(baseTech * 1.05);
		
		baseDex++;
		if (lv % 2 == 0) baseEvd++;
		if (lv % 3 == 0) baseRes++;
		
		levelRestore();
	}
	public void levelRestore() {
		hp = maxHP;
		tp = maxTP;
		
		drone1.refillBattery();
		drone2.refillBattery();
	}
	
	//STAT EFFECT TIMERS SETTERS
	public void setHPTimer(int num) {
		hpTimer = num;
	}
	public void setPwrTimer(int num) {
		pwrTimer = num;
	}
	public void setDexTimer(int num) {
		dexTimer = num;
	}
	public void setEvdTimer(int num) {
		evdTimer = num;
	}
	public void setResTimer(int num) {
		resTimer = num;
	}
	public void setTechTimer(int num) {
		techTimer = num;
	}
	public void setDefTimer(int num) {
		defTimer = num;
	}
	public void setTechDefTimer(int num) {
		techDefTimer = num;
	}
	public void setDefModTimer(int num) {
		dModTimer = num;
	}
	public void setTechModTimer(int num) {
		tModTimer = num;
	}
	public void setRegenTimer(int num) {
		regenTimer = num;
	}
		
	//TIMER DECAY
	public void timer() {
		if (hpTimer == 0) {
			maxHP = previousHP;
			if (hp > maxHP) hp = maxHP;
		}
		hpTimer--;
		
		if (pwrTimer == 0) pwr = basePwr;
		pwrTimer--;
		
		if (dexTimer == 0) dex = baseDex;
		dexTimer--;
				
		if (evdTimer == 0) evd = baseEvd;
		evdTimer--;
			
		if (resTimer == 0) res = baseRes;
		resTimer--;
				
		if (techTimer == 0) tech = baseTech;
		techTimer--;
				
		if (defTimer == 0) def = baseDef;
		defTimer--;
				
		if (techDefTimer == 0) techDef = baseTechDef;
		techDefTimer--;
				
		if (dModTimer == 0) defMod = 100;
		dModTimer--;
				
		if (tModTimer == 0) techMod = 100;
		tModTimer--;
				
		if (regenTimer == 0) regen = 0;
		else setHP(regen);
	}	
	
	//TARGETING
	public Enemy getTarget() {
		return target;
	}
	public int getChoice() {
		return choice;
	}
	public int getDP() {
		return dp;
	}
	public int getCP() {
		return cp;
	}
	public int getRP() {
		return rp;
	}
	public int getSP() {
		return sp;
	}
	public void setTarget(Enemy e) {
		target = e;
	}
	public void setChoice(int num) {
		choice = num;
	}
	public void setDP(int num) {
		dp += num;
	}
	public void setCP(int num) {
		cp += num;
	}
	public void setRP(int num) {
		rp += num;
	}
	public void setSP(int num) {
		sp += num;
	}

	//END STAT VALUES/////////////////////////////////////////////////////////////////////
	
	//TECHS and SKILLS
	public void learnTech(Tech tech) {
		tech.setBrawler(this);
		
		switch (tech.getType()) {
		case "Curative": 
			for (int i = 0; i < cureTechs.size(); i++) {
				if (tech.getIndex() == cureTechs.get(i).getIndex()) return;
			}
			cureTechs.add(tech); break;
		case "Offensive":
			for (int i = 0; i < offTechs.size(); i++) {
				if (tech.getIndex() == offTechs.get(i).getIndex()) return;
			}
			offTechs.add(tech); break;
		case "Defensive": 
			for (int i = 0; i < defTechs.size(); i++) {
				if (tech.getIndex() == defTechs.get(i).getIndex()) return;
			}
			defTechs.add(tech); break;
		}
	}
	public void learnSkill(Skill newSkill) {
		newSkill.setBrawler(this);
		
		skills.remove(skills.get(0));
		skills.add(newSkill);
	}
	
	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	public boolean hasSkill(Skill s) {
		for (int i = 0; i < skills.size(); i++) {
			if (s.getIndex() == skills.get(i).getIndex()) return true;
		}
		return false;
	}
	
	public ArrayList<Tech> getCures() {
		return cureTechs;
	}
	public ArrayList<Tech> getOffs() {
		return offTechs;
	}
	public ArrayList<Tech> getDefs() {
		return defTechs;
	}
	
	public boolean hasTech(Tech t, int type) {
		switch(type) {
		case 0:
			for (int i = 0; i < offTechs.size(); i++) {
				if (t.getIndex() == offTechs.get(i).getIndex()) return true;
			}
			break;
		case 1:
			for (int i = 0; i < cureTechs.size(); i++) {
				if (t.getIndex() == cureTechs.get(i).getIndex()) return true;
			}
			break;
		case 2:
			for (int i = 0; i < cureTechs.size(); i++) {
				if (t.getIndex() == cureTechs.get(i).getIndex()) return true;
			}
			break;
		}
		return false;
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	
	public Tech getTechChosen() {
		return techChosen;
	}
	public int getTypeChosen() {
		return typeChosen;
	}
	public Skill getSkillChosen() {
		return skillChosen;
	}
	
	public void setTechType(int num) {
		typeChosen = num;
	}
	public void setTechChosen(Tech tech) {
		techChosen = tech;
	}
	public void setSkillChosen(Skill skill) {
		skillChosen = skill;
	}
	
	//Drones
	public ArrayList<Drone> getDrones() {
		return drones;
	}
	public void addDrone(Drone drone) {
		for (int i = 0; i < drones.size(); i++) {
			if (drones.get(i).getIndex() == drone.getIndex()) return;
		}
		drone.setBrawler(this);
		drones.add(drone);
	}
	public Drone getDrone1() {
		return drone1;
	}
	public Drone getDrone2() {
		return drone2;
	}
	public void setDrone1(Drone d) {
		drone1 = d;
	}
	public void setDrone2(Drone d) {
		drone2 = d;
	}
	public boolean hasDrone(Drone d) {
		for (int i = 0; i < drones.size(); i++) {
			if (d.getIndex() == drones.get(i).getIndex()) return true;
		}
		return false;
	}
	public void buyDrone(Drone d) {
		d.setBrawler(this);
		drones.add(d);
	}
	
	//Animation
	public boolean getAnimating() {
		return animating;
	}
	public int getFrame() {
		return anim;
	}
	public void setAnimation(boolean bool) {
		animating = bool;
		hasGone = true;
	}
	public boolean hasGone() {
		return hasGone;
	}
	public void setGone(boolean bool) {
		hasGone = bool;
	}
	public void changeState(STATE state) {
		this.state = state;
	}
	
	//Message
	public String getMessage() {
		return message;
	}
	public void setMessage(String text) {
		message = text;
	}
	
	//Inventory
	public ArrayList<Item> getInventory() {
		return items;
	}
	public void setItem(Item it) {
		itemChosen = it;
	}
	
	public void addItem(Item it) {
		for (int i = 0; i < items.size(); i++) {
			if (it.getIndex() == items.get(i).getIndex()) {
				items.get(i).stock(1);
				return;
			}
		}
		it.setBrawler(this);
		items.add(it);
	}
	public void newInventory() {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setStock(0);
		}
	}
	
	//Weapons
	public void switchWeapon(Weapon weapon) {
		this.weapon = weapon;
		
		switch(weapon.getType()) {
		case "Pistol":
			attack_1 = gun_1;
			attack_2 = gun_2;
			break;
		case "Blade":
			attack_1 = sword_1;
			attack_2 = sword_2;
			break;
		case "Cannon":
			attack_1 = cannon_1;
			attack_2 = cannon_2;
		default: break;
		}
	}
	
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	public Weapon getWeapon() {
		return weapon;
	}	
	public void weaponEffect() {
		int attack = 0;
		Enemy target = getTarget();
		
		int pwr = (int) (getPwr() * weapon.getModifier());
		int dex = getDex();
		
		int eDef = target.getDef();
		int eEvd = target.getEvd();
		
		int chance = random.nextInt(95);
		
		attack = ((pwr/eDef) * target.getDefMod()) / 100;
		
		if (chance + (eEvd - dex) < 95) {
			int res = target.getRes();
			
			switch(weapon.getEffect()) {
			case "Laser":
				if (target.getType() == "Machine" || target.getType() == "Cyborg") {
					attack *= 2;
				}
				break;
			case "Poison": 
				chance = random.nextInt(100 + res);
				if (chance < 75) {
					target.setPoisoned(true);
					target.setMessage("Poisoned");
				}
				break;
			case "Hack":
				chance = random.nextInt(100 + res);
				if (chance < 75) {
					target.setHacked(true);
					target.setMessage("Hacking");
				}
				break;
			case "Radio":
				chance = random.nextInt(100 + res);
				if (chance < 60) {
					target.setPoisoned(true);
					target.setMessage("Radiation");
				}
				break;
			
			default: break;
			}
			target.setHP(-attack);
			target.changeState(STATES.HIT);
		}
		else target.setMessage("Attack Missed");
	}
	public void sellWeapon(Weapon w) {
		weapons.remove(w);
	}
	public void buyWeapon(Weapon w) {
		for (int i = 0; i < weapons.size(); i++) {
			if (w.getIndex() == weapons.get(i).getIndex()) return;
		}
		weapons.add(weapon);
	}
	public boolean hasWeapon(Weapon w) {
		for (int i = 0; i < weapons.size(); i++) {
			if (w.getIndex() == weapons.get(i).getIndex()) return true;
		}
		return false;
	}
	public void addWeapon(Weapon w) {
		weapons.add(w);
	}
	
	//Suits
	public void switchSuit(Suit suit) {
		this.suit = suit;
	}
	public ArrayList<Suit> getSuits() {
		return suits;
	}
	public Suit getSuit() {
		return suit;
	}
	public void sellSuit(Suit s) {
		suits.remove(s);
	}
	public void addSuit(Suit s) {
		suits.add(s);
	}
	public void buySuit(Suit s) {
		for (int i = 0; i < suits.size(); i++) {
			if (s.getIndex() == suits.get(i).getIndex()) return;
		}
		suits.add(s);
		Player.money -= s.getCost();
	}
	public boolean hasSuit(Suit s) {
		for (int i = 0; i < suits.size(); i++) {
			if (s.getIndex() == suits.get(i).getIndex()) return true;
		}
		return false;
	}
}
