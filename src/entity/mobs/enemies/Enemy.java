package entity.mobs.enemies;

import input.Player;
import items.Item;

import java.util.ArrayList;
import java.util.Random;

import main.Game;
import party.Brawler;
import party.Brawler.STATE;
import states.Battle;
import battle.Skill;
import battle.Tech;
import battle.off.Shock;
import battle.skills.Bulk_Up;
import entity.mobs.Mob;
import graphics.Screen;

public abstract class Enemy extends Mob implements Cloneable {
	
	//RNG
	protected Random random = new Random();
	
	//Timing
	protected long time = 0;
	protected long elapsed = 0;
	protected int ax, ay; //Resets coordinates after a physical attack
	
	//Movement and Animation
	protected boolean inRange;
	protected int anim = 0;
	public boolean animating = false;
	protected boolean hasGone = false;
	
	//Player Detection
	protected int encounterRange = 24;
	protected int chaseRange;
	
	//Stats
	protected int index;
	protected String name = "";
	protected String type;
	protected int lv, expGiven, moneyGiven;
	protected int maxHP, maxTP;
	protected int hp, tp;
	protected int pwr, dex, evd, res, tech, eng, def, techDef;
	protected int basePwr, baseDex, baseEvd, baseRes, baseTech, baseDef, baseTechDef;
	protected int defMod = 100;
	protected int techMod = 100;
	
	protected int fleeChance;
	protected int mobNum;
	
	//Status Effects
	protected boolean statusEffected = false;
	protected boolean poisoned = false;
	protected boolean burned = false;
	protected boolean hacked = false;
	protected boolean radio = false;
	
	//Timers
	protected int pwrTimer = 0;
	protected int dexTimer = 0;
	protected int evdTimer = 0;
	protected int resTimer = 0;
	protected int techTimer = 0;
	protected int defTimer = 0;
	protected int techDefTimer = 0;
	protected int dModTimer = 0;
	protected int tModTimer = 0;
	protected int regenTimer = 0;
	
	protected int regen;
	
	//Battle Message
	protected String message = "";
	
	//Attacks
	protected int[] moves;
	protected Brawler target;
	protected Enemy friendly = this;
	protected int choice = 0; protected int choice2 = 0;
	protected int dp = 0;
	protected int cp = 0;
	protected int rp = 0;
	protected int sp = 0;
	protected boolean attacks;
	
	//Tech / Skill
	protected ArrayList<Tech> offTechs = new ArrayList<Tech>();
	protected ArrayList<Tech> cureTechs = new ArrayList<Tech>();
	protected ArrayList<Tech> defTechs = new ArrayList<Tech>();
	protected int typeChosen = 0;
	protected Tech techChosen = new Shock(this);
	
	protected ArrayList<Skill> skills = new ArrayList<Skill>();
	protected Skill skillChosen = new Bulk_Up(this);
	
	protected ArrayList<Enemy> party = new ArrayList<Enemy>();
	
	//Multiparty
	protected boolean multiple = false;
	protected ArrayList<Enemy> multiparty = new ArrayList<Enemy>();
	protected int quantity; 
	/* QUANTITES
	 * 0 = 2 Enemies; 1 - 1
	 * 1 = 2 Enemies; 2 - 1
	 * 2 = 2 Enemies; 1 - 2
	 * 3 = 3 Enemies; 1 - 1 - 1
	 */
	
	//Drop Item
	protected Item drop;
	protected int dropChance = 0;
	
	//Music
	protected String music = "Battle";
	
	//Battle States
	public enum STATES {
		NORMAL, ATTACK, TECH, SKILL, DEAD, ILL, HIT;
	};
	
	private STATES state = STATES.NORMAL;
	
	public void update() { //Default movement	
		anim++;
		if (anim > 9999) anim = 0;
		
		if (Game.state == Game.STATE.GAME) movement();
		else animate();
	}
	
	public void movement() {
		
	}
	
	public void noMovement() {
	}
	
	public void render(Screen screen) {
		if (Game.state == Game.STATE.GAME) {
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
		
			xa = 0; ya = 0;
		}
		
		screen.renderMob(x, y, sprite); 
	}

	//Animation
	public void animate() {
		if (Game.state == Game.STATE.BATTLE) {
			switch (state) {
			
			//ATTACK
			case ATTACK: 
				if (anim <= 12) {
					sprite = left;
					x-=2;
				}
				else if (anim < 35) {
					x = target.getX() + 36; y = target.getY();
					sprite = attack_1;
				} 
				else if (anim == 37) {
					int attack = 0;
					Brawler target = getTarget();
				
					int ePwr = getPwr();
					int eDex = getDex();
				
					int def = target.getDef();
					int evd = target.getEvd();
				
					attack = (int) ((ePwr / def) / target.getSuit().getModifier());
					
					attack = (attack * target.getDefMod()) / 100;
				
					int chance = random.nextInt(100);
				
					if (chance + (evd - eDex) < 95) {
						target.setHP(-attack);
						target.changeState(STATE.HIT);
					
						target.getSuit().effect();
					}
					else target.setMessage("Attack Missed");
				}
				else if (anim <= 55) {
					sprite = attack_2;
				}
				else if (anim <= 80) {
					sprite = left;
					x = ax - 24; y = ay;
				}
				else if (anim <= 92) {
					sprite = left;
					x+=2;
				}
				else {
					state = STATES.NORMAL;
					hasGone = true;
					Battle.secondMove = true;
				}
				break;
			
			//TECH
			case TECH: 
				if (anim < 12) {
					sprite = left;
					x--;
				}
				else if (anim == 12) techChosen.setAnimating(true);
				else if (anim < 42) { //30 frames
					sprite = tech_1;
				}
				else if (anim < 82) { //40 frames
					sprite = tech_2;
				}
				else if (anim < 94) {
					sprite = left;
					x++;
				}
				else {
					state = STATES.NORMAL;
					hasGone = true;
					Battle.secondMove = true;
				} 
				break;
			
			//SKILL
			case SKILL:
				if (anim < 12) {
					sprite = left;
					x--;
				}
				else if (anim == 12) skillChosen.setAnimation(true);
				else if (anim <= 100) {
					sprite = tech_1;
				}
				else if (anim <= 192) {
					sprite = tech_2;
				}
				else if (anim <= 204) {
					sprite = left;
					x++;
				}
				else {
					state = STATES.NORMAL;
					hasGone = true;
					Battle.secondMove = true;
				} 
				break;
			
			//Take a HIT
			case HIT:
				if (anim <= 30) {
					sprite = hit;
				}
				else {
					state = STATES.NORMAL;
					animating = false;
				}
				break;
			
				//OTHERS
			case DEAD: sprite = dead; animating = false; choice = 0; break;
			case ILL: sprite = ill; animating = false; break;
			
			case NORMAL: sprite = left; animating= false; anim = 0;
				if (hp == 0) state = STATES.DEAD;
				else {
					setStatusEffected();
					if (statusEffected) state = STATES.ILL;
				}
				break;
			
			default: sprite = left; animating = false; break;
			}
		}
	}

	//Random AI
	public void randomMovement() {
		elapsed = System.currentTimeMillis() - time;
		if (elapsed >= 3000) {
			
			time = System.currentTimeMillis();
			
			movement = random.nextInt(5);
		}
		
		if (movement == 0) xa = speed;
		else if (movement == 1) xa = -speed;
		else if (movement == 2) ya = speed;
		else if (movement == 3) ya = -speed;
		
		//Turn in opposite direction if colliding with a walll
		if (collision(xa, ya) && movement == 0) movement = 1;
		else if (collision(xa, ya) && movement == 1) movement = 0;
		else if (collision(xa, ya) && movement == 2) movement = 3;
		else if (collision(xa, ya) && movement == 3) movement = 2;
		
		if (xa != 0 || ya != 0 && !collision(xa, ya)) { //enables movement
			move(xa, ya);
			walking = true;
		}
		else {
			walking = false;
		}
		
		if (anim > 9999) anim = 0;
		anim+=2;
	}
	
	//Linear AI
	public void linearMovement() {
		move(xa, ya);
		walking = true;
		
		if (anim > 9999) anim = 0;
		anim++;
	}
	
	//Chaser AI
	public void chaserMovement() {
		int xa = 0; int ya = 0;
		
		chaserDistance();
		
		if (inRange) {
			follow();
			return;
		}
		
		elapsed = System.currentTimeMillis() - time;
		if (elapsed >= 3000) {
			
			time = System.currentTimeMillis();
			
			movement = random.nextInt(5);
		}
		
		if (movement == 0) xa = speed*3/4;
		else if (movement == 1) xa = -speed*3/4;
		else if (movement == 2) ya = speed*3/4;
		else if (movement == 3) ya = -speed*3/4;
		
		//Turn in opposite direction if colliding with a walll
		if (collision(xa, ya) && movement == 0) movement = 1;
		else if (collision(xa, ya) && movement == 1) movement = 0;
		else if (collision(xa, ya) && movement == 2) movement = 3;
		else if (collision(xa, ya) && movement == 3) movement = 2;
		
		if (xa != 0 || ya != 0 && !collision(xa, ya)) { //enables movement
			move(xa, ya);
			walking = true;
		}
		else {
			walking = false;
		}
		
		if (anim > 9999) anim = 0;
		anim+=2;
	}
	
	public void chaserDistance() {
		//Chaser coordinates
		int cx = x;
		int cy = y;
		
		//Player coordinates
		int px = Player.x;
		int py = Player.y;
		
		//horizontal and vertical distance
		int dx = cx - px;
		int dy = cy - py;
		
		//total distance (Pythagorean Theorem)
		double dist = Math.sqrt((dx*dx) + (dy*dy));
		if (dist <= chaseRange) inRange = true;
		else inRange = false;
	}
	
	public void follow() {
		int xa = 0;
		int ya = 0;
		if (x < Player.x) xa = speed;
		if (x > Player.x) xa = -speed;
		if (y < Player.y) ya = speed;
		if (y > Player.y) ya = -speed;
		
		if (xa != 0 || ya != 0) { //enables movement
			walking = true;
			move(xa, ya);
		}
		else {
			walking = false;
		}
		
		if (anim > 9999) anim = 0;
		anim+=2;
	}
	
	//Battle Behavior and Move Selection
	public void behavior() {
	}
	
	//STAT GETTERS
	
	//Basic Info
	public int getIndex() {
		return index;
	}
	
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public int getHP() {
		return hp;
	}
	public int getTP() {
		return tp;
	}
	
	//Battle stats
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
	public int getEng() {
		return eng;
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
	
	//STATUS EFFECT GETTERS
	public boolean getStatusEffected() {
		return statusEffected;
	}
	public boolean getPoisoned() {
		if (type == "Machine" || type == "Alien") return false;
		else return poisoned;
	}
	public boolean getBurned() {
		if (burned) {
			poisoned = false;
			hacked = false;
		}
		return burned;
	}
	public boolean getHacked() {
		if (type == "Organic" || type == "Alien") return false;
		else return hacked;
	}
	public boolean getRadio() {
		if (type != "Alien") return radio;
		else return false;
	}
	
	//BASE STATES
	public int getMaxHP() {
		return maxHP;
	}
	public int getMaxTP() {
		return maxTP;
	}
	public int getBasePwr() {
		return basePwr;
	}
	public int getBaseDex() {
		return baseDex;
	}
	public int getBaseEvd() {
		return baseEvd;
	}
	public int getBaseRes() {
		return baseRes;
	}
	public int getBaseTech() {
		return baseTech;
	}
	public int getBaseDef() {
		return baseDef;
	}
	public int getBaseTechDef() {
		return baseTechDef;
	}

	//SETTER METHODS
	public void setHP(int num) {
		hp += num;
		
		if (num < 0) setDP(num);
		else if (num > 0) setCP(num);
		
		if (hp <= 0) {
			hp = 0;
			state = STATES.DEAD;
		}
		else if (hp > maxHP) hp = maxHP;
	}
	public void setTP(int num) {
		tp += num;
		
		if (num < 0) setSP(num);
		else if (num > 0) setRP(num);
		
		if (tp <= 0) tp = 0;
		else if (tp > maxTP) tp = maxTP;
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
	public void setEng(int num) {
		eng = num;
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
	public void setTechMod(int num) {
		techMod = num;
	}
	
	//Stat Effect Setters
	public void setStatusEffected() {
		if (!poisoned && !burned && !hacked && !radio) statusEffected = false;
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
	public void setHacked(boolean bool) {
		hacked = bool;
	}
	public void setRadio(boolean bool) {
		radio = bool;
	}
	
	//Regen
	public void setRegen(int num) {
		regen = num;
	}
	public int getRegen() {
		return regen;
	}
	
	//Experience and Money given
	public int getExp() {
		return expGiven;
	}
	
	public int getMoney() {
		return moneyGiven;
	}
	
	//Chance to flee successfully from mob
	public int getFleeChance() {
		return fleeChance;
	}
	
	//Battle range
	public int getEncounterRange() {
		return encounterRange;
	}
	
	public void setEncounterRange(int num) {
		encounterRange += num;
	}
	
	public void changeEncounterRange(int num) {
		encounterRange = num;
	}
	
	//Monster party size
	public int getNum() {
		return mobNum;
	}
	
	//Change coordinates for the start of a battle
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
		ax = x;
		ay = y;
	}
	
	//Clone
	protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public void startAnimation() {
		animating = true; 
		anim = 0; 
	}
	
	public void setAnimation(boolean bool) {
		animating = bool;
	}
	
	public boolean isAnimating() {
		return animating;
	}
	
	public int getFrame() {
		return anim;
	}

	public boolean hasGone() {
		animating = false;
		return hasGone;
	}
	
	public void setGone(boolean bool) {
		hasGone = bool;
	}
	
	public void changeState(STATES attack) {
		this.state = attack;
	}
	
	//TARGETING
	public Brawler getTarget() {
		return target;
	}
	public Enemy getFriendly() {
		return friendly;
	}
	public int getChoice() {
		return choice;
	}
	public int getChoice2() {
		return choice2;
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
	public void setTarget(Brawler p) {
		target = p;
	}
	public void setFriendly(Enemy e) {
		friendly = e;
	}
	public void setChoice(int num) {
		choice = num;
	}
	public void setChoice2(int num) {
		choice2 = num;
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
	
	//TECHS and SKILLS
	public ArrayList<Tech> getCures() {
		return cureTechs;
	}
	public ArrayList<Tech> getOffs() {
		return offTechs;
	}
	public ArrayList<Tech> getDefs() {
		return defTechs;
	}
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	
	public void setParty(ArrayList<Enemy> enemies) {
		party = enemies;
	}
	public ArrayList<Enemy> getParty() {
		return party;
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
	public void setTech(Tech tech) {
		techChosen = tech;
	}
	public void setSkillChosen(Skill skill) {
		skillChosen = skill;
	}
	
	//STAT EFFECT TIMERS SETTERS
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
		else regenTimer--;
	}
	
	//BATTLE MESSAGE
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String string) {
		message = string;
	}
	
	//MULTI-PARTY
	public boolean getMultiple() {
		return multiple;
	}
	public int getQuantity() {
		return quantity;
	}
	public ArrayList<Enemy> getMultiparty() {
		return multiparty;
	}
	
	//Full Restore
	public void resetStats() {
		hp = maxHP;
		tp = maxTP;
		
		cp = 0; rp = 0; dp = 0; sp = 0;
		
		pwr = basePwr;
		dex = baseDex;
		evd = baseEvd;
		res = baseRes;
		tech = baseTech;
		
		defMod = 100;
		techMod = 100;
		regen = 0;
		
		poisoned = false; burned = false; hacked = false; radio = false;
	}
	
	//Item Drop
	public Item getDrop() {
		return drop;
	}
	
	public int getDropChance() {
		return dropChance;
	}
	
	//Music
	public String getMusic() {
		return music;
	}
}