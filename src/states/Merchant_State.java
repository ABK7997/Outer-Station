package states;

import input.Player;
import items.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Game;
import party.Brawler;
import party.drones.*;
import party.equip.*;
import party.weapons.*;
import storage.Storage;
import battle.Skill;
import battle.Tech;
import battle.cure.*;
import battle.def.*;
import battle.off.*;
import entity.mobs.merchants.Merchant;

public class Merchant_State extends State {
	
	private static Brawler player = new Brawler();
	
	private static String name;
	private static String service;
	private static String[] dialogue;
	private static String[] inquiry;
	
	private int n = 0;
	public static int techChoice = 0;
	
	private enum STATE {
		MAIN, TALK, INQUIRE, SERVICE, SELL
	};
	
	private STATE state = STATE.MAIN;
	
	private static String[] options = new String[] {
		"Leave",
		"Talk",
		"Inquire",
		"Service",
		"Sell"
	};
	
	private static String[] restOptions = new String[] {
		"Rest / Recharge - $100",
		"Rest Only - $70",
		"Recharge Drones - $50"
	};
	
	//Merchandise
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	public static ArrayList<Suit> suits = new ArrayList<Suit>();
	public static ArrayList<Tech> offTechs = new ArrayList<Tech>();
	public static ArrayList<Tech> cureTechs = new ArrayList<Tech>();
	public static ArrayList<Tech> defTechs = new ArrayList<Tech>();
	public static ArrayList<Skill> skills = new ArrayList<Skill>();
	public static ArrayList<Drone> drones = new ArrayList<Drone>();
	
	public Merchant_State(Brawler b) {
		player = b;
		
		drones.add(new Assault_Drone(b));
		drones.add(new Shock_Drone(b));
		drones.add(new Medic_Drone(b));
		drones.add(new Shell_Drone(b));
		
		weapons.add(new DSIS_Pistol());
		weapons.add(new Laser_Pistol());
		weapons.add(new Dart_Gun());
		weapons.add(new Hack_Device());
		weapons.add(new Radio_Blade());
		
		suits.add(new Rugged_Suit(b));
		suits.add(new Military_Suit(b));
		suits.add(new Heat_Suit(b));
		suits.add(new Hazmat_Suit(b));
		suits.add(new Plague_Suit(b));
		suits.add(new Shock_Suit(b));
		suits.add(new Double_Suit(b));
		
		offTechs.add(new Shock(b));
		cureTechs.add(new Heal(b));
		defTechs.add(new Shield(b));
		
		for (int i = 0; i < Storage.allSkills.size(); i++) {
			skills.add(Storage.allSkills.get(i));
		}
		
		items.add(new Medkit(player));
		items.add(new Super_Kit(player));
		items.add(new X_Medkit(player));
		
		items.add(new Battery(player));
		items.add(new Super_Battery(player));
		items.add(new X_Battery(player));
		
		items.add(new Antidote(player));
		items.add(new Ointment(player));
		items.add(new Antirad(player));
		items.add(new Toolkit(player));
		items.add(new AntiHack(player));
	}
	
	public void update() {
		switch(state) {
		case MAIN:
			if (chosen) {
				switch(choice) {
				case 0: backToGame(); break;
				case 1: state = STATE.TALK; break;
				case 2: state = STATE.INQUIRE; break;
				case 3: state = STATE.SERVICE; techChoice = 0; choice = 0; break;
				case 4: state = STATE.SELL; break;
				default: break;
				}
			}
			break;
		case INQUIRE:
		case TALK:
			if (chosen) {
				n++;
				if (n == inquiry.length) closeDialogue();
			}
			break;
		case SERVICE:
			switch(service) {
			case "Rest":
				if (choice < 0) choice = 2;
				else if (choice > 2) choice = 0;
				
				if (chosen) {
					switch(choice) {
					case 0: 
						player.levelRestore();
						player.getDrone1().refillBattery();
						player.getDrone2().refillBattery();
						Player.money -= 100;
						break;
					case 1:
						player.levelRestore();
						Player.money -= 70;
						break;
					case 2:
						player.getDrone1().refillBattery();
						player.getDrone2().refillBattery();
					}
				}
			case "Skills":
				if (choice < 0) choice = skills.size() - 1;
				else if (choice > skills.size() - 1) choice = 0;
				
				if (chosen) {
					if (!player.hasSkill(skills.get(choice))) player.learnSkill(skills.get(choice));
				}
				break;
				
			case "Items":
				if (choice < 0) choice = items.size() - 1;
				else if (choice > items.size() - 1) choice = 0;
				
				if (chosen) {
					if (Player.money >= items.get(choice).getCost()) {
						player.addItem(items.get(choice));
						Player.money -= items.get(choice).getCost();
					}
				}
				break;
			case "Weapons": 
				if (choice < 0) choice = weapons.size() - 1;
				else if (choice > weapons.size() - 1) choice = 0;
				
				if (chosen) {
					if (Player.money >= weapons.get(choice).getCost() && !player.hasWeapon(weapons.get(choice))) {
						player.addWeapon(weapons.get(choice));
						Player.money -= weapons.get(choice).getCost();
					}
				}
				break;
			case "Suits":
				if (choice < 0) choice = suits.size() - 1;
				else if (choice > suits.size() - 1) choice = 0;
				
				if (chosen) {
					if (Player.money >= suits.get(choice).getCost()) {
						player.buySuit(suits.get(choice));
					}
				}
				break;
			case "Drones":
				if (choice < 0) choice = drones.size() - 1;
				else if (choice > drones.size() - 1) choice = 0;
				
				if (chosen) {
					if (Player.money >= drones.get(choice).getCost()) {
						if (!player.hasDrone(drones.get(choice))) {
							player.buyDrone(drones.get(choice));
							Player.money -= drones.get(choice).getCost();
						}
					}
				}
				break;
			case "Techs":
				if (techChoice < 0) techChoice = 2;
				else if (techChoice > 2) techChoice = 0;
				
				switch(techChoice) {
				case 0:
					if (choice < 0) choice = offTechs.size() - 1;
					else if (choice > offTechs.size() - 1) choice = 0;
					break;
				case 1:
					if (choice < 0) choice = cureTechs.size() - 1;
					else if (choice > cureTechs.size() - 1) choice = 0;
					break;
				case 2:
					if (choice < 0) choice = defTechs.size() - 1;
					else if (choice > defTechs.size() - 1) choice = 0;
					break;
				}
				
				if(chosen) {
					switch(techChoice) {
					case 0: if (Player.money >= offTechs.get(choice).getPrice()) {
						if (!player.hasTech(offTechs.get(choice), techChoice)) {
							player.learnTech(offTechs.get(choice));
							Player.money -= offTechs.get(choice).getPrice();
							}
						}
						break;
					case 1: if (Player.money >= cureTechs.get(choice).getPrice()) {
						if (!player.hasTech(cureTechs.get(choice), techChoice)) {
							player.learnTech(cureTechs.get(choice));
							Player.money -= cureTechs.get(choice).getPrice();
							}
						}
						break;
					case 2: if (Player.money >= defTechs.get(choice).getPrice()) {
						if (!player.hasTech(defTechs.get(choice), techChoice)) {
							player.learnTech(defTechs.get(choice));
							Player.money -= defTechs.get(choice).getPrice();
							}
						}
						break;
					}
				}
				break;
			}
			break;
			
		case SELL:
			switch(service) {
			case "Items":
				if (choice < 0) choice = player.getInventory().size() - 1;
				else if (choice > player.getInventory().size() - 1) choice = 0;
				
				if (chosen && player.getInventory().size() > 0) {
					Player.money += player.getInventory().get(choice).getCost()/3;
					player.getInventory().get(choice).stock(-1);
				}
				break;
			case "Weapons": 
				if (choice < 0) choice = player.getWeapons().size() - 1;
				else if (choice > player.getWeapons().size() - 1) choice = 0;
				
				if (chosen && player.getWeapons().size() > 0) {
					Player.money += player.getWeapons().get(choice).getCost()/3;
					player.sellWeapon(player.getWeapons().get(choice));
				}
				break;
			case "Suits":
				if (choice < 0) choice = player.getSuits().size() - 1;
				else if (choice > player.getSuits().size() - 1) choice = 0;
				
				if (chosen && player.getSuits().size() > 0) {
					Player.money += player.getSuits().get(choice).getCost()/3;
					player.sellSuit(player.getSuits().get(choice));
				}
				break;
			case "Techs":
				if (techChoice < 0) techChoice = 2;
				else if (techChoice > 2) techChoice = 0;
				
				switch(techChoice) {
				case 0:
					if (choice < 0) choice = player.getOffs().size() - 1;
					else if (choice > player.getOffs().size() - 1) choice = 0;
					break;
				case 1:
					if (choice < 0) choice = player.getCures().size() - 1;
					else if (choice > player.getCures().size() - 1) choice = 0;
					break;
				case 2:
					if (choice < 0) choice = player.getDefs().size() - 1;
					else if (choice > player.getDefs().size() - 1) choice = 0;
					break;
				}
				
				if(chosen) {
					switch(techChoice) {
					case 0: 
						if (player.getOffs().size() > 0) {
							Player.money += player.getOffs().get(choice).getPrice()/3;
							player.getOffs().remove(choice);
						}
					break;
					case 1: 
						if (player.getCures().size() > 0) {
							Player.money += player.getCures().get(choice).getPrice()/3;
							player.getCures().remove(choice);
						}
						break;
					case 2: 
						if (player.getDefs().size() > 0) {
							Player.money += player.getDefs().get(choice).getPrice()/3;
							player.getDefs().remove(choice);
						}
						break;
					}
				}
				break;
			case "Drones":
				if (choice < 0) choice = player.getDrones().size() - 1;
				else if (choice > player.getDrones().size() - 1) choice = 0;
				
				Drone d = player.getDrones().get(choice);
				
				if (Player.droneParts > 0 && chosen) {
					if (d.getLevel() == 1) {
						d.levelUp();
					}
					else if (d.getLevel() == 2) {
						d.levelUp2();
					}
					Player.droneParts--;
					d.refillBattery();
				}
				
				break;
			}
			break;
			
		default: break;
		}
		
		if (reset) {
			n = 0;
			techChoice = 0;
			reset = false;
			
			switch(state) {
			case TALK: choice = 1; break;
			case INQUIRE: choice = 2; break;
			case SERVICE: choice = 3; break;
			default: break;
			}
			
			if (state != STATE.MAIN) {
				state = STATE.MAIN;
				reset();
			}
			else {
				reset();
				Game.state = Game.STATE.GAME;
			}
		}
		if (chosen) chosen = false;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(w-(w/3), 0, w/3, h);
		
		setFont(g, midFont);
		
		switch(state) {
		case MAIN:
			g.setFont(largeFont);
			g.drawString(name, w-(w/3), 25);
			
			listWithChoice(g, options, w-(w/3), 50, midFont);
			break;
			
		case TALK:
			g.setColor(Color.BLACK);
			g.fillRect(0, h-150, w*3/4, 150);
			
			setFont(g, font);
			g.drawString(dialogue[n], 15, h-125);
			break;
			
		case INQUIRE:
			g.setColor(Color.BLACK);
			g.fillRect(0, h-150, w*3/4, 150);
			
			setFont(g, font);
			g.drawString(inquiry[n], 15, h-125);
			break;
			
		case SERVICE:
			switch(service) {
			case "Rest":
				listWithChoice(g, restOptions, w-(w/3) + 5, 30, midFont);
				break;
			case "Skills":
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, w/3, h);
				g.fillRect(0, h - 100, w*3/4, 100);
				
				setFont(g, largeFont);
				g.drawString("Choose Skills", w*2/3 + 5, 30);
				
				g.setFont(midFont);
				for (int i = 0; i < skills.size(); i++) {
					Skill s = skills.get(i);
					if (choice == i) {
						g.drawString(s.getDescription(), 10, h-75);
						g.setColor(Color.RED);
					}
					g.drawString(s.getName(), w*2/3 + 10, 60 + (i*midFont.getSize()*2));
					g.setColor(Color.WHITE);
					
					if (player.hasSkill(skills.get(i))) {
						g.setFont(superFont);
						g.drawString("*", w*2/3, 65 + (i*midFont.getSize()*2));
					}
					g.setFont(midFont);
				}
				
				setFont(g, midFont);
				for (int i = 0; i < player.getSkills().size(); i++) {
					Skill ss = player.getSkills().get(i);
					
					g.drawString(ss.getName(), 5, 30 + (i*midFont.getSize()));
				}
				break;
				
			case "Items":
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, w/3, h);
				g.fillRect(0, h - 100, w*3/4, 100);
				
				setFont(g, midFont);
				g.drawString("Wallet: $" + Player.money, w/4 - 100, 30);
				g.drawString("Your Inventory", 5, 30);
				
				//Merchant
				for (int i = 0; i < items.size(); i++) {
					Item it = items.get(i);
					
					if (choice == i) {
						g.drawString(it.getDescription(), 10, h-75);
						g.setColor(Color.RED);
					}
					g.drawString(it.getName() + " -$" + it.getCost(), w*2/3 + 10, 30 + i*midFont.getSize());
					g.setColor(Color.WHITE);
				}
				
				//Player
				for (int i = 0; i < player.getInventory().size(); i++) {
					Item in = player.getInventory().get(i);
					
					g.drawString(in.getName() + " x" + in.getStock(), 10, 75 + i*midFont.getSize());
				}
				break;
				
			case "Weapons":
				g.setColor(Color.BLACK);
				g.fillRect(0, h-100, w*3/4, 100);
				g.fillRect(0, 0, w/6, 100);
				
				g.setColor(Color.WHITE);
				for (int i = 0; i < weapons.size(); i++) {
					Weapon weapon = weapons.get(i);
					
					if (choice == i) {
						g.drawString(weapon.getDescription(), 10, h-75);
						g.setColor(Color.RED);
					}
					g.drawString(weapon.getName() + " -$" + weapon.getCost(), w*2/3 + 10, 30 + i*midFont.getSize());
					g.setColor(Color.WHITE);
					
					if (player.hasWeapon(weapon)) {
						g.drawString("*", w*2/3, 35 + (i*midFont.getSize()));
					}
				}
				
				g.drawString("Money: $" + Player.money, 10, 30);
				break;
				
			case "Suits":
				g.setColor(Color.BLACK);
				g.fillRect(0, h-100, w*3/4, 100);
				g.fillRect(0, 0, w/6, 100);
				
				g.setColor(Color.WHITE);
				for (int i = 0; i < suits.size(); i++) {
					Suit s = suits.get(i);
					
					if (choice == i) {
						g.drawString(s.getDescription(), 10, h-75);
						g.drawString("DEF Modifier: x" + s.getModifier(), w-(w/3) + 5, h*3/4);
						g.drawString("TECH-DEF Modifier: x" + s.getModifier2(), w-(w/3) + 5, h*3/4 + 25);
						g.setColor(Color.RED);
					}
					g.drawString(s.getName() + " -$" + s.getCost(), w*2/3 + 10, 30 + i*midFont.getSize());
					g.setColor(Color.WHITE);
					
					if (player.hasSuit(s)) {
						g.drawString("*", w*2/3, 35 + (i*midFont.getSize()));
					}
				}
				
				g.drawString("Money: $" + Player.money, 10, 30);
				break;
				
			case "Drones":
				g.setColor(Color.BLACK);
				g.fillRect(0, h-100, w*3/4, 100);
				g.fillRect(0, 0, w/6, 100);
				
				g.setColor(Color.WHITE);
				for (int i = 0; i < drones.size(); i++) {
					Drone d = drones.get(i);
					
					if (choice == i) {
						g.drawString(d.getDescription(), 10, h-75);
						g.setColor(Color.RED);
					}
					g.drawString(d.getName() + " -$" + d.getCost(), w*2/3 + 10, 30 + i*midFont.getSize());
					g.setColor(Color.WHITE);
					
					if (player.hasDrone(d)) {
						g.drawString("*", w*2/3, 35 + (i*midFont.getSize()));
					}
				}
				
				g.drawString("Money: $" + Player.money, 10, 30);
				break;
				
			case "Techs":
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, w/3, h);
				g.fillRect(w*3/5, 0, w*2/5, h);
				g.fillRect(0, h - 100, w*3/4, 100);
				
				setFont(g, midFont);
				g.drawString("Offensive", w/6, 25);
				g.drawString("Curative", w*3/5, 25);
				g.drawString("Defensive", w-(w/5), 25);
				
				listTechs(g, offTechs, 0, w/6, 60, midFont);
				listTechs(g, cureTechs, 1, w*3/5 + 5, 60, midFont);
				listTechs(g, defTechs, 2, w-(w/5) + 5, 60, midFont);
				
				g.drawString("Money: $" + Player.money, 5, 30);
				g.drawString("Max TP: " + player.getMaxTP(), 5, 60);
				break;
			}
			break;
			
		case SELL:
			g.setColor(Color.BLACK);
			g.fillRect(w-(w/3), 0, w/3, h);
			g.fillRect(0, 0, w/9, h/10);
			
			switch(service) {
			case "Items":
				setFont(g, midFont);
				g.drawString("Money: " + Player.money, 5, 25);
				
				for (int i = 0; i < player.getInventory().size(); i++) {
					Item it = player.getInventory().get(i);
					
					if (i == choice) g.setColor(Color.RED);
					
					g.drawString(it.getName() + " +$" + it.getCost()/3 + " x" + it.getStock(), w-(w/3), 25 + (midFont.getSize()*i));
					g.setColor(Color.WHITE);
				}
				break;
			case "Weapons":
				setFont(g, midFont);
				g.drawString("Money: " + Player.money, 5, 25);
				
				for (int i = 0; i < player.getWeapons().size(); i++) {
					Weapon weapon = player.getWeapons().get(i);
					
					if (i == choice) g.setColor(Color.RED);
					
					g.drawString(weapon.getName() + " +$" + weapon.getCost()/3, w-(w/3), 25 + (midFont.getSize()*i));
					g.setColor(Color.WHITE);
				}
				break;
			case "Suits":
				setFont(g, midFont);
				g.drawString("Money: " + Player.money, 5, 25);
				
				for (int i = 0; i < player.getSuits().size(); i++) {
					Suit s = player.getSuits().get(i);
					
					if (i == choice) g.setColor(Color.RED);
					
					g.drawString(s.getName() + " +$" + s.getCost()/3, w-(w/3), 25 + (midFont.getSize()*i));
					g.setColor(Color.WHITE);
				}
				break;
			case "Techs":
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, w/3, h);
				g.fillRect(w*3/5, 0, w*2/5, h);
				g.fillRect(0, h - 100, w*3/4, 100);
				
				setFont(g, midFont);
				g.drawString("Offensive", w/6, 25);
				g.drawString("Curative", w*3/5, 25);
				g.drawString("Defensive", w-(w/5), 25);
				
				listTechs2(g, player.getOffs(), 0, w/6, 60, midFont);
				listTechs2(g, player.getCures(), 1, w*3/5 + 5, 60, midFont);
				listTechs2(g, player.getDefs(), 2, w-(w/5) + 5, 60, midFont);
				
				g.drawString("Money: $" + Player.money, 5, 30);
				break;
				
			//DRONE UPGRADES
			case "Drones": 
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, w/3, h);
				g.fillRect(w*3/5, 0, w*2/5, h);
				g.fillRect(0, h - 100, w*3/4, 100);
				
				setFont(g, midFont);
				
				for (int i = 0; i < player.getDrones().size(); i++) {
					Drone d = player.getDrones().get(i);
					
					if (i == choice) {
						//Drone states
						g.drawString("Battery: " + d.getMaxBattery(), 5, 100);
						g.drawString("Recharge Time: " + d.getMaxRecharge(), 5, 130);
						g.drawString("Power: " + d.getEffect() + d.getPower(), 5, 160);
						
						g.setColor(Color.RED);
					}
					
					g.drawString(d.getName() + " Lv. " + d.getLevel(), w*3/5 + 5, 30 + (i * midFont.getSize()));
					
					g.setColor(Color.WHITE);
					
					g.drawString("Drone Parts: " + Player.droneParts, 5, 30);
				}
				break;
				
			default: state = STATE.MAIN; break;
			}
			break;
			
		default:
			break;
		}
		
	}
	
	public static void setMerchant(Merchant m, Brawler p) {
		player = p;
		
		name = m.getName();
		service = m.getService();
		dialogue = m.getDialogue();
		inquiry = m.getInquiry();
		
		if (service == "Drones") options[4] = "Upgrade";
		else if (service == "Skills") options[4] = "----";
		else options[4] = "Sell";
	}
	
	private void closeDialogue() {
		state = STATE.MAIN;
		n = 0;
	}

	private void listTechs(Graphics g, ArrayList<Tech> list, int index, int width, int height, Font font) {
		setFont(g, font);
		
		for (int i = 0; i < list.size(); i++) {
			Tech t = list.get(i);
			
			if (techChoice == index && choice == i) {
				g.drawString(t.getDescription() + "; TP Cost: -" + t.getCost(), 5, h - 50);
				g.setColor(Color.RED);
			}
			g.drawString(t.getName() + " -$" + t.getPrice(), width+5, height + (font.getSize()*i));
			g.setColor(Color.WHITE);
			
			if (player.hasTech(t, index)) {
				g.setFont(superFont);
				g.drawString("*", width-5, height + (font.getSize()*i) + 10);
			}
			
			g.setFont(midFont);
		}
	}
	
	private void listTechs2(Graphics g, ArrayList<Tech> list, int index, int width, int height, Font font) {
		setFont(g, font);
		
		for (int i = 0; i < list.size(); i++) {
			Tech t = list.get(i);
			
			if (techChoice == index && choice == i) g.setColor(Color.RED);
			
			g.drawString(t.getName() + " +$" + t.getPrice()/3, width+5, height + (font.getSize()*i));
			g.setColor(Color.WHITE);
			
			g.setFont(midFont);
		}
	}
	
	//Add new Tech
	public static void addTech(Tech tech) {
		switch(tech.getType()) {
		case "Offensive": offTechs.add(tech); break;
		case "Curative": cureTechs.add(tech); break;
		case "Defensive": defTechs.add(tech); break;
		default: break;
		}
	}
	
}
