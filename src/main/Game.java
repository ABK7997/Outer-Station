package main;

import graphics.Screen;
import input.Keyboard;
import input.Player;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import level.Level;
import party.Brawler;
import states.Battle;
import states.Death;
import states.Dialogue;
import states.Main;
import states.Menu;
import states.Menu.STATES;
import states.Merchant_State;
import states.Start;
import storage.SaveState;
import storage.Storage;
import entity.mobs.enemies.EnemyParty;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -1459248923413976199L;
	
	//Core Components
	private Thread thread;
	public static Game game;
	private static boolean running = false;
	private Keyboard key;
	public static FullScreen frame;
	
	//Game Components - TBD
	public static Level level;
	public static Player player;
	public static Brawler brawler;
	public static EnemyParty eParty;
	
	//Screen
	private static Screen screen;
	private BufferedImage image;
	private int[] pixels;
	
	//States
	public enum STATE {
		START, GAME, MAIN, MENU, BATTLE, DEAD, MERCHANT, DIALOGUE, CUTSCENE
	};
	
	public static STATE state = STATE.START;
	
	public static boolean renderAll = true;
	
	//State objects - TBD
	private static Start start;
	public static Battle battle;
	private static Main main;
	private static Menu menu;
	private static Merchant_State merchant;
	private static Dialogue dialogue;
	private static Death death;
	
	//Miscellaneous
	public static Storage storage;
	
	//GAME CONSTRUCTOR
	public Game() {
		frame = new FullScreen();
		
		screen = new Screen(frame.getWidth(), frame.getHeight());
		image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		setSize(frame.getWidth(), frame.getHeight());
		frame.add(this);
		
		//Misc
		storage = new Storage(brawler);
		
		key = new Keyboard();
		brawler = new Brawler();
		player = new Player(392, 647, key, level, brawler);
		eParty = new EnemyParty();
		
		//State components
		start = new Start();
		main = new Main();
		menu = new Menu(brawler);
		merchant = new Merchant_State(brawler);
		dialogue = new Dialogue();
		death = new Death();
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				key.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				key.keyReleased(e);
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		
		requestFocus();
	}
	
	//RUN AND STOP
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Main");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join(); //thread stop altogether
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long time = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - time) / ns;
			time = now;
			
			while (delta >= 1) {
				delta = 0;
				update();
			}
			render();
		}
		stop();
	}
	
	public void update() {
		switch (state) {
		case START: start.update(); break;
		case GAME:
			level.update();
			key.update();
			player.update();
			break;
		case MAIN: main.update(); break;
		case MENU: menu.update(); break;
		case BATTLE: battle.update(); break;
		case MERCHANT: merchant.update(); break;
		case DIALOGUE: dialogue.update(); break;
		case CUTSCENE:
			level.update();
			player.update();
			break;
		case DEAD: death.update(); break;
		default: break;
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, frame.getWidth()*3, frame.getHeight()*3, null);
		
		screen.clear(); //erase old pixels each frame
		int xScroll = Player.x - frame.getWidth()/6; int yScroll = Player.y - frame.getHeight()/6; 
		if (Game.state != Game.STATE.START) level.render(xScroll, yScroll, screen);
		
		//STATE-SPECIFIC RENDERING
		switch (state) {
		case START: start.render(g); break;
		case GAME: renderAll = true;
			player.render(screen, g);
			break;
		case MAIN: main.render(g); renderAll = false; break;
		case MENU:
			menu.render(g, screen);
			
			if (Menu.state != STATES.DRONES) {
				player.render(screen, g);
				renderAll = true;
			}
			else renderAll = false;
			break;
		case BATTLE: renderAll = false;
			battle.render(g, screen);
			break;
		case MERCHANT: renderAll = true;
			merchant.render(g);
			player.render(screen, g);
			break;
		case DIALOGUE: 
			player.render(screen, g);
			dialogue.render(g); renderAll = true; 
			break;
		case CUTSCENE:
			player.render(screen, g);
			break;
		case DEAD: 
			death.render(g); 
			renderAll = false; 
			break;
		default: break;
		}
		
		for (int i = 0; i < pixels.length/3; i++) {
			if (i > i/454 && i < i/1380) continue;
			pixels[i] = screen.pixels[i];
		}
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		SaveState.resetAll();
		
		game = new Game();
		game.start();
		
		Level.music.playSound("Main.wav");
	}

}
