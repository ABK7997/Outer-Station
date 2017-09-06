package entity.mobs.enemies.organics;

import items.Antidote;
import main.Game;
import entity.mobs.enemies.Enemy;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Slug extends Enemy {
	
	public Slug(int x, int y, int level, int num) { //controls spawn point/location of goon at any given frame		
		this.x = x * 24;
		this.y = y * 24;
		xa = 0;
		ya = 0;
		
		//STATS
		index = 0;
		name = "Slug";
		type = "Organic";
		lv = level;
		expGiven = 5 * level; //Experience awarded for defeating mob
		moneyGiven = random.nextInt(5 * level) + 5;
		
		hp = maxHP = 15 * level;
		tp = maxTP = 0 * level;
		
		pwr = basePwr = 10 * level;
		dex = baseDex = 2 * level;
		evd = baseEvd = 1 * level;
		res = baseRes = 45 * level;
		tech = baseTech = 5 * level;
		def = baseDef = 1;
		techDef = baseTechDef = 2;
		
		fleeChance = 100 - (level * 5);
		mobNum = num;
		
		if (dir == 0) xa = 0;
		else if (dir == 1) ya = 0;
		
		//Movement
		speed = 1;
		
		//Sprites
		up = new Sprite(24, 3, 3, SpriteSheet.organics_1);
		up_1 = new Sprite(24, 3, 4, SpriteSheet.organics_1);
		up_2 = up;
		
		down = new Sprite(24, 0, 3, SpriteSheet.organics_1);
		down_1 = new Sprite(24, 0, 4, SpriteSheet.organics_1);;
		down_2 = down;
		
		right = new Sprite(24, 2, 3, SpriteSheet.organics_1);
		right_1 = new Sprite(24, 2, 4, SpriteSheet.organics_1);
		right_2 = right;
		
		left = new Sprite(24, 1, 3, SpriteSheet.organics_1);
		left_1 = new Sprite(24, 1, 4, SpriteSheet.organics_1);
		left_2 = left;
		
		attack_1 = left;
		attack_2 = new Sprite(24, 0, 5, SpriteSheet.organics_1);
		
		tech_1 = left;
		tech_2 = attack_2;
		
		ill = new Sprite(24, 2, 5, SpriteSheet.organics_1);
		dead = new Sprite(24, 1, 5, SpriteSheet.organics_1);
		hit = new Sprite(24, 3, 5, SpriteSheet.organics_1);
		
		sprite = down;
		
		drop = new Antidote(Game.brawler);
		dropChance = 15;
	}
	
	public void movement() {
		randomMovement();
	}
	
	public void behavior() {
		choice = 1;
	}
	
}
