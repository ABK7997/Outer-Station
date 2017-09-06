package states;

import graphics.Screen;
import input.Player;
import items.Item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Game;
import party.Brawler;
import party.drones.Drone;
import party.equip.Suit;
import party.weapons.Weapon;
import battle.Skill;
import battle.Tech;

public class Menu extends State {
	
	public static enum STATES {
		MAIN, DRONES, TECHS, INVENTORY, EQUIPMENT
	}
	
	public static STATES state = STATES.MAIN;
	
	private String[] mainOptions = new String[] {
		"Resume", 
		"Drones",
		"Techs / Skills",
		"Inventory",
		"Equipment"
	};
	
	Brawler player;
	private ArrayList<Drone> drones;
	private String[] dronesList;
	private int droneSelection = 1;
	
	public static int techChoice = 0;
	
	public Menu(Brawler brawler) {
		player = brawler;
		drones = player.getDrones();
	}
	
	public void update() {
		switch(state) {
		case MAIN:
			if (chosen) {
				switch(choice) {
				case 0: backToGame(); break;
				case 1: if (player.getDrones().size() > 0) state = STATES.DRONES; fillDrones(); break;
				case 2: state = STATES.TECHS; break;
				case 3: if (player.getInventory().size() > 0) state = STATES.INVENTORY; break;
				case 4: state = STATES.EQUIPMENT; break;
				}
				reset();
			}
			break;
			
		case DRONES:
			if (chosen) {
				if (droneSelection == 1) {
					player.setDrone1(drones.get(choice));
					droneSelection *= -1;
				}
				else if (choice != player.getDrone1().getIndex()) {
					player.setDrone2(drones.get(choice));
					droneSelection *= -1;
				}
				chosen = false;
			}
			break;
			
		case TECHS:
			if (techChoice < 0) techChoice = 3;
			else if (techChoice > 3) techChoice = 0;
			
			switch (techChoice) {
			case 0: 
				if (choice > player.getOffs().size()-1) choice = 0;
				else if (choice < 0) choice = player.getOffs().size()-1;
				break;
			case 1: 
				if (choice > player.getCures().size()-1) choice = 0;
				else if (choice < 0) choice = player.getCures().size()-1;
				break;
			case 2: 
				if (choice > player.getDefs().size()-1) choice = 0;
				else if (choice < 0) choice = player.getDefs().size()-1;
				break;
			case 3: 
				if (choice < 0) choice = player.getSkills().size() - 1;
				else if (choice > player.getSkills().size() - 1) choice = 0;
				break;
			}
			break;
			
		case INVENTORY: 
			if (choice > player.getInventory().size() - 1) choice = 0;
			else if (choice < 0) choice = player.getInventory().size() - 1;
			
			if (chosen && player.getInventory().get(choice).getStock() > 0) {
				player.getInventory().get(choice).useItem();
			}
			
			break;
			
		case EQUIPMENT:
			if (techChoice < 0) techChoice = 1;
			else if (techChoice > 1) techChoice = 0;
			
			if (techChoice == 0) {	
				if (choice < 0) choice = player.getWeapons().size() - 1;
				else if (choice > player.getWeapons().size() - 1) choice = 0;
				
				if (chosen) player.switchWeapon(player.getWeapons().get(choice));
			}
			
			else {
				if (choice < 0) choice = player.getSuits().size() - 1;
				else if (choice > player.getSuits().size() - 1) choice = 0;
				
				if (chosen) player.switchSuit(player.getSuits().get(choice));
			}
			
			break;
			
		default:
			break;
		}
		if (reset) {
			reset();
			
			switch(state) {
			case DRONES: choice = 1; break;
			case EQUIPMENT: choice = 4; break;
			case INVENTORY: choice = 3; break;
			case TECHS: choice = 2; break;
			default: break;
			}
			
			techChoice = 0;
			
			if (state != STATES.MAIN) state = STATES.MAIN;
			else Game.state = Game.STATE.GAME;
		}
		if (chosen) chosen = false;
	}
	
	public void render(Graphics g, Screen screen) {
		setFont(g, largeFont);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w/3, h);
		g.fillRect(w - (w/3), 0, w/3, h);
		g.fillRect(w*1/3, h-100, w/3 + 5, 100);
		
		setFont(g, midFont);
		//STATS
		int n = midFont.getSize();
		
		g.drawString("Lv. " + player.getLevel(), w-(w/3) + 5, h/15);
		g.drawString("HP: " + player.getHP() + " / " + player.getMaxHP(), w-(w/3) + 5, h/15 + n);
		g.drawString("TP: " + player.getTP() + " / " + player.getMaxTP(), w-(w/3) + 5, h/15 + n*2);	
		g.drawString("EXP: " + player.getExp() + " / " + player.getNextLv(), w-(w/3) + 5, h/15 + n*3);
		
		g.drawString("Pwr: " + player.getBasePwr() + " x" + player.getWeapon().getModifier(), w-(w/3) + 5, h/15 + n*5);
		g.drawString("Tech: " + player.getBaseTech(), w-(w/3) + 5, h/15 + n*6);
		g.drawString("Dex: " + player.getBaseDex(), w-(w/3) + 5, h/15 + n*7);
		g.drawString("Evd: " + player.getBaseEvd(), w-(w/3) + 5, h/15 + n*8);
		g.drawString("Res: " + player.getBaseRes(), w-(w/3) + 5, h/15 + n*9);
		g.drawString("Def: " + player.getBaseDef() + " x" + player.getSuit().getModifier(), w-(w/3) + 5, h/15 + n*10);
		g.drawString("T-Def: " + player.getBaseTechDef() + " x" + player.getSuit().getModifier2(), w-(w/3) + 5, h/15 + n*11);
		
		g.drawString("Weapon: " + player.getWeapon().getName(), w-(w/3) +5, h/15 + n*13);
		g.drawString("Damage Type: " + player.getWeapon().getEffect(), w-(w/3)+5, h/15 + n*14);
		
		g.drawString(player.getSuit().getName(), w-(w/3), h/15 + n*16);
		g.drawString("Bonus: " + player.getSuit().getEffect(), w-(w/3), h/15 + n*17);
		
		g.drawString("Drone 1: " + player.getDrone1().getName(), w-(w/3)+5, h/15 + n*19);
		g.drawString("Drone 2: " + player.getDrone2().getName(), w-(w/3)+5, h/15 + n*21);
		
		Drone d1 = player.getDrone1();
		Drone d2 = player.getDrone2();
		
		if (!player.getDrone1().dead()) {
			g.drawRect(w-(w/3)+5, h/15 + n*19 + 10, 10 * d1.getMaxBattery(), 10); //first
			g.fillRect(w-(w/3)+5, h/15 + n*19 + 10, 10 * (d1.getMaxBattery()-(d1.getMaxBattery() - d1.getBattery())), 10);
		}
		else {
			g.setColor(Color.GRAY);
			g.drawRect(w-(w/3)+5, h/15 + n*19 + 10, 10 * d1.getMaxRecharge(), 10); //first
			g.fillRect(w-(w/3)+5, h/15 + n*19 + 10, 10 * (d1.getMaxRecharge()-(d1.getMaxRecharge() - d1.getRecharge())), 10);
			g.setColor(Color.WHITE);
		}
		
		if (!d2.dead()) {
			g.drawRect(w-(w/3)+5, h/15 + n*21 + 10, 10 *  d2.getMaxBattery(), 10); //first
			g.fillRect(w-(w/3)+5, h/15 + n*21 + 10, 10 * (d2.getMaxBattery()-(d2.getMaxBattery() - d2.getBattery())), 10);
		}
		else {
			g.setColor(Color.GRAY);
			g.drawRect(w-(w/3)+5, h/15 + n*21 + 10, 10 * d2.getMaxRecharge(), 10); //first
			g.fillRect(w-(w/3)+5, h/15 + n*21 + 10, 10 * (d2.getMaxRecharge()-(d2.getMaxRecharge() - d2.getRecharge())), 10);
			g.setColor(Color.WHITE);
		}
		
		g.drawString("Money: $" + Player.money, w-(w/3) + 5, n*26);
		g.drawString("Drone Parts: " + Player.droneParts, w-(w/3) + 5, n*27);
		
		switch(state) {
		case MAIN:
			listWithChoice(g, mainOptions, 0, h/10, largeFont);
			break;
		case DRONES:
			g.setColor(Color.BLACK);
			g.fillRect(w*2/3, 0, w/3, h);
			
			g.setColor(Color.WHITE);
			listDrones(g);
			g.drawString("Drone Parts: " + Player.droneParts, w-(w/7), 25);
			
			Drone d = drones.get(choice);
			int f = midFont.getSize();
				
			g.drawString(d.getName(), w*2/3 + 5, f);
			g.drawString("Battery: " + d.getBattery() + " / " + d.getMaxBattery(), w*2/3 + 5, f*2);
			
			if (d.getRecharge() == d.getMaxRecharge()) g.drawString("Fully Charged", w*2/3 + 5, f*3);
			else g.drawString("Charge: " + d.getRecharge() + " / " + d.getMaxRecharge(), w*2/3 + 5, f*3);
			
			if (!d.dead()) {
				g.drawRect(w*2/3 + 5, f*3+10, 10 * d.getMaxBattery(), 10); //first
				g.fillRect(w*2/3 + 5, f*3+10, 10 * (d.getMaxBattery()-(d.getMaxBattery() - d.getBattery())), 10);
			}
			else {
				g.setColor(Color.GRAY);
				g.drawRect(w*2/3 + 5, f*3+10, 10 * d.getMaxRecharge(), 10); //first
				g.fillRect(w*2/3 + 5, f*3+10, 10 * (d.getMaxRecharge()-(d.getMaxRecharge() - d.getRecharge())), 10);
				g.setColor(Color.WHITE);
			}
			
			if (d.dead()) {
				g.setColor(Color.RED);
				g.drawString("Recharging", w*2/3 + 5, f*5);
			}
			else {
				g.setColor(Color.GREEN);
				g.drawString("Active", w*2/3 + 5, f*5);
			}
			
			g.setColor(Color.WHITE);
			g.drawString("Commands:", w*2/3 + 5, f*7);
			g.drawString("___________", w*2/3 + 5, (f*8)-15);
			for (int i = 0; i < d.getCommands().length; i++) {
				g.drawString(d.getCommands()[i], w*2/3 + 5, f*(9+i));
			}
			
			g.drawString(d.getDescription(), 0, h-50);
			
			d.render(screen);
			
			break;
		case EQUIPMENT:
			g.setColor(Color.WHITE);
			g.drawString("WEAPONS", 5, 25);
			for (int i = 0; i < player.getWeapons().size(); i++) {
				Weapon weapon = player.getWeapons().get(i);
				
				g.setColor(Color.WHITE);
				if (i == choice && techChoice == 0) {
					g.drawString(weapon.getDescription(), 5, h-50);
					g.setColor(Color.RED);
				}
				g.drawString(weapon.getName(), 5, 60 + (i * midFont.getSize()));
			}
			
			g.setColor(Color.WHITE);
			g.drawString("SUITS", w/6, 25);
			for (int i = 0; i < player.getSuits().size(); i++) {
				Suit suit = player.getSuits().get(i);
				
				if (i == choice && techChoice == 1) {
					g.drawString(suit.getDescription(), 5, h-50);
					g.setColor(Color.RED);
				}
				g.drawString(suit.getName(), w/6, 60 + (i * midFont.getSize()));
				g.setColor(Color.WHITE);
			}
			
			break;
		case INVENTORY: 
			g.setColor(Color.BLACK);
			
			for (int i = 0; i < player.getInventory().size(); i++) {
				Item it = player.getInventory().get(i);
				
				g.setColor(Color.WHITE);
				if (i == choice) {
					g.drawString(it.getDescription(), 5, h-50);
					g.setColor(Color.RED);
				}
				
				g.drawString(it.getName() + " x" + it.getStock(), w/6, 25 + (i * midFont.getSize()));
			}
			break;
			
		case TECHS:
			g.setColor(Color.BLACK);
			g.fillRect(w*2/3, 0, w/2, h);
			
			g.setColor(Color.WHITE);
			g.drawString("Offensive", 5, 25);
			listTechs(g, player.getOffs(), 0, 10, 50, midFont);
			
			g.drawString("Curative", w/5, 25);
			listTechs(g, player.getCures(), 1, w/5 + 5, 50, midFont);
			
			g.drawString("Defensive", w*2/3, 25);
			listTechs(g, player.getDefs(), 2, w*2/3 + 5, 50, midFont);
			
			g.setColor(Color.WHITE);
			g.drawString("Skills", w*5/6, 25);
			
			for (int i = 0; i < player.getSkills().size(); i++) {
				Skill s = player.getSkills().get(i);
				
				if (techChoice == 3 && i == choice) {
					g.drawString(s.getDescription(), 5, h-50);
					g.setColor(Color.RED);
				}
				
				g.drawString(s.getName(), w*5/6 + 5, 25 + (midFont.getSize() * (i+1)));
				g.setColor(Color.WHITE);
			}
			break;
		default:
			break;
		
		}
	}
	
	//Fill drone list with player drones every time menu is called up
	
	public void fillDrones() {
		dronesList = new String[drones.size()];
		for (int i = 0; i < drones.size(); i++) {
			Drone d = drones.get(i);
			
			dronesList[i] = d.getName();
			d.setCoordinates(Player.x - (1*24), Player.y - (1*24));
		}
	}
	
	private void listDrones(Graphics g) {
		g.setFont(midFont);
		
		for (int i = 0; i < drones.size(); i++) {
			Drone d = drones.get(i);
			if (d.dead()) g.setColor(Color.GRAY);
			if (choice == i) g.setColor(Color.RED);
			g.drawString(d.getName(), 5, 20 + ((font.getSize()+10) * i));
			g.setColor(Color.WHITE);
			if (i == player.getDrone1().getIndex() || i == player.getDrone2().getIndex()) {
				g.setFont(largeFont);
				g.drawString("*", 0, 20 + ((font.getSize()+10) * i));
				g.setFont(midFont);
			}
			
			if (choice < 0) choice = drones.size()-1;
			else if (choice > drones.size()-1) choice = 0;
		}
	}

	//Tech lists
	private void listTechs(Graphics g, ArrayList<Tech> list, int index, int width, int height, Font font) {
		setFont(g, font);
		
		for (int i = 0; i < list.size(); i++) {
			Tech t = list.get(i);
			
			if (techChoice == index && choice == i) {
				g.drawString(t.getDescription(), 5, h - 50);
				g.setColor(Color.RED);
			}
			g.drawString(t.getName() + " -" + t.getCost(), width+5, height + (font.getSize()*i));
			g.setColor(Color.WHITE);
		}
	}

}
