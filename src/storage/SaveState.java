package storage;

import input.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import level.Level;
import main.Game;
import party.Brawler;
import party.drones.Drone;
import states.Merchant_State;

public class SaveState {
	
	//Player
	private static Brawler player = Game.brawler;
	
	//Drones
	public static boolean[] droneIndex = new boolean[10];
	
	//Weapons
	public static boolean[] weaponIndex = new boolean[18];
	private static int currentWeapon;
	
	//Suits
	public static boolean[] suitIndex = new boolean[8];
	private static int currentSuit;
	
	//Skills
	public static boolean[] skillIndex = new boolean[13];
	private static int s1, s2, s3;
	
	//Techs
	public static boolean[] offIndex = new boolean[10];
	public static boolean[] cureIndex = new boolean[10];
	public static boolean[] defIndex = new boolean[6];
	
	//Inventory
	public static boolean[] itemIndex = new boolean[21];
	public static int[] itemStocks = new int[21];
	
	//Level
	public static boolean[] chestsOpened = new boolean[Level.chests.size()];
	public static boolean[] bossesDefeated = new boolean[0];
	
	public static void resetIndex(boolean[] index) {
		for (int i = 0; i < index.length; i++) {
			index[i] = false;
		}
	}
	public static void resetIndex2(int[] index) {
		for (int i = 0; i < index.length; i++) {
			index[i] = 0;
		}
	}
	public static void resetAll() {
		resetIndex(droneIndex);
		resetIndex(weaponIndex);
		resetIndex(suitIndex);
		resetIndex(offIndex);
		resetIndex(cureIndex);
		resetIndex(defIndex);
		resetIndex(skillIndex);
		resetIndex(itemIndex);
		resetIndex(chestsOpened);
		resetIndex(bossesDefeated);
		resetIndex2(itemStocks);
		
		//Story elements
		resetIndex(Story.docks);
		resetIndex(Story.mall);
	}
	
	//RESTORE
	public static void restoreDrones() {
		Merchant_State.drones = new ArrayList<Drone>();
		
		for (int i = 0; i < droneIndex.length; i++) {
			if (droneIndex[i] == true) {
				player.addDrone(Storage.allDrones.get(i));
				
				Merchant_State.drones.add(Storage.allDrones.get(i));
			}
		}
	}
	public static void restoreWeapons() {
		for (int i = 0; i < weaponIndex.length; i++) {
			if (weaponIndex[i] == true) {
				player.addWeapon(Storage.allWeapons.get(i));
				Merchant_State.weapons.add(Storage.allWeapons.get(i));
			}
		}
		player.switchWeapon(Storage.allWeapons.get(currentWeapon));
	}
	public static void restoreSuits() {
		for (int i = 0; i < suitIndex.length; i++) {
			if (suitIndex[i] == true) {
				player.addSuit(Storage.allSuits.get(i));
				Merchant_State.suits.add(Storage.allSuits.get(i));
			}
		}
		player.switchSuit(Storage.allSuits.get(currentSuit));
	}
	public static void restoreSkills() {
		for (int i = 0; i < skillIndex.length; i++) {
			if (skillIndex[i] == true) {
				Merchant_State.skills.add(Storage.allSkills.get(i));
			}
		}
		player.learnSkill(Storage.allSkills.get(s1));
		player.learnSkill(Storage.allSkills.get(s2));
		player.learnSkill(Storage.allSkills.get(s3));
	}
	public static void restoreOffs() {
		for (int i = 0; i < offIndex.length; i++) {
			if (offIndex[i] == true) {
				player.learnTech(Storage.offTechs.get(i));
				Merchant_State.offTechs.add(Storage.offTechs.get(i));
			}
		}
	}
	public static void restoreCures() {
		for (int i = 0; i < cureIndex.length; i++) {
			if (cureIndex[i] == true) {
				player.learnTech(Storage.cureTechs.get(i));
				Merchant_State.cureTechs.add(Storage.cureTechs.get(i));
			}
		}
	}
	public static void restoreDefs() {
		for (int i = 0; i < defIndex.length; i++) {
			if (defIndex[i] == true) {
				player.learnTech(Storage.defTechs.get(i));
				Merchant_State.defTechs.add(Storage.defTechs.get(i));
			}
		}
	}
	public static void restoreInventory() {
		for (int i = 0; i < itemIndex.length; i++) {
			if (itemIndex[i] == true) {
				player.addItem(Storage.allItems.get(i));
			}
		}
		
		for (int i = 0; i < itemStocks.length; i++) {
			if (itemStocks[i] > 0) player.getInventory().get(i).stock(itemStocks[i]-1);
		}
	}
	public static void restoreChests() {
		for (int i = 0; i < Level.chests.size(); i++) {
			if (chestsOpened[i]) {
				Level.chests.get(i).open();
			}
		}
	}
	
	//SAVE INDECES
	public static void saveDrones() {
		for (int i = 0; i < player.getDrones().size(); i++) {
			Drone d = player.getDrones().get(i);
			
			droneIndex[d.getIndex()] = true;
		}
	}
	public static void saveWeapons() {
		for (int i = 0; i < player.getWeapons().size(); i++) {
			weaponIndex[player.getWeapons().get(i).getIndex()] = true;
		}
	}
	public static void saveSuits() {
		for (int i = 0; i < player.getSuits().size(); i++) {
			suitIndex[player.getSuits().get(i).getIndex()] = true;
		}
	}
	public static void saveInventory() {
		itemStocks = new int[player.getInventory().size()+1];
		
		for (int i = 0; i < player.getInventory().size(); i++) {
			itemIndex[player.getInventory().get(i).getIndex()] = true;
			itemStocks[i] = player.getInventory().get(i).getStock();
		}
	}
	public static void saveSkills() {
		for (int i = 0; i < Merchant_State.skills.size(); i++) {
			skillIndex[Merchant_State.skills.get(i).getIndex()] = true;
		}
		s1 = player.getSkills().get(0).getIndex();
		s2 = player.getSkills().get(1).getIndex();
		s3 = player.getSkills().get(2).getIndex();
	}
	public static void saveOffs() {
		for (int i = 0; i < player.getOffs().size(); i++) {
			offIndex[player.getOffs().get(i).getIndex()] = true;
		}
	}
	public static void saveCures() {
		for (int i = 0; i < player.getCures().size(); i++) {
			cureIndex[player.getCures().get(i).getIndex()] = true;
		}
	}
	public static void saveDefs() {
		for (int i = 0; i < player.getDefs().size(); i++) {
			defIndex[player.getDefs().get(i).getIndex()] = true;
		}
	}
	public static void saveChests() {
		chestsOpened = new boolean[Level.chests.size()];
		
		for (int i = 0; i < Level.chests.size(); i++) {
			chestsOpened[i] = Level.chests.get(i).getOpened();
		}
	}

	//SAVE PROCESS
	public static void saveGame() throws FileNotFoundException {
		player = Game.brawler;
		
		saveDrones();
		saveWeapons();
		saveSuits();
		saveInventory();
		saveSkills();
		saveOffs();
		saveCures();
		saveDefs();
		saveChests();
		
		try {
			FileOutputStream saveFile = new FileOutputStream("Save1.sav");
			
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			
			//Location
			save.writeObject(Player.x);
			save.writeObject(Player.y);
			
			save.writeObject(Player.money);
			
			//Player stats
			save.writeObject(player.getLevel());
			save.writeObject(player.getExp());
			save.writeObject(player.getNextLv());
			
			save.writeObject(player.getHP());
			save.writeObject(player.getMaxHP());
			save.writeObject(player.getTP());
			save.writeObject(player.getMaxTP());
			
			save.writeObject(player.getBasePwr());
			save.writeObject(player.getBaseDex());
			save.writeObject(player.getBaseEvd());
			save.writeObject(player.getBaseRes());
			save.writeObject(player.getBaseTech());
			
			//Indeces
			save.writeObject(droneIndex);
			
			save.writeObject(weaponIndex);
			save.writeObject(player.getWeapon().getIndex());
			
			save.writeObject(suitIndex);
			save.writeObject(player.getSuit().getIndex());
			
			save.writeObject(skillIndex);
			save.writeObject(s1);
			save.writeObject(s2);
			save.writeObject(s3);
			
			save.writeObject(offIndex);
			save.writeObject(cureIndex);
			save.writeObject(defIndex);
			
			save.writeObject(itemIndex);
			save.writeObject(itemStocks);
			
			save.writeObject(chestsOpened);
			
			for (int i = 0; i < player.getDrones().size(); i++) {
				save.writeObject(player.getDrones().get(i).getBattery());
				save.writeObject(player.getDrones().get(i).getRecharge());
			}
			
			//Bosses
			save.writeObject(Story.bossesDefeated);
			
			//Story elements
			save.writeObject(Story.docks);
			save.writeObject(Story.mall);
			
			save.writeObject(Story.docks_Story);
			save.writeObject(Story.mall_Story);
			
			save.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadGame() throws ClassNotFoundException {
		player = Game.brawler;
		
		FileInputStream saveFile;
		try {
			saveFile = new FileInputStream("Save1.sav");
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			
			//Location
			Player.x = (int) restore.readObject();
			Player.y = (int) restore.readObject();
			
			Player.money = (int) restore.readObject();
			
			//Player Stats
			player.restoreLevel((int) restore.readObject());
			player.restoreExp((int) restore.readObject());
			player.restoreNextLevel((int) restore.readObject());
			
			player.reviveHP((int) restore.readObject());
			player.restoreMaxHP((int) restore.readObject());
			player.restoreTP((int) restore.readObject());
			player.restoreMaxTP((int) restore.readObject());
			
			player.restorePwr((int) restore.readObject());
			player.restoreDex((int) restore.readObject());
			player.restoreEvd((int) restore.readObject());
			player.restoreRes((int) restore.readObject());
			player.restoreTech((int) restore.readObject());
			
			//Indeces
			droneIndex = (boolean[]) restore.readObject();
			
			weaponIndex = (boolean[]) restore.readObject();
			currentWeapon = (int) restore.readObject();
			
			suitIndex = (boolean[]) restore.readObject();
			currentSuit = (int) restore.readObject();
			
			skillIndex = (boolean[]) restore.readObject();
			s1 = (int) restore.readObject();
			s2 = (int) restore.readObject();
			s3 = (int) restore.readObject();
			
			offIndex = (boolean[]) restore.readObject();
			cureIndex = (boolean[]) restore.readObject();
			defIndex = (boolean[]) restore.readObject();
			
			itemIndex = (boolean[]) restore.readObject();
			itemStocks = (int[]) restore.readObject();
			
			chestsOpened = (boolean[]) restore.readObject();
			
			restoreDrones();
			
			for (int i = 0; i < player.getDrones().size(); i++) {
				player.getDrones().get(i).startBattery((int) restore.readObject());
				player.getDrones().get(i).restoreRecharge((int) restore.readObject());
			}
			
			//Bosses
			Story.bossesDefeated = (boolean[]) restore.readObject();
			
			//Story elements
			Story.docks = (boolean[]) restore.readObject();
			Story.mall = (boolean[]) restore.readObject();
			
			Story.docks_Story = (int) restore.readObject();
			Story.mall_Story = (int) restore.readObject();
			
			restore.close();
			
			restoreWeapons();
			restoreSuits();
			restoreSkills();
			restoreOffs();
			restoreCures();
			restoreDefs();
			restoreInventory();
			restoreChests();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
