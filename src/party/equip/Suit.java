package party.equip;

import party.Brawler;

public abstract class Suit {

	protected Brawler player;
	
	protected String name;
	protected double modifier = 1.0; //physical
	protected double modifier2 = 1.0; //tech
	protected int cost;
	protected int index;
	protected String description;
	protected String effect = "None"; 
	
	public String getName() {
		return name;
	}
	
	public double getModifier() {
		return modifier;
	}
	
	public double getModifier2() {
		return modifier2;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEffect() {
		return effect;
	}
	
	public void effect() {
		
	}
	
}
