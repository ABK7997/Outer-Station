package entity.mobs.enemies.bosses;

import main.Game;
import states.Battle;
import battle.off.Fumes;
import entity.mobs.enemies.Enemy;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Slug_Mother extends Enemy {
	
	public Slug_Mother(int x, int y, int level, int num) { //controls spawn point/location of goon at any given frame		
		this.x = x * 24;
		this.y = y * 24;
		xa = 0;
		ya = 0;
		
		//STATS
		index = 0;
		name = "Slug Mother";
		type = "Organic";
		lv = level;
		expGiven = 33 * level; //Experience awarded for defeating mob
		moneyGiven = 333;
		
		hp = maxHP = 20 * level;
		tp = maxTP = 100 * level;
		
		pwr = basePwr = 20 * level;
		dex = baseDex = 5 * level;
		evd = baseEvd = 1 * level;
		res = baseRes = 100 * level;
		tech = baseTech = 5 * level;
		def = baseDef = 1;
		techDef = baseTechDef = 2;
		
		fleeChance = 0;
		mobNum = num;
		
		if (dir == 0) xa = 0;
		else if (dir == 1) ya = 0;
		
		//Movement
		speed = 1;
		encounterRange = 48;
		
		//Sprites
		left = new Sprite(48, 0, 0, SpriteSheet.organics_2);
		left_1 = left;
		left_2 = left;
		
		attack_1 = left;
		attack_2 = new Sprite(48, 1, 0, SpriteSheet.organics_2);
		
		tech_1 = new Sprite(48, 0, 1, SpriteSheet.organics_2);
		tech_2 = attack_2;
		
		ill = left;
		dead = new Sprite(48, 1, 1, SpriteSheet.organics_2);
		hit = attack_2;
		
		down = left;
		up = left;
		right = left;
		
		sprite = left;
		
		music = "Boss";
		
		offTechs.add(new Fumes(this));
		
		moves = new int[] {1, 2};
	}
	
	public void update() {
		if (Game.state == Game.STATE.GAME) {
		}
	}
	
	public void behavior() {
		if (Battle.turnNumber % 6 == 0 && tp > 0) choice = 2;
		else choice = 1;
		
		techChosen = offTechs.get(0);
	}
	
}
