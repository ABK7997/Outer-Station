package states;

import java.awt.Graphics;

import level.Level;
import storage.SaveState;

public class Start extends State {
	
	//States
	private enum STATE {
		MAIN, LOAD, SETTINGS, HTP
	};
	
	private STATE state = STATE.MAIN;
	
	//Options
	private String[] mainList = new String[] {
		"Load Game",
		"New Game",
		"Settings",
		"How to Play",
		"Exit"
	};
	
	private String[] htp = new String[] {
		"Movement: Arrow keys",
		"V: select",
		"C: cancel / go back",
		"X: in-game menu",
		"ESC: main menu - use for saving, reloading, and quitting"
	};

	public Start() {
		
	}
	
	public void update() {
		switch (state) {
		case MAIN:
			if (chosen) {
				chosen = false;
				switch(choice) {
				case 0: 
					try {
						SaveState.loadGame();
						loadLevel();
						Level.restoreChests();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} break;
				case 1: loadLevel(); break;
				case 3: state = STATE.HTP; break;
				case 4: System.exit(0); break;
				}
			}
			break;
		case HTP:
			break;
		case SETTINGS:
			break;
		default:
			break;
		}
		
		if (reset) {
			reset = false;
			state = STATE.MAIN;
		}
	}
	
	public void render(Graphics g) {
		blackScreen(g);
		
		switch (state) {
		case MAIN:
			setFont(g, largeFont);
			g.drawString("Outer Station", 5, 25);
			
			listWithChoice(g, mainList, 20, h/3, largeFont);
			break;
		case HTP:
			setFont(g, largeFont);
			list(g, htp, 5, 50, largeFont); break;
			
		default: break;
		}
	}
	
}
