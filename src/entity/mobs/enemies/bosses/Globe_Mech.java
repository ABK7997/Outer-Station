package entity.mobs.enemies.bosses;

import battle.def.EM_Field;
import battle.def.Shield;
import battle.off.Super_Shock;
import entity.mobs.enemies.Enemy;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Globe_Mech extends Enemy {
	
	public Globe_Mech(int x, int y, int level, int num) { //controls spawn point/location of goon at any given frame		
		this.x = x * 24;
		this.y = y * 24;
		xa = 0;
		ya = 0;
		
		//STATS
		index = 3;
		name = "Globe Mech";
		type = "Machine";
		lv = level;
		expGiven = 2500 * level; //Experience awarded for defeating mob
		moneyGiven = 5000;
		
		hp = maxHP = 5000 * level;
		tp = maxTP = 400 * level;
		
		pwr = basePwr = 60 * level;
		dex = baseDex = 25 * level;
		evd = baseEvd = 10 * level;
		res = baseRes = 25 * level;
		tech = baseTech = 70 * level;
		def = baseDef = 2;
		techDef = baseTechDef = 1;
		
		encounterRange = 32;
		fleeChance = 100;
		mobNum = num;
		
		if (dir == 0) xa = 0;
		else if (dir == 1) ya = 0;
		
		//Movement
		speed = 1;
		
		//Sprites
		left = new Sprite(48, 0, 0, SpriteSheet.machines_2);
		left_1 = left;
		left_2 = left;
		
		up = left; down = left; right = left;
		
		attack_1 = new Sprite(48, 1, 0, SpriteSheet.machines_2);
		attack_2 = new Sprite(48, 1, 1, SpriteSheet.machines_2);
		
		tech_1 = new Sprite(48, 2, 0, SpriteSheet.machines_2);
		tech_2 = new Sprite(48, 2, 1, SpriteSheet.machines_2);
		
		ill = left;
		dead = new Sprite(48, 0, 1, SpriteSheet.machines_2);
		hit = left;
		
		sprite = down;
		
		moves = new int[] {1, 2, 4};
		
		music = "Boss";
		
		//Techs
		offTechs.add(new Super_Shock(this));
		defTechs.add(new Shield(this));
		defTechs.add(new EM_Field(this));
	}
	
	public void movement() {
		noMovement();
	}
	
	public void behavior() {
		/*
		if (Battle.turnNumber == 0) choice = 1;
		if (Battle.turnNumber == 1) {
			choice = 4;
			techChosen = defTechs.get(0);
		}
		else if (Battle.turnNumber % 3 == 0) choice = 1;
		else {
			choice = random.nextInt(1)+1;
			techChosen = offTechs.get(0);
		}
		if (Battle.turnNumber > 5) Battle.turnNumber = 0;
		*/
		choice = 1;
	}
	
}
