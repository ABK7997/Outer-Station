package states;

import graphics.Screen;
import input.Player;
import items.Item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import level.Level;
import main.Game;
import party.Brawler;
import party.Brawler.STATE;
import party.drones.Drone;
import party.drones.Null_Drone;
import battle.Skill;
import battle.Tech;
import entity.mobs.enemies.Enemy;
import entity.mobs.enemies.Enemy.STATES;
import entity.mobs.enemies.EnemyParty;

public class Battle extends State {
	
	private Random random = new Random();

	public static enum PHASE {
		SELECTION, DRONES, COMMAND1, COMMAND2, ANIMATING, TARGET, TECH, SKILL, ITEM, END
	}
	
	public static PHASE phase = PHASE.DRONES;
	public static int turnNumber = 0;
	
	//Parties
	private Brawler player;
	private ArrayList<Drone> drones;
	private Drone drone1 = new Null_Drone();
	private Drone drone2 = new Null_Drone();
	private int droneSelection = 1;
	
	public static ArrayList<Enemy> eParty;
	
	//Animation Sequence
	public static boolean secondMove = false;
	private static boolean end = false;
	
	//Move Selection
	private String[] options = new String[] {
		"Attack",
		"Tech",
		"Skill",
		"Defend",
		"Drones",
		"Drone 1 Command",
		"Drone 2 Command",
		"Item",
		"Flee"
	};
	public static int techChoice = 0;
	public static int itemChoice = 0;
	
	//Item drop
	private String itemDropped = "";
	
	public Battle(Brawler brawler) {
		player = brawler;
		drones = brawler.getDrones();
		drone1 = brawler.getDrone1();
		drone2 = brawler.getDrone2();
		
		eParty = EnemyParty.eParty;
		
		beginBattle();
	}
	
	public void beginBattle() {
		end = false;
		phase = PHASE.SELECTION;
		
		player.setCoordinates(Player.x - (3*24), Player.y);
		drone1.setCoordinates(Player.x - (3*24), Player.y - (2*24));
		drone2.setCoordinates(Player.x - (3*24), Player.y + (2*24));
		
		if (eParty.size() == 1) {
			eParty.get(0).setCoordinates(Player.x + (3*24), Player.y);
		}
		
		else {
			for (int i = 0; i < eParty.size(); i++) {
				Enemy e = eParty.get(i);
			
				e.setCoordinates(Player.x + (3*24), Player.y + ((i*2) * 24) - (2 * 24));
				e.setParty(eParty);
			}
		}
		
		player.setPwr(player.getBasePwr());
		player.setDex(player.getBaseDex());
		player.setEvd(player.getBaseEvd());
		player.setRes(player.getBaseRes());
		player.setTech(player.getBaseTech());
		
		player.setDef(player.getBaseDef());
		player.setTechDef(player.getBaseTechDef());
		
		player.setPwrTimer(0);
		player.setDexTimer(0);
		player.setEvdTimer(0);
		player.setResTimer(0);
		player.setTechTimer(0);
		player.setDefTimer(0);
		player.setTechDefTimer(0);
		player.timer();
		
		player.setDP(player.getDP());
		player.setCP(-player.getCP());
		player.setRP(-player.getRP());
		player.setSP(-player.getSP());
		player.setMessage("");
		
		player.setStatusEffected();	
		
		reset();
		
		Level.music.playSound(eParty.get(0).getMusic() + ".wav");
	}
	
	public void update() {
		switch(phase) {
		case SELECTION:
			if (chosen) {
				switch(choice) {
				case 0: phase = PHASE.TARGET; player.setChoice(0); break;
				case 1: phase = PHASE.TECH; break;
				case 2: phase = PHASE.SKILL; break;
				case 3: startAnimation(); player.setChoice(1); break;
				case 4: if (player.getDrones().size() > 0) phase = PHASE.DRONES; break;
				case 5: phase = PHASE.COMMAND1; break;
				case 6: phase = PHASE.COMMAND2; break;
				case 7: if (player.getInventory().size() > 0) phase = PHASE.ITEM; break;
				case 8: 
					Player.fleeing = true;
					Player.fleeTimer = 0;
					player.setChoice(2);
					startAnimation();
					break;
				}
				reset();
			}
			break;
		case ANIMATING: attackSequence(); 
			if (player.getTechChosen().isAnimating()) player.getTechChosen().update();
			else if (player.getSkillChosen().isAnimating()) player.getSkillChosen().update();
			
			for (int i = 0; i < eParty.size(); i++) {
				Enemy e = eParty.get(i);
				
				if (e.getTechChosen().isAnimating()) e.getTechChosen().update2();
				else if (e.getSkillChosen().isAnimating()) e.getSkillChosen().update2();
			}
			
			break;
		case COMMAND1: 
			if (chosen) {
				drone1.setCommand(choice+1);
				chosen = false;
			}
			break;
		case COMMAND2: 
			if (chosen) {
				drone2.setCommand(choice+1);
				chosen = false;
			}
				break;
		case DRONES:
			if (chosen) {
				if (droneSelection == 1 && !drones.get(choice).dead()) {
					drones.get(choice).setCoordinates(drone1.getX(), drone1.getY());
					drone1 = drones.get(choice);
					droneSelection *= -1;
				}
				else if (choice != drone1.getIndex() && !drones.get(choice).dead()){
					drones.get(choice).setCoordinates(drone2.getX(), drone2.getY());
					drone2 = drones.get(choice);
					droneSelection *= -1;
				}
				if (drones.get(choice).dead()) {
					player.setMessage("Drone Recharging");
				}
				chosen = false;
			}
			break;
		case END:
			if (chosen || reset) {
				reset();
				endBattle();
			}
			break;
		case ITEM:
			if (choice < 0) choice = player.getInventory().size() - 1;
			else if (choice > player.getInventory().size() - 1) choice = 0;
			
			if (chosen) {
				player.setItem(player.getInventory().get(choice));
				startAnimation();
			}
			
			player.setChoice(5);
			break;
		case SKILL:
			if (choice < 0) choice = player.getSkills().size() - 1;
			else if (choice > player.getSkills().size() - 1) choice = 0;
			
			if (chosen) {
				player.setSkillChosen(player.getSkills().get(choice));
				player.setChoice(4);
				String type = player.getSkillChosen().getType();
				
				if (type == "Offensive") {
					phase = PHASE.TARGET;
				}
				else {
					startAnimation();
				}
				reset();
			}
			
			break;
		case TARGET:
			if (choice < 0) choice = eParty.size()-1;
			else if (choice >= eParty.size()) choice = 0;
			
			if (eParty.get(choice).getHP() == 0) choice++;
			
			if (chosen) {
				player.setTarget(eParty.get(choice));
				reset();
				startAnimation();
			}
			break;
		case TECH:
			if (techChoice > 2) techChoice = 0;
			else if (techChoice < 0) techChoice = 2;
			
			switch(techChoice) {
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
			}
			
			if (chosen) {
				chosen = false;
				player.setTechType(techChoice);
				switch(techChoice) {
				case 0:
					player.setTechChosen(player.getOffs().get(choice));
					if (player.getTechChosen().getCost() > player.getTP()) {
						player.setMessage("Not enough TP");
						return;
					}
					phase = PHASE.TARGET; 
					break;
				case 1:
					player.setTechChosen(player.getCures().get(choice));
					if (player.getTechChosen().getCost() > player.getTP()) {
						player.setMessage("Not enough TP");
						return;
					}
					startAnimation();
					break;
				case 2:
					player.setTechChosen(player.getDefs().get(choice));
					if (player.getTechChosen().getCost() > player.getTP()) {
						player.setMessage("Not enough TP");
						return;
					}
					startAnimation();
					break;
				}
				reset();
				player.setChoice(3);
			}
			break;
		default:
			break;
		}
		
		if (reset) {
			reset();
			
			switch (phase) {
			case COMMAND1: choice = 5; phase = PHASE.SELECTION; break;
			case COMMAND2: choice = 6; phase = PHASE.SELECTION; break;
			case DRONES: choice = 4; phase = PHASE.SELECTION; break;
			case ITEM: choice = 7; break;
			case SKILL: choice = 2; break;
			case TECH: choice = 1; break;
			case TARGET: choice = 0; break;
			default: break;
			}
			
			if (phase != PHASE.ANIMATING && phase != PHASE.END) {
				phase = PHASE.SELECTION;
			}
		}
		
		//Entity updates
		player.update();
		drone1.update();
		drone2.update();
		
		for (int i = 0; i < eParty.size(); i++) {
			eParty.get(i).update();
		}
	}
	
	public void render(Graphics g, Screen screen) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w/6, h);
		g.fillRect(w - (w/6), 0, w/6, h);
		setFont(g, midFont);
		
		player.render(g, screen);
		drone1.render(screen);
		drone2.render(screen);
		
		//ENEMY STATS
		for (int i = 0; i < eParty.size(); i++) { 
			Enemy e = eParty.get(i);
			
			e.render(screen);
			
			if (e.getHP() == 0) g.setColor(Color.RED);
			g.drawString(e.getName(), w - (w/6) + 5, 20 + (i * h/3));
			g.drawString("HP: " + e.getHP() + " / " + e.getMaxHP(), w - (w/6) + 5, 50 + (i * h/3));
			
			g.setFont(font);
			//PWR / TECH / DEFMOD / T-DEFMOD
			g.drawString(" x" + e.getPwr()/e.getBasePwr() + " / x" + e.getTech()/e.getBaseTech() + " / " +
					e.getDefMod() + "% / " + e.getTechMod() + "%", w - (w/6) + 5, 70 + (i * h/3));
			
			if (phase == PHASE.TARGET && i == choice) {
				g.setFont(superFont);
				g.setColor(Color.RED);
				g.drawString("*", w - (w/6) - 10, 50 + (i * h/3)); //TARGET
			}
			setFont(g, midFont);
		}
		
		//DISPLAY BATTLE EFFECTS - Player
		g.setFont(midFont);
		
		g.setColor(Color.RED); //DP
		if (player.getDP() < 0) g.drawString("HP Lost: " + player.getDP(), 5, h-150);
		
		g.setColor(new Color(0xffFF00FF)); //SP
		if (player.getSP() < 0) g.drawString("TP Lost: " + player.getSP(), 5, h-120);
		
		g.setColor(Color.GREEN); //CP
		if (player.getCP() > 0) g.drawString("HP Gained: +" + player.getCP(), 5, h-90);
		
		g.setColor(new Color(0xff0088FF)); //RP
		if (player.getRP() > 0) g.drawString("TP Gained: +" + player.getRP(), 5, h-60);
		
		g.setColor(Color.WHITE); //BATTLE MESSAGE
		if (player.getMessage() != "") {
			g.setColor(new Color(0xffFFd700));
			g.drawString(player.getMessage(), 5, h - 180);
		}
		
		//STATUS EFFECTS - Player
		if (player.getStatusEffected()) {
			g.setColor(Color.BLACK);
			g.fillRect(w/6, 0, w/5, h/4);
		}
		
		if (player.getPoisoned()) { //Poison
			g.setColor(new Color(0xff88FF88));
			g.drawString("POISONED: -" + (player.getMaxHP()*5)/100 + " HP", w/6 + 5, 40);
		}
		else if (player.getBurned()) { //Burn
			g.setColor(Color.ORANGE);
			g.drawString("BURNT: -" + player.getMaxHP()/10 + " HP", w/6 + 5, 40);
		}
		if (player.getRadio()) { //Radiation Poisoning
			g.setColor(Color.MAGENTA);
			g.drawString("RADIATION: -" + (player.getMaxTP()*5)/100 + "TP", w/6 + 5,70);
		}
		
		if (player.getDefMod() < 100) {
			g.setColor(Color.GRAY);
			g.fillRect(w/3 + (24*2 + 12), h/2, 10, (24 * 2));
		}
		if (player.getTechMod() < 100) {
			g.setColor(new Color(0xff00CCCC));
			g.fillRect(w/3 + (24*3), h/2, 10, (24 *2));
		}
		
		//BATTLE EFFECTS - Enemies
		for (int i = 0; i < eParty.size(); i++) {
			Enemy e = eParty.get(i);
			
			if (e.getDP() < 0) {
				g.setColor(Color.RED); //DP
				g.drawString("HP Lost: " + eParty.get(i).getDP(), (w * 5/6) + 5, 20 + (i * (h/3) + 70));
			}
			if (e.getCP() > 0) {
				g.setColor(Color.GREEN); //CP
				g.drawString("HP Gained: +" + eParty.get(i).getCP(), (w * 5/6) + 5, 50 + (i * (h/3) + 70));
			}
			if (i < 3 && eParty.get(i).getMessage() != "") {
				g.setColor(new Color(0xffFFd700));
				g.drawString(eParty.get(i).getMessage(), (w * 5/6) + 5, 80 + (i * (h/3) + 60));
			}
			
			//STATUS EFFECTS - Enemies
			if (e.getPoisoned()) { //Poison
				g.setColor(new Color(0xff88FF88));
				g.drawString("POISON: -" + (e.getMaxHP()*5)/100 + " HP", w*5/6 + 5, 175 + (i*240));
			}
			else if (e.getHacked()) {
				g.setColor(Color.GRAY);
				g.drawString("HACKING: -" + (e.getMaxHP()*5)/100 + " HP", w*5/6 + 5, 175 + (i*240));
			}
			else if (e.getBurned()) { //Burn
				g.setColor(Color.ORANGE);
				g.drawString("BURN: -" + e.getMaxHP()/10 + " HP", w*5/6 + 5, 175 + (i*240));
			}
			if (e.getRadio()) { //Radiation Poisoning
				g.setColor(Color.MAGENTA);
				g.drawString("RADIATION: -" + (e.getMaxTP()*5) / 100 + " TP", w*5/6 + 5, 200 + (i*240));
			}
		}
		
		//PLAYER STATS
		setFont(g, midFont);
		int n = midFont.getSize();
				
		g.drawString("HP: " + player.getHP() + " / " + player.getMaxHP(), 5, n);
		g.drawString("TP: " + player.getTP() + " / " + player.getMaxTP(), 5, n*2);
		
		
		g.setFont(font);
		//PWR / TECH / DEFMOD / T-DEFMOD
		g.drawString(" x" + player.getPwr()/player.getBasePwr() + " / x" + player.getTech()/player.getBaseTech() + " / " +
				player.getDefMod() + "% / " + player.getTechMod() + "%", 5, n*3);
		
		g.setFont(midFont);
		
		//Drones
		g.drawString("Drones Active", 5, n*4);
		g.setFont(font);
		g.drawString("1. " + drone1.getName(), 5, n*5);
		g.drawString("2. " + drone2.getName(), 5, n*7);
		
		if (!drone1.dead()) {
			g.drawRect(5, n*6-15, 10 * drone1.getMaxBattery(), 10); //first
			g.fillRect(5, n*6-15, 10 * (drone1.getMaxBattery()-(drone1.getMaxBattery() - drone1.getBattery())), 10);
		}
		else {
			g.setColor(Color.GRAY);
			g.drawRect(5, n*6-15, 10 * drone1.getMaxRecharge(), 10); //first
			g.fillRect(5, n*6-15, 10 * (drone1.getMaxRecharge()-(drone1.getMaxRecharge() - drone1.getRecharge())), 10);
			g.setColor(Color.WHITE);
		}
		
		if (!drone2.dead()) {
			g.drawRect(5, n*8-15, 10 *  drone2.getMaxBattery(), 10); //first
			g.fillRect(5, n*8-15, 10 * (drone2.getMaxBattery()-(drone2.getMaxBattery() - drone2.getBattery())), 10);
		}
		else {
			g.setColor(Color.GRAY);
			g.drawRect(5, n*8-15, 10 * drone2.getMaxRecharge(), 10); //first
			g.fillRect(5, n*8-15, 10 * (drone2.getMaxRecharge()-(drone2.getMaxRecharge() - drone2.getRecharge())), 10);
			g.setColor(Color.WHITE);
		}
				
		list(g, options, 5, h/3, midFont);
		
		switch(phase) {
		case ANIMATING: //Tech and Skill animations
			if (player.getTechChosen().isAnimating()) player.getTechChosen().animate(g);
			else if (player.getSkillChosen().isAnimating()) player.getSkillChosen().animate(g);
			
			for (int i = 0; i < eParty.size(); i++) {
				Enemy e = eParty.get(i);
				
				if (e.getTechChosen().isAnimating()) e.getTechChosen().animate2(g);
				else if (e.getSkillChosen().isAnimating()) e.getSkillChosen().animate2(g);
			}
			break;
		case COMMAND1:
			g.setColor(Color.BLACK);
			g.fillRect(w/3, h*3/5, w/6, h/2);
			
			g.setColor(Color.WHITE);
			g.drawString(drone1.getName(), w/3 + 5, h*3/5 + midFont.getSize());
			g.drawString("__________________", w/3 + 5, h*3/5 + midFont.getSize());
			
			listCommands(g, w/3 + 5, h*3/5 + midFont.getSize()*3, midFont, drone1);
			break;
			
		case COMMAND2:
			g.setColor(Color.BLACK);
			g.fillRect(w/3, 0, w/6, h/2);
			
			g.setColor(Color.WHITE);
			g.drawString(drone2.getName(), w/3 + 5, 0 + midFont.getSize());
			g.drawString("__________________", w/3 + 5, 0 + midFont.getSize());
			
			listCommands(g, w/3 + 5, 0 + midFont.getSize()*3, midFont, drone2);
			break;
			
		case DRONES: //Swap drones
			g.setColor(Color.BLACK);
			g.fillRect(w/6, 0, w/6, h);
			
			setFont(g, midFont);
			
			String[] dronesList = new String[drones.size()];
			for (int i = 0; i < drones.size(); i++) {
				dronesList[i] = drones.get(i).getName();
			}
			
			listDrones(g, w/6 + 5, 25);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, h - (h/10), w, h/9);
			g.setColor(Color.WHITE);
			
			Drone d = drones.get(choice);
			
			if (!d.dead()) g.drawString("Battery: " + d.getBattery() + " / " + d.getMaxBattery() + "; " + d.getDescription(), 5, h - (h/15));
			else g.drawString("Recharging: " + d.getRecharge() + " / " + d.getMaxRecharge() + "; " + d.getDescription(), 5, h - (h/15));
			
			break;
			
		case END:
			g.setColor(Color.BLACK);
			g.fillRect(w/3, h/38, w/3, h/3);
			
			g.setColor(Color.WHITE);
			int gained = eParty.get(0).getExp() * eParty.size();
			g.drawString("Experience Gained: " + eParty.get(0).getExp() + " x" + eParty.size() + " = " + gained, w/3 + 5, h/19);
				
			g.drawString("Lv. " + player.getLevel(), w/3 + 5, h/9);
			g.drawString(player.getPriorExp() + " + " + gained + " = " + (player.getPriorExp() + gained), w/3 + 5, h/7);
			
			g.drawString("Total Exp: " + player.getExp() + " / " + player.getNextLv(), w/3 + 5, h/6 + 5);
			if (player.getLevel() > player.getPriorLv()) {
				g.drawString("Level Up! " + player.getPriorLv() + " to " + player.getLevel(), w/3 + w/10, h/9 + (h/10));
			}
			
			int mGain = eParty.get(0).getMoney() * eParty.size();
			g.drawString("Money Gained: " + mGain, w/3 + 5, h/6 + (h/12));
			g.drawString("Total Money: " + Player.money, w/3 + 5, h/6 + (h/12) + 25);
			
			if (itemDropped != "") {
				g.drawString(itemDropped, w/3 + 5, h/6 + (h/12) + 70);
			}
			
			break;
		case ITEM:
			g.setColor(Color.BLACK);
			g.fillRect(w/6, 0, w/6, h);
			
			g.setColor(Color.WHITE);
			
			for (int i = 0; i < player.getInventory().size(); i++) {
				Item it = player.getInventory().get(i);
				
				g.setColor(Color.WHITE);
				if (choice == i) g.setColor(Color.RED);
				
				g.drawString(it.getName() + " x" + it.getStock(), w/6, 25 + (i * midFont.getSize()));
			}
			break;
			
		case SELECTION: //Main move selection
			listWithChoice(g, options, 5, h/3, midFont); //MOVES LIST
			break;
		case SKILL:
			g.setColor(Color.BLACK);
			g.fillRect(w/6, 0, w/6, h/4);
			g.fillRect(w/6, h-50, w*3/4, 100);
			
			setFont(g, midFont);
			//Skills List
			for (int i = 0; i < player.getSkills().size(); i++) {
				Skill skill = player.getSkills().get(i);
				
				if (choice == i) {
					g.drawString(skill.getDescription(), 0, h-25);
					g.setColor(Color.RED);
				}
				g.drawString(skill.getName(), w/6 + 5, 25 + (i * 25));
				g.setColor(Color.WHITE);
			}
			
			break;
		case TARGET:
			break;
		case TECH:
			g.setColor(Color.BLACK);
			g.fillRect(w/6, 0, w/3, h);
			g.fillRect(w/6, h-50, w*3/4, 100);
			
			setFont(g, midFont);
			
			//Spells List
			g.drawString("ATK", w/6 + 5, 25);
			for (int i = 0; i < player.getOffs().size(); i++) {
				Tech t = player.getOffs().get(i);
				if (i == choice && techChoice == 0) {
					g.drawString(t.getDescription(), 0, h-25);
					g.setColor(Color.RED);
				}
				g.drawString(t.getName() + " -" + t.getCost(), w/6 + 5, 50 + (50 * i));
				g.setColor(Color.WHITE);
			}
			g.drawString("CURE", w/6 + 150, 25);
			for (int i = 0; i < player.getCures().size(); i++) {
				Tech t = player.getCures().get(i);
				if (i == choice && techChoice == 1) {
					g.drawString(t.getDescription(), 0, h-25);
					g.setColor(Color.RED);
				}
				g.drawString(t.getName() + " -" + t.getCost(), w/6 + 150, 50 + (50 * i));
				g.setColor(Color.WHITE);
			}
			g.drawString("DEF", w/6 + 300, 25);
			for (int i = 0; i < player.getDefs().size(); i++) {
				Tech t = player.getDefs().get(i);
				if (i == choice && techChoice == 2) {
					g.drawString(t.getDescription(), 0, h-25);
					g.setColor(Color.RED);
				}
				g.drawString(t.getName() + " -" + t.getCost(), w/6 + 300, 50 + (50 * i));
				g.setColor(Color.WHITE);
			}
			
			
			break;
		default:
			break;
		}
	}
	
	private void listDrones(Graphics g, int width, int height) {
		g.setFont(midFont);
		
		for (int i = 0; i < drones.size(); i++) {
			int spacing = height + ((font.getSize()+10) * i);
			Drone d = drones.get(i);
			
			if (d.dead()) {
				g.drawRect(width + 50, spacing, 10 * d.getMaxRecharge(), 10);
				g.setColor(Color.RED);
				g.fillRect(width + 50, spacing, 10 * (d.getMaxRecharge()-(d.getMaxRecharge() - d.getRecharge())), 10);
				g.setColor(Color.GRAY);
			}
			else {
				g.drawRect(width + 50, spacing, 10 * d.getMaxBattery(), 10);
				g.setColor(Color.GREEN);
				g.fillRect(width + 50, spacing, 10 * (d.getMaxBattery()-(d.getMaxBattery() - d.getBattery())), 10);
				g.setColor(Color.WHITE);
			}
			if (choice == i) g.setColor(Color.RED);
			g.drawString(d.getName(), width, spacing);
			g.setColor(Color.WHITE);
			if (i == drone1.getIndex() || i == drone2.getIndex()) {
				g.drawString("*", w/6 - 10, spacing);
			}
			
			if (choice < 0) choice = drones.size()-1;
			else if (choice > drones.size()-1) choice = 0;
		}
	}
	
	private void listCommands(Graphics g, int width, int height, Font font, Drone d) {
		g.setFont(font);
		String[] list = d.getCommands();
		
		for (int i = 0; i < list.length; i++) {
			if (d.getCommand()-1 == i) {
				g.setFont(largeFont);
				g.drawString("*", width-5, height + ((font.getSize()+10) * i));
				g.setFont(midFont);
			}
			if (choice == i) g.setColor(Color.RED);
			g.drawString(list[i], width + 5, height + ((font.getSize()+10) * i));
			g.setColor(Color.WHITE);
		}
		
		if (choice < 0) choice = list.length - 1;
		else if (choice > list.length - 1) choice = 0;
	}
	
	//BATTLE ANIMATIONS//////////////////////////////////
	public void startAnimation() {
		enemyChoices();
		droneChoices();
		pointReset();
		
		phase = PHASE.ANIMATING;
	}
	
	private void attackSequence() {
		if (!player.hasGone()) {
			player.setAnimation(true);
			move(player.getChoice()+1);
		}
		else if (!player.getAnimating()) {
			checkDeath();
			if (end) return;
			droneAnimations();
		}
	}
	
	//Enemy chooses move
	public void enemyChoices() {
		for (int i = 0; i < eParty.size(); i++) {
			Enemy e = eParty.get(i);
			
			if (e.getChoice() == 3 || e.getChoice() == 4) {
				e.setFriendly(eParty.get(random.nextInt(eParty.size())));
			}
			else if (e.getChoice() == 5) {
				e.setFriendly(eParty.get(random.nextInt(eParty.size())));
				e.setTarget(player);
			}
			else e.setTarget(player);
			
			if (e.getHP() > 0) e.behavior();
			else e.setChoice(0);
		}
	}
	
	public void droneChoices() {
		drone1.setTarget(eParty.get(random.nextInt(eParty.size())));
		drone2.setTarget(eParty.get(random.nextInt(eParty.size())));
	}
	
	//Enemy changes friendly target if friendly is dead
	private void changeFriendly(Enemy e) {
		e.setFriendly(eParty.get(random.nextInt(eParty.size())));
		for (int i = 0; i < 20; i++) {
			if (e.getFriendly().getHP() == 0) {
				e.setFriendly(eParty.get(random.nextInt(eParty.size())));
			}
			else return;
		}
		
		for (int i = 0; i < eParty.size(); i++) {
			e.setFriendly(eParty.get(i));
			if (eParty.get(i).getHP() != 0) return;
		}
	}
		
	private void enemyAnimations() {
		for (int i = 0; i < eParty.size(); i++) {
			Enemy e = eParty.get(i);
			
			if (e.getHP() == 0) {
				e.setGone(true);
			}
			else if (e.getChoice() != 0) {
				checkDeath();
				e.startAnimation();
				enemyMove(i);
			}
			else if (secondMove == true) {
				secondMove = false;
				changeFriendly(e);
				e.startAnimation();
				enemyMove(i);
			}
			
			if (!e.hasGone()) {
				return;
			}
		}
	}
	
	private void droneAnimations() {
		checkDeath();
		if (end) return;
		if (!drone1.dead() && !drone1.hasGone()) {
			drone1.setAnimating(true);
		}
		else if (!drone2.dead() && !drone1.getAnimating() && !drone2.hasGone()) {
			checkDeath();
			if (end) return;
			drone2.setAnimating(true);
		}
		else {
			enemyAnimations();
			endTurn();
		}
	}
	
	//Drones change target if hp = 0
	public void changeTarget(Drone d) {
		if (d.getEnemy().getHP() == 0) {
			d.setTarget(eParty.get(0));
		}
		if (d.getEnemy().getHP() == 0) {
			d.setTarget(eParty.get(1));
		}
		if (d.getEnemy().getHP() == 0) {
			d.setTarget(eParty.get(2));
		}
	}
	
	//END of each TURN
	public void endTurn() {
		for (int i = 0; i < eParty.size(); i++) {
			if (!eParty.get(i).hasGone()) return;
		}
		
		if (drone1.dead()) drone1.setRecharge(1);
		if (drone2.dead()) drone2.setRecharge(1);
		
		for (int i = 0; i < drones.size(); i++) {
			Drone d = drones.get(i);
			
			if (d.getIndex() == drone1.getIndex() || d.getIndex() == drone2.getIndex()) continue; 
			else if (!d.dead()) drones.get(i).chargeBattery();
			else drones.get(i).setRecharge(1);
		}
		
		phase = PHASE.SELECTION;
		reset();
			
		turnNumber++;
		
		checkDeath();
		
		//Stat Timers and point resets
		player.timer();
		
		for (int i = 0; i < eParty.size(); i++) {
			Enemy e = eParty.get(i);
			
			e.timer();
		}
		checkEffects();
	}

	public void pointReset() {
		player.setMessage("");
		player.setGone(false);
		
		player.setDP(-player.getDP());
		player.setCP(-player.getCP());
		player.setSP(-player.getSP());
		player.setRP(-player.getRP());
		
		drone1.setGone(false);
		drone2.setGone(false);
		
		for (int i = 0; i < eParty.size(); i++) {
			Enemy e = eParty.get(i);
			e.setMessage("");
			
			e.setDP(-e.getDP());
			e.setCP(-e.getCP());
			e.setSP(-e.getSP());
			e.setRP(-e.getRP());
			
			e.setGone(false);
		}
	}
	
	private void move(int move) {
		if (player.getHP() == 0) move = 0; //player death
		
		switch (move) {
		
		//ATTACK
		case 1: player.changeState(STATE.ATTACK); break;
		
		//DEFEND
		case 2: 
			player.changeState(STATE.DEFEND);
			player.setDef(player.getBaseDef()*2);
			player.setDefTimer(1);
			break;
		
		//FLEE
		case 3:
			int fleeChance = eParty.get(0).getFleeChance();
			int rand = random.nextInt(100);
			if (rand <= fleeChance) {
				Player.fleeing = true;
				System.out.println("Running");
				eParty.get(0).setCoordinates(Player.x, Player.y);
				endBattle();
				
				for (int i = 0; i < eParty.size(); i++) {
					eParty.get(i).resetStats();
				}
				
				return;
			}
			else if (eParty.get(0).getFleeChance() == 0) {
				player.setMessage("Cannot Flee this Battle");
				player.setAnimation(false);
			}
			else {
				player.setMessage("Flee failed");
				player.setAnimation(false);
			}
			break;
		
		//TECH
		case 4: 
			player.changeState(STATE.TECH);
			switch(player.getTypeChosen()) {
			case 0:
				Enemy target = player.getTarget();
				player.getTechChosen().attack(target);
				break;
			case 1: player.getTechChosen().heal(player);
			case 2: player.getTechChosen().alter(player);
			}
			break;
		
		//SKILL
		case 5: 
			player.changeState(STATE.SKILL);
			String type = player.getSkillChosen().getType();
			if (type == "Offensive") {
				Enemy target = player.getTarget();
				player.getSkillChosen().attack(target);
			}
			else if (type == "Curative") player.getSkillChosen().heal();
			else if (type == "Defensive") player.getSkillChosen().alter();
			break;
		
		//ITEM
		case 6: player.changeState(STATE.ITEM); break;
		}	
	}
	
	public void enemyMove(int num) {
		Enemy e = eParty.get(num);
		if (e.getHP() == 0) choice = 0; //Check enemy death
		
		int choice = e.getChoice();
		
		if (e.getChoice() == 0 && e.getChoice2() != 0) {
			choice = e.getChoice2();
		}
		
		switch(choice) {
		
		//ATTACK
		case 1: e.changeState(STATES.ATTACK); break;
	
		//OFFENSIVE MAGIC
		case 2: e.changeState(STATES.TECH); 
		
		Brawler target = e.getTarget();
		e.getTechChosen().attack(target);
		break;
		
		//CURATIVE MAGIC
		case 3: e.changeState(STATES.TECH); 
		e.setFriendly(e.getParty().get(random.nextInt(e.getParty().size())));
		
		if (e.getFriendly().getHP() == 0) {
			changeFriendly(e);
		}
		
		Enemy friend = e.getFriendly();
		
		e.getTechChosen().heal(friend); 
		break;
		
		//DEFENSIVE MAGIC
		case 4: e.changeState(STATES.TECH); 
			if (e.getFriendly().getHP() == 0) {
				changeFriendly(e);
			}
			
			Enemy friend2 = e.getFriendly();
			
			e.getTechChosen().alter(friend2);
			
			break;
		
		//SKILL
		case 5: e.changeState(STATES.SKILL); 
		Brawler target3 = e.getTarget();
		
		e.setFriendly(e.getParty().get(random.nextInt(e.getParty().size())));
		
		if (e.getFriendly().getHP() == 0) {
			changeFriendly(e);
		}
		
		Enemy friend3 = e.getFriendly();
		
		switch (e.getSkillChosen().getType()) {
		case "Offensive": e.getSkillChosen().attack(target3); break;
		case "Curative": e.getSkillChosen().heal(friend3); break;
		case "Defensive": e.getSkillChosen().alter(e); break;
		}
		break;
		
		//DEAD
		default: break;
		}
		
		if (e.getChoice() == 0) e.setChoice2(0);
		e.setChoice(0);
	}
	
	public void checkEffects() {
		//Player Status Effects
		if (player.getPoisoned()) {
			if (player.getHP()/20 == 0) {
				player.setDP(1);
				player.setHP(-1);
			}
			else {
				player.setDP((player.getHP() * 5)/100);
				player.setHP(-(player.getHP() * 5)/100);
			}
		}
		if (player.getBurned()) {
			if (player.getHP()/10 == 0) {
				player.setDP(1);
				player.setHP(-1);
			}
			else {
				player.setDP(player.getHP()/10);
				player.setHP(-player.getHP()/10);
			}
		}
		if (player.getRadio()) {
			if (player.getMaxTP()/20 == 0) {
				player.setSP(1);
				player.setTP(-1);
			}
			else {
				player.setSP(player.getTP()/20);
				player.setTP(-(player.getTP()/20));
			}
		}
		
		player.setStatusEffected();
		
		//Regen
		if (player.getRegen() > 0) {
			player.setHP(player.getRegen());
			player.setCP(player.getRegen());
		}
		
		//Enemy statuses
		for (int i = 0; i < eParty.size(); i++) {
			Enemy e = eParty.get(i);
			if (e.getPoisoned()) {
				if (e.getHP()/20 == 0) {
					e.setDP(1);
					e.setHP(-1);
				}
				else {
					e.setDP(e.getHP()/20);
					e.setHP(-(e.getHP()/20));
				}
			}
			if (e.getHacked()) {
				if (e.getHP()/20 == 0) {
					e.setDP(1);
					e.setHP(-1);
				}
				else {
					e.setDP(e.getHP()/20);
					e.setHP(-(e.getHP()/20));
				}
			}
			if (e.getBurned()) {
				if (e.getHP()/10 == 0) {
					e.setDP(1);
					e.setHP(-1);
				}
				else {
					e.setDP(e.getHP()/10);
					e.setHP(-(e.getHP()/10));
				}
			}
			if (e.getRadio()) {
				if (e.getMaxTP() == 0) {
					e.setTP(-1);
					e.setSP(1);
				}
				else {
					if (e.getMaxTP()/20 == 0) {
						e.setSP(1);
						e.setTP(-1);
					}
					else {
						e.setSP(e.getTP()/20);
						e.setTP(-(e.getTP()/20));
					}
				}
			}
			
			e.setStatusEffected();
			
			//Regen
			if (e.getRegen() > 0) {
				e.setHP(e.getRegen());
				e.setCP(e.getRegen());
			}
		}
	}
	
	//Player and enemy deaths
	public void checkDeath() {
		boolean dead = true;
		boolean enemiesDead = true;
		
		for (int i = 0; i < eParty.size(); i++) {
			if (eParty.get(i).getHP() > 0) {
				enemiesDead = false;
			}
		}
		if (player.getHP() > 0) {
			dead = false;
		}
		
		if (enemiesDead) {
			end = true;
			victory();
		}
		else if (dead) {
			end = true;
			drone1.setGone(true);
			drone2.setGone(true);
			
			for (int i = 0; i < eParty.size(); i++) {
				eParty.get(i).setChoice(0);
				eParty.get(i).setChoice2(0);
			}
			
			if (drone1.getIndex() == 8) {
				drone1.setCommand(2);
				drone1.setGone(false);
				return;
			}
			else if (drone2.getIndex() == 8) {
				drone2.setCommand(2);
				drone2.setGone(false);
				return;
			}
			
			endGame();
		}
		else {
			changeTarget(drone1);
			changeTarget(drone2);
		}
	}
	
	//END OF BATTLE
	private void victory() {
		phase = PHASE.END;
		chosen = false;
		
		Player.money += (eParty.get(0).getMoney() * eParty.size());
		getDrop();
		
		player.changeState(STATE.VICTORY);
		player.setPriorLv(); player.setPriorExp();
		player.gainExp(eParty.get(0).getExp() * eParty.size());
		
		eParty.get(0).kill();
		
		for (int i = 0; i < eParty.size(); i++) { 
			Bestiary.encountered[eParty.get(i).getIndex()] = true;
		}
		
		for (int i = 0; i < player.getDrones().size(); i++) {
			Drone d = player.getDrones().get(i);
			
			if (d.getIndex() != drone1.getIndex() && d.getIndex() != drone2.getIndex()) {
				if (!d.dead()) d.chargeBattery();
				else d.setRecharge(1);
			}
		}
		
		player.setDrone1(drone1);
		player.setDrone2(drone2);
		
		Level.music.differentSound("Victory.wav");
	}
	
	private void endGame() {
		Game.state = Game.STATE.DEAD;
	}
	
	private void getDrop() {
		Enemy e = eParty.get(0);
		
		if (e.getDropChance() > 0) {
			int chance = random.nextInt(100);
			if (chance <= e.getDropChance()) {
				player.addItem(e.getDrop());
				itemDropped = "Item Received: " + e.getDrop().getName();
			}
		}
	}
	
	private void endBattle() {
		Game.state = Game.STATE.GAME;
		secondMove = false;
		player.changeState(STATE.NORMAL);
		
		Level.music.resumeSound(Level.area + ".wav");
	}
	
}
