package entity.mobs.enemies.organics;

import main.Game;
import entity.mobs.enemies.Enemy;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Rat extends Enemy {
	
	public Rat(int x, int y, int level, int num) { //controls spawn point/location of goon at any given frame		
		this.x = x * 24;
		this.y = y * 24;
		xa = 0;
		ya = 0;
		
		//STATS
		index = 2;
		name = "Rat";
		type = "Organic";
		lv = level;
		expGiven = 10 * level; //Experience awarded for defeating mob
		moneyGiven = random.nextInt(10 * level) + 10;
		
		hp = maxHP = 25 * level;
		tp = maxTP = 0 * level;
		
		pwr = basePwr = 20 * level;
		dex = baseDex = 3 * level;
		evd = baseEvd = 1 * level;
		res = baseRes = 25 * level;
		tech = baseTech = 5 * level;
		def = baseDef = 1;
		techDef = baseTechDef = 2;
		
		fleeChance = 100 - (level * 5);
		mobNum = num;
		
		if (dir == 0) xa = 0;
		else if (dir == 1) ya = 0;
		
		//Movement
		speed = 2;
		
		//Sprites
		up = new Sprite(24, 3, 0, SpriteSheet.organics_1);
		up_1 = new Sprite(24, 3, 1, SpriteSheet.organics_1);
		up_2 = up;
		
		down = new Sprite(24, 0, 0, SpriteSheet.organics_1);
		down_1 = new Sprite(24, 0, 1, SpriteSheet.organics_1);;
		down_2 = down;
		
		right = new Sprite(24, 2, 0, SpriteSheet.organics_1);
		right_1 = new Sprite(24, 2, 1, SpriteSheet.organics_1);
		right_2 = right;
		
		left = new Sprite(24, 1, 0, SpriteSheet.organics_1);
		left_1 = new Sprite(24, 1, 1, SpriteSheet.organics_1);
		left_2 = left;
		
		attack_1 = left;
		attack_2 = new Sprite(24, 0, 2, SpriteSheet.organics_1);
		
		tech_1 = left;
		tech_2 = attack_2;
		
		ill = new Sprite(24, 2, 2, SpriteSheet.organics_1);
		dead = new Sprite(24, 1, 2, SpriteSheet.organics_1);
		hit = new Sprite(24, 3, 2, SpriteSheet.organics_1);
		
		sprite = down;
	}
	
	public void update() {
		if (Game.state == Game.STATE.GAME) {
			randomMovement();
		}
	}
	
	public void behavior() {
		choice = 1;
	}
	
}
