package entity.mobs.enemies.machines;

import items.Medkit;
import battle.cure.Heal;
import battle.def.Shield;
import battle.off.Shock;
import entity.mobs.enemies.Enemy;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Droid extends Enemy {
	
	public Droid(int x, int y, int level, int num) { //controls spawn point/location of goon at any given frame		
		this.x = x * 24;
		this.y = y * 24;
		xa = 0;
		ya = 0;
		
		//STATS
		index = 1;
		name = "Droid";
		type = "Machine";
		lv = level;
		expGiven = 6 * level; //Experience awarded for defeating mob
		moneyGiven = random.nextInt(6 * level) + 6;
		
		hp = maxHP = 15 * level;
		tp = maxTP = 0 * level;
		
		pwr = basePwr = 10 * level;
		dex = baseDex = 4 * level;
		evd = baseEvd = 2 * level;
		res = baseRes = 1 * level;
		tech = baseTech = 1 * level;
		def = baseDef = 2;
		techDef = baseTechDef = 1;
		
		fleeChance = 100 - (level * 5);
		mobNum = num;
		
		if (dir == 0) xa = 0;
		else if (dir == 1) ya = 0;
		
		//Movement
		speed = 1;
		
		//Sprites
		up = new Sprite(24, 3, 0, SpriteSheet.machines_1);
		up_1 = up;
		up_2 = up;
		
		down = new Sprite(24, 0, 0, SpriteSheet.machines_1);
		down_1 = down;
		down_2 = down;
		
		right = new Sprite(24, 1, 0, SpriteSheet.machines_1);
		right_1 = right;
		right_2 = right;
		
		left = new Sprite(24, 2, 0, SpriteSheet.machines_1);
		left_1 = left;
		left_2 = left;
		
		attack_1 = new Sprite(24, 0, 1, SpriteSheet.machines_1);
		attack_2 = new Sprite(24, 0, 2, SpriteSheet.machines_1);
		
		tech_1 = attack_1;
		tech_2 = attack_2;
		
		ill = new Sprite(24, 1, 1, SpriteSheet.machines_1);
		dead = new Sprite(24, 3, 1, SpriteSheet.machines_1);
		hit = new Sprite(24, 2, 1, SpriteSheet.machines_1);
		
		sprite = down;
		
		drop = new Medkit(target);
		dropChance = 10;
		
		offTechs.add(new Shock(this));
		cureTechs.add(new Heal(this));
		defTechs.add(new Shield(this));
	}
	
	public void movement() {
		randomMovement();
	}
	
	public void behavior() {
		choice = 2;
		techChosen = offTechs.get(0);
	}
	
}
