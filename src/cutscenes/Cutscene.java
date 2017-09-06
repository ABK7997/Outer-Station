package cutscenes;

import java.util.ArrayList;

import states.Dialogue;
import main.Game;
import entity.mobs.npcs.NPC;
import graphics.Screen;

public class Cutscene {

	protected int x, y, range;
	protected int anim = 0;
	protected int block = 24;
	protected boolean animating = false;
	protected boolean pre = true; //Prerequesites for the cutscene to start
	protected boolean finished = false;
	protected String[] dialogue;
	
	public static ArrayList<NPC> characters = new ArrayList<NPC>();
	
	public void update() {
		if (animating) anim++;
		animation();
	}
	
	public void render(Screen screen) {
		for (int i = 0; i < characters.size(); i++) {
			characters.get(i).render(screen);
		}
	}
	
	public void animation() {
	}
	
	//Animation
	public boolean isAnimating() {
		return animating;
	}
	public boolean isFinished() {
		return finished;
	}
	public boolean getPre() {
		return pre;
	}
	public void setPre() {
		pre = true;
	}
	public void startAnimation() {
		Game.state = Game.STATE.CUTSCENE;
		animating = true;
		anim = 0;
	}
	public void endAnimation() {
		Game.state = Game.STATE.GAME;
		animating = false;
		anim = 0;
		
		characters = new ArrayList<NPC>();
	}
	
	//Message
	public String[] getDialogue() {
		return dialogue;
	}
	public void startDialogue(String[] dialogue) {
		Game.state = Game.STATE.DIALOGUE;
		Dialogue.dialogue = dialogue;
	}
	
	//Location
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getRange() {
		return range;
	}
	
}
