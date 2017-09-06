package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import states.Battle;
import states.Menu;
import states.Menu.STATES;
import states.Merchant_State;
import states.State;
import main.Game;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	
	//Keys
	private final int l = 37;
	private final int u = 38;
	private final int r = 39;
	private final int d = 40;
	
	private final int v = 86;
	private final int c = 67;
	private final int x = 88;
	//private final int z = 90;
	
	private final int esc = 27;
	
	public void update() {
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
		
		if (Game.state == Game.STATE.CUTSCENE) {
			up = false;
			down = false;
			left = false;
			right = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {	
		keys[e.getKeyCode()] = true;
		
		int k = e.getKeyCode();
		
		//System.out.println(k);
		
		switch(Game.state) {
		case START:
			switch(k) {
			case v: State.chosen = true; break;
			case c: State.reset = true; break;
			
			case u: State.choice--; break;
			case d: State.choice++; break;
			case l: State.choice--; break;
			case r: State.choice++; break;
			
			} break;
		case GAME:
			switch(k) {
			case esc: Game.state = Game.STATE.MAIN; break;
			case x: Game.state = Game.STATE.MENU; return;
			case v: Player.chosen = true; return;
			
			default: break;
			} break;
			
		case MAIN: 
			switch(k) {
			case esc: State.backToGame(); break;
			case c: State.reset = true; break;
			
			case v: State.chosen = true; break;
			
			case u: State.choice--; break;
			case d: State.choice++; break;
			case l: State.choice--; break;
			case r: State.choice++; break;
			
			} break;

		case MENU:
			switch(k) {
			case x: State.backToGame(); break;
			case v: State.chosen = true; break;
			case c: State.reset = true; break;
			
			case u: State.choice--;	break;
			case d: State.choice++; break;
			
			case l: if (Menu.state != STATES.TECHS && Menu.state != STATES.EQUIPMENT) State.choice--;
			else Menu.techChoice--; break; 
			
			case r: if (Menu.state != STATES.TECHS && Menu.state != STATES.EQUIPMENT) State.choice++;
			else Menu.techChoice++; break; 
			} break; 
			

		case BATTLE:
			if (Battle.phase == Battle.PHASE.TECH) {
				switch(k) {
				case v: State.chosen = true; break;
				
				case u: State.choice--; break;
				case d: State.choice++; break;
				case l: Battle.techChoice--; break;
				case r: Battle.techChoice++; break;
				
				case c: Battle.reset = true; break;
				}
			}
			
			else {
				switch(k) {
			
				case v: State.chosen = true; break;
				
				case u: State.choice--; break;
				case d: State.choice++; break;
				case l: State.choice -= 3; break;
				case r: State.choice += 3; break;
				
				case c: Battle.reset = true; break;
			
				default: break;
				}
			}
			break;
			
		case MERCHANT:
			switch(k) {
			case v: State.chosen = true; break;
			case c: State.reset = true; break;
			
			case u: State.choice--; break;
			case d: State.choice++; break;
			
			case l: Merchant_State.techChoice--; break;
			case r: Merchant_State.techChoice++; break;
			} 
			break;
			
		case DEAD: 
			switch(k) {
			case v: State.chosen = true; break;
			
			case u: State.choice--; break;
			case d: State.choice++; break;
			case l: State.choice--; break;
			case r: State.choice++; break;
			
			} break;
		
		case DIALOGUE: if (k == v) State.chosen = true; break;
		
		default: break;
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) { //UNUSED
	}

}
