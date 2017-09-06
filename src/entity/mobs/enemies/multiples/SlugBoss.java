package entity.mobs.enemies.multiples;

import storage.Story;
import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.bosses.Slug_Mother;
import entity.mobs.enemies.organics.Slug;
import graphics.Sprite;
import graphics.SpriteSheet;

public class SlugBoss extends Enemy {
	
	public SlugBoss(int x, int y, int level, int quantity) {
		this.x = x * 24;
		this.y = y * 24;
		
		this.quantity = quantity;
		
		left = new Sprite(48, 0, 0, SpriteSheet.organics_2);
		up = left;
		down = left;
		right = left;
		
		multiparty.add(new Slug_Mother(0, 0, level, 1));
		multiparty.add(new Slug(0, 0, level, 1));
		multiparty.add(new Slug(0, 0, level, 1));
		
		encounterRange = 48;
		speed = 0;
		
		multiple = true;
		
		sprite = left;
	}
	
	public void movement() {
		noMovement();
		if (multiparty.get(0).getRemoved()) {
			kill(); //Ensures removal after battle
			Story.bossesDefeated[1] = true;
			Story.docks_Story = 1;
		}
	}
	
}
