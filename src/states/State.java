package states;

import input.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import level.Level;
import main.Game;

public class State {
	
	public static int choice = 0;
	public static boolean chosen = false;
	public static boolean reset = false;

	//Common elements
	protected Font smallFont = new Font("Times New Roman", Font.BOLD, 14);
	protected Font font = new Font("Times New Roman", Font.BOLD, 18);
	protected Font midFont = new Font("Times New Roman", Font.BOLD, 24);
	protected Font largeFont = new Font("Times New Roman", Font.BOLD, 30);
	protected Font superFont = new Font("Times New Roman", Font.BOLD, 48);
	
	//Dimensions
	protected static int h = Game.frame.getHeight();
	protected static int w = Game.frame.getWidth();
	
	//For every state
	public void update() throws ClassNotFoundException, FileNotFoundException {
		
	}
	
	public void render() {
		
	}
	
	//Common methods
	protected void setFont(Graphics g, Font type) {
		g.setFont(type);
		g.setColor(Color.WHITE);
	}
	
	protected void list(Graphics g, String[] list, int width, int height, Font font) {
		setFont(g, font);
		
		for (int i = 0; i < list.length; i++) {
			g.drawString(list[i], width, height + ((font.getSize()+10) * i));
			g.setColor(Color.WHITE);
		}
	}
	
	protected void listWithChoice(Graphics g, String[] list, int width, int height, Font font) {
		setFont(g, font);
		
		for (int i = 0; i < list.length; i++) {
			if (choice == i) g.setColor(Color.RED);
			g.drawString(list[i], width, height + ((font.getSize()+10) * i));
			g.setColor(Color.WHITE);
			
			if (choice < 0) choice = list.length-1;
			else if (choice > list.length-1) choice = 0;
		}
	}
	
	protected static void blackScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w, h);
	}
	
	public static void reset() {
		choice = 0;
		chosen = false;
		reset = false;
	}
	
	public static void backToGame() {
		Game.state = Game.STATE.GAME;
		reset();
	}
	
	//Start Game
	public static void loadLevel() {
		Game.level = new Level();
		Player.level = Game.level;
		Game.state = Game.STATE.GAME;
		reset();
	}
}
