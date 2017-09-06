package states;

import java.awt.Color;
import java.awt.Graphics;

public class Death extends State {

	public Death() {
	}
	
	private String[] options = new String[] {
		"Quit Game"
	};
	
	public void update() {
		if (chosen) {
			if (choice == 0) System.exit(0);
			reset();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(w/3, 0, w/3, h/4);
		
		setFont(g, largeFont);
		g.drawString("Mission Failed.", w/3+5, 25);
		
		listWithChoice(g, options, w/3+5, 100, largeFont);
	}
	
}
