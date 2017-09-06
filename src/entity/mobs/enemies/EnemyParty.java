package entity.mobs.enemies;

import java.util.ArrayList;

public class EnemyParty {

	public static ArrayList<Enemy> eParty = new ArrayList<Enemy>();
	
	public EnemyParty() {
	}
	
	//Alter party at the beginning of a new battle scenario
	public static void changeParty(Enemy e, int num) {
		eParty = new ArrayList<Enemy>();
		eParty.add(e);
		for (int i = 0; i < num-1; i++) {
			try {
				eParty.add((Enemy)e.clone());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void changeParty(Enemy e) {
		eParty = new ArrayList<Enemy>();
		eParty.add(e);
	}
	
	public static void addEnemy(Enemy e) {
		eParty.add(e);
	}
	
}
