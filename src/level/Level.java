package level;

import items.Battery;
import items.Medkit;
import items.Super_Kit;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import cutscenes.Cutscene;
import cutscenes.docks.*;
import cutscenes.mall.Mall_0;
import party.Brawler;
import party.drones.*;
import party.equip.*;
import storage.SaveState;
import storage.Story;
import main.Game;
import music.Music_Player;
import assets.Chest;
import entity.Entity;
import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.bosses.Globe_Mech;
import entity.mobs.enemies.machines.*;
import entity.mobs.enemies.multiples.*;
import entity.mobs.enemies.organics.*;
import entity.mobs.merchants.*;
import entity.mobs.npcs.NPC;
import entity.mobs.npcs.Trader;
import graphics.BasicSprites;
import graphics.Screen;
import graphics.tiles.Solid;
import graphics.tiles.Tile;
import graphics.tiles.UnSolid;

public class Level {
	
	private Brawler b;
	
	private static int width;
	private static int height;
	private static int[] tiles;
	//private String levelMap;
	private String path = "station.png";
	private String mapPath;
	public static String area = "Docks";
	
	//Entities
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<Merchant> merchants = new ArrayList<Merchant>();
	public static ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	//Misc
	public static ArrayList<Cutscene> cutscenes = new ArrayList<Cutscene>();
	public static ArrayList<Chest> chests = new ArrayList<Chest>();
	
	public static Music_Player music = new Music_Player();
	
	public Level() {
		loadLevel(path);
		loadDocks();
		loadMall();
	}
	
	private void loadLevel(String path) {	
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			
			e.update();
			if (e.getRemoved()) enemies.remove(e);
		}
		for (int i = 0; i < merchants.size(); i++) {
			Merchant m = merchants.get(i);
			m.update();
		}
		for (int i = 0; i < npcs.size(); i++) {
			NPC n = npcs.get(i);
			n.update();
		}
		
		for (int i = 0; i < cutscenes.size(); i++) {
			if (cutscenes.get(i).isAnimating()) cutscenes.get(i).update();
		}
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffsets(xScroll, yScroll);
		int x0 = xScroll / 24; //left edge of the screen >>4
		int x1 = (xScroll + screen.width) / 24; //right edge
		int y0 = yScroll / 24; //top edge
		int y1 = (yScroll + screen.height) / 24; //bottom edge
			
		for (int y = y0 - 1; y < y1 + 1; y++) {
			for (int x = x0 - 1; x < x1 + 1; x++) {
				if (x + y * 24 > 0 || x + y * 24 <= 20 * 30) {
					getTile(x, y).render(x, y, screen);
				}
			}
		}
		
		if (Game.renderAll) {
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).render(screen);
			}
			for (int i = 0; i < merchants.size(); i++) {
				merchants.get(i).render(screen);
			}
			for (int i = 0; i < npcs.size(); i++) {
				npcs.get(i).render(screen);
			}
			
			for (int i = 0; i < chests.size(); i++) {
				chests.get(i).render(screen);
			}
		}
	}
	
	public static void add(Entity e) {
		if (e instanceof Enemy) {
			enemies.add((Enemy) e);
		}
		if (e instanceof Merchant) {
			merchants.add((Merchant) e);
		}
		if (e instanceof NPC) {
			npcs.add((NPC) e);
		}
	}
	
	public void remove() {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getRemoved()) enemies.remove(enemies.get(i));
		}
	}
	
	public static void restoreChests() {
		for (int i = 0; i < chests.size(); i++) {
			if (SaveState.chestsOpened[i]) Level.chests.get(i).open();
		}
	}
	
	//TILES
	public static Tile Void = new Solid(BasicSprites.space);
	public static Tile Floor = new UnSolid(BasicSprites.floor);
	public static Tile Wall = new Solid(BasicSprites.wall);
	public static Tile Bridge = new UnSolid(BasicSprites.bridge);
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x>= width || y >= height) return Void;
		
		switch(tiles[x + y * width]) {
		case 0xffC0C0C0: return Floor;
		case 0xff404040: return Wall;
		case 0xff000000: return Bridge;
		
		default: return Void;
		}
	}
	
	public String getFile() {
		return path;
	}
	
	public String getMap() {
		return mapPath;
	}
	
	public static int[] getTiles() {
		return tiles;
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public void clearAssets(){
		enemies = new ArrayList<Enemy>();
	}
	
	public void loadDocks() {
		//Cutscenes
		cutscenes.add(new Docks_0(391, 647));
		cutscenes.add(new Docks_GlobeMech(414, 678));
		
		//Merchants
		if (Story.docks_Story == 0) add(new Hutch(395, 645)); //Moves to the Mall after the Slug Mother is defeated
		
		//Starting Room
		Medkit m = new Medkit(b);
		chests.add(new Chest(388, 654, m));
		chests.add(new Chest(389, 654, m));
		chests.add(new Chest(390, 654, m));
			
		Battery bat = new Battery(b);
		chests.add(new Chest(395, 654, bat));
		chests.add(new Chest(396, 654, bat));
		
		//Room 1
		add(new Slug(391, 661, 1, 1));
		add(new Slug(395, 670, 1, 2));
		chests.add(new Chest(396, 670, new Assault_Drone(b)));
		
		//Central Room
		add(new Droid(407, 666, 1, 1));
		add(new Droid(416, 664, 1, 1));
		add(new Droid(416, 671, 1, 2));
		add(new Droid(412, 664, 1, 2));
		
		//Reception
		add(new RatDroid(434, 662, 1, 0));
		add(new RatDroid(432, 658, 1, 0));
		add(new RatDroid(436, 650, 1, 0));
		
		//Side-Path
		add(new Slug(442, 673, 1, 2));
		add(new Slug(442, 673, 1, 2));
		add(new Slug(444, 673, 1, 2));
		add(new Slug(444, 673, 1, 2));
		add(new Slug(442, 675, 1, 2));
		
		add(new Slug(448, 683, 1, 3));
		add(new Slug(438, 686, 1, 3));
		add(new Droid(438, 683, 1, 3));
		add(new Droid(448, 686, 1, 3));
		chests.add(new Chest(445, 672, new Super_Kit(b)));
		chests.add(new Chest(448, 686, new Rugged_Suit(b)));
		
		//Boss
		if (!Story.bossesDefeated[0]) add(new Globe_Mech(414, 682, 1, 1));
		if (Story.docks_Story == 0) add(new SlugBoss(416, 658, 1, 3));
		chests.add(new Chest(415, 653, new Shock_Drone(b)));
		
		music.playSound(area + ".wav");
	}

	public void loadMall() {
		//Merchants
		add(new Hutch(412, 623));
		add(new Wattson(417, 587));
		add(new OConner(434, 598));
		add(new Circuit(432, 587));
		add(new Gyro(437, 582));
		
		//Cutscenes
		cutscenes.add(new Mall_0(412, 626));
	}
	
	public void loadDemo() {
		//Enemies
		add(new RatDroid(495, 905, 1, 0));
		
		//Merchants
		add(new Circuit(488, 897));
		add(new Gyro(490, 897));
		add(new Hutch(492, 897));
		add(new OConner(494, 897));
		add(new Smith(500, 897));
		add(new Wattson(502, 897));
		add(new Troy(498, 897));
		
		//NPC
		add(new Trader(500, 900, Trader.dialogue1));
		
		//Chests
		chests.add(new Chest(490, 900, new Medic_Drone(Game.brawler)));
	}
}
