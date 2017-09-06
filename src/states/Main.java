package states;

import java.awt.Graphics;
import java.io.FileNotFoundException;

import storage.SaveState;

public class Main extends State {

	private enum STATE {
		MAIN, LOAD, SAVE
	}
	
	private STATE state = STATE.MAIN;
	
	//options
	private String[] mainList = new String[] {
		"Resume",
		"Save Game",
		"Quit"
	};
	
	public Main() {
		
	}
	
	public void update() {
		switch (state) {
		case MAIN:
			if (chosen) {
				switch(choice) {
				case 0: backToGame(); break;
				case 1: try {
						SaveState.saveGame();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} break;
				case 2: System.exit(0); break;
				}
				reset();
			}
			break;
		case LOAD: break;
		case SAVE: break;
		default:
			break;
		}
		
		if (reset) backToGame();
	}
	
	public void render(Graphics g) {
		blackScreen(g);
		
		switch (state) {
		case MAIN:
			setFont(g, largeFont);
			g.drawString("MAIN MENU", 5, 25);
			
			listWithChoice(g, mainList, 20, h/3, largeFont);
			break;
			
		default: break;
		}
	}
	
}
