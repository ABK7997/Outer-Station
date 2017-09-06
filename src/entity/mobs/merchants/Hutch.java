package entity.mobs.merchants;

import graphics.Sprite;
import graphics.SpriteSheet;

public class Hutch extends Merchant {

	public Hutch(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		
		name = "Doc Hutch";
		service = "Skills";
		
		dialogue = dialogue1;
		inquiry = inquiry1;
		
		down = new Sprite(24, 0, 3, SpriteSheet.mainChars);
		
		sprite = down;
	}
	
	public static String[] dialogue1 = new String[] {
		"Why, hello there. ",
		"The name’s Huch. Doc Hutch.",
		"Looks like you’re stuck here, my friend.",
		"There’s not a chance that big old thing’s moving without any power.",
		"Hell if I know where the power went. ",
		"The station’s been experiencing fluctuations for days. ",
		"Can’t even use mobile devices anymore. ",
		"Something sucks the life right out of them."
	};
	
	public static String[] inquiry1 = new String[] {
		"Can I suggest you try to clear a path for us?",
		"The area ahead is littered with wild animals and corrupt drones.",
		"I’ve been stuck here for days. Don’t ask me how slugs inhabited the station.",
		"They make great omelets, though.",
		"Anyway, if you’re going out there, avid the southernmost walkway.",
		"A security mech is standing guard, and I wouldn’t try your odds against it."
	};
	
}
