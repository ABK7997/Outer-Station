package cutscenes.mall;

import storage.Story;
import cutscenes.Cutscene;

public class Mall_0 extends Cutscene { //Game start
	
	public Mall_0(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		range = 1 * 24;
		
		dialogue = new String[] {
			"MISSION I: The Metal Fist"
		};
	}
	
	public void animation() {
		if (anim == 0);
		else endAnimation();
	}
	
	public void startAnimation() {
		if (Story.mall[0]) return;
		Story.mall[0] = true;
		
		//Misc
		animating = true;
		startDialogue(dialogue);
	}
	
}
