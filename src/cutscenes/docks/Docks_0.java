package cutscenes.docks;

import storage.Story;
import level.Level;
import input.Player;
import cutscenes.Cutscene;

public class Docks_0 extends Cutscene { //Game start
	
	public Docks_0(int x, int y) {
		this.x = x * 24;
		this.y = y * 24;
		range = 5 * 24;
		
		dialogue = new String[] {
			"It was your third year on the job.",
			"The DSIS had been good to you so far.",
			"And then this assignment...",
			"You came to investigate a mysterious signal.",
			"You could tell the brass wasn't telling you everything...",
			"...but who were you to question it?",
			"Just another mission, right?",
			"You pulled into Station PSI-9 and then lost control of the ship.",
			"The moment you finally managed to dock, the power went out.",
			"Looks like the power's out everywhere.",
			"PROLOGUE: Welcome to the Station"
		};
	}
	
	public void animation() {
		if (anim == 0);
		else {
			endAnimation();
			Level.music.playSound("Docks.wav");
		}
	}
	
	public void startAnimation() {
		if (Story.docks[0]) return;
		Story.docks[0] = true;
		
		//Misc
		animating = true;
		Player.dir = 1;
		Level.music.playSound("Main.wav");
		startDialogue(dialogue);
	}
	
}
