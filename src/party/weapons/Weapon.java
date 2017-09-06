package party.weapons;

public abstract class Weapon {

	protected String name;
	protected int cost;
	protected int index;
	protected String description;
	protected double modifier;
	protected String type;
	protected String effect;
	
	public String getName() {
		return name;
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
	public double getModifier() {
		return modifier;
	}
	public String getType() {
		return type;
	}
	public String getEffect() {
		return effect;
	}
	
}
