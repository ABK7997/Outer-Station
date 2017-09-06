package storage;

import items.*;

import java.util.ArrayList;

import party.Brawler;
import party.drones.*;
import party.equip.*;
import party.weapons.*;
import battle.Skill;
import battle.Tech;
import battle.cure.*;
import battle.def.*;
import battle.off.*;
import battle.skills.*;

public class Storage {

	private Brawler player;
	
	public static ArrayList<Drone> allDrones = new ArrayList<Drone>();
	public static ArrayList<Weapon> allWeapons = new ArrayList<Weapon>();
	public static ArrayList<Suit> allSuits = new ArrayList<Suit>();
	public static ArrayList<Skill> allSkills = new ArrayList<Skill>();
	
	public static ArrayList<Tech> offTechs = new ArrayList<Tech>();
	public static ArrayList<Tech> cureTechs = new ArrayList<Tech>();
	public static ArrayList<Tech> defTechs = new ArrayList<Tech>();
	
	public static ArrayList<Item> allItems = new ArrayList<Item>();
	
	public Storage(Brawler b) {
		player = b;
		
		//Drones
		allDrones.add(new Assault_Drone(player));
		allDrones.add(new Shock_Drone(player));
		allDrones.add(new Medic_Drone(player));
		allDrones.add(new Shell_Drone(player));
		allDrones.add(new Status_Drone(player));
		allDrones.add(new Crippler_Drone(player));
		allDrones.add(new Buff_Drone(player));
		allDrones.add(new Suicide_Drone(player));
		allDrones.add(new Revival_Drone(player));
		allDrones.add(new Null_Drone());
		
		//Weapons
		allWeapons.add(new DSIS_Pistol());
		allWeapons.add(new Dagger());
		allWeapons.add(new Military_Rifle());
		allWeapons.add(new Magnum());
		allWeapons.add(new Hand_Cannon());
		allWeapons.add(new Saber());
		
		allWeapons.add(new Laser_Pistol());
		allWeapons.add(new Laser_Rapier());
		allWeapons.add(new Ion_Beam());
		
		allWeapons.add(new Dart_Gun());
		allWeapons.add(new Venom_Blade());
		allWeapons.add(new Toxin_Cannon());
		
		allWeapons.add(new Hack_Device());
		allWeapons.add(new Hack_Gun());
		allWeapons.add(new Hack_Beam());
		
		allWeapons.add(new Radio_Blade());
		allWeapons.add(new Radio_Generator());
		allWeapons.add(new Particle_Beam());
		
		//Suits
		allSuits.add(new Standard_Suit(player));
		allSuits.add(new Rugged_Suit(player));
		allSuits.add(new Military_Suit(player));
		allSuits.add(new Iron_Suit(player));
		allSuits.add(new Odyssey_Suit(player));
		
		allSuits.add(new Heat_Suit(player));
		allSuits.add(new Hazmat_Suit(player));
		allSuits.add(new Plague_Suit(player));
		
		//Skills
		allSkills.add(new Aim(player));
		allSkills.add(new Auto_Immune(player));
		allSkills.add(new Blind(player));
		allSkills.add(new Booster(player));
		allSkills.add(new Bulk_Up(player));
		allSkills.add(new Encumber(player));
		allSkills.add(new Harden(player));
		allSkills.add(new HP_Boost(player));
		allSkills.add(new Immunity(player));
		allSkills.add(new Nerf(player));
		allSkills.add(new Shell(player));
		allSkills.add(new Sacrifice(player));
		allSkills.add(new Charge(player));
		
		//Off-Techs
		offTechs.add(new Shock(player));
		offTechs.add(new Super_Shock(player));
		offTechs.add(new Voltage(player));
		
		offTechs.add(new Pulse(player));
		offTechs.add(new Super_Pulse(player));
		offTechs.add(new Surge(player));
		
		offTechs.add(new Fumes(player));
		offTechs.add(new Torch(player));
		offTechs.add(new Hack(player));
		offTechs.add(new Radiation(player));
		
		//Cure-Techs
		cureTechs.add(new Heal(player));
		cureTechs.add(new X_Heal(player));
		cureTechs.add(new Revive(player));
		
		cureTechs.add(new Cure(player));
		cureTechs.add(new Burn_Heal(player));
		cureTechs.add(new Stability(player));
		
		cureTechs.add(new Regen(player));
		cureTechs.add(new Stem_Cells(player));
		cureTechs.add(new Recharge(player));
		cureTechs.add(new Rejuvenate(player));
		
		//Def-Techs
		defTechs.add(new Shield(player));
		defTechs.add(new EM_Field(player));
		defTechs.add(new Barrier(player));
		
		defTechs.add(new Phantom(player));
		defTechs.add(new Ghost(player));
		defTechs.add(new Cyber_Shield(player));
		
		//Inventory
		allItems.add(new Medkit(player));
		allItems.add(new Super_Kit(player));
		allItems.add(new X_Medkit(player));
		allItems.add(new Alien_Gel (player));
		
		allItems.add(new Battery(player));
		allItems.add(new Super_Battery(player));
		allItems.add(new X_Battery(player));
		allItems.add(new Alien_Battery(player));
		
		allItems.add(new Antidote(player));
		allItems.add(new Ointment(player));
		allItems.add(new Antirad(player));
		allItems.add(new Toolkit(player));
		allItems.add(new AntiHack(player));
		
		allItems.add(new Life_Pill(player));
		
		allItems.add(new HP_Plus(player));
		allItems.add(new TP_Plus(player));
		allItems.add(new Pwr_Plus(player));
		allItems.add(new Tech_Plus(player));
		allItems.add(new Dex_Plus(player));
		allItems.add(new Evd_Plus(player));
		allItems.add(new Res_Plus(player));
	}
	
}
