package states;

import java.util.ArrayList;

import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.organics.*;
import entity.mobs.enemies.machines.*;

public class Bestiary extends State {

	public static ArrayList<Enemy> enemies;
	public static boolean[] encountered = new boolean[100];
	
	public Bestiary() {
		enemies.add(new Slug(0, 0, 1, 1));
		enemies.add(new Droid(0, 0, 1, 1));
		enemies.add(new Rat(0, 0, 1, 1));
	}
	
	public void update() {
		if (choice < 0) choice = enemies.size() - 1;
		else if (choice > enemies.size() - 1) choice = 0;
	}
	
}
