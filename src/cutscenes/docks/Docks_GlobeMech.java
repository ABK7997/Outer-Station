package cutscenes.docks;

import storage.Story;
import cutscenes.Cutscene;

public class Docks_GlobeMech extends Cutscene { //Game start
	
	public Docks_GlobeMech(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		range = 3 * 24;
		
		dialogue = new String[] {
			"I think this is a bad idea.",
			"I should probably avoid this area.",
		};
	}
	
	public void animation() {
		if (anim == 0);
		else endAnimation();
	}
	
	public void startAnimation() {
		if (Story.docks[1]) return;
		Story.docks[1] = true;
		
		//Misc
		animating = true;
		startDialogue(dialogue);
	}
	
}
