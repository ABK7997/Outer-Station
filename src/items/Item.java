package items;

import party.Brawler;

public abstract class Item {

	protected Brawler player;
	
	protected String name;
	protected int cost;
	protected int stock = 1;
	protected int effect;
	protected int index;
	protected String description;
	
	public String getName() {
		return name;
	}
	public int getCost() {
		return cost;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int num) {
		stock = num;
	}
	public int getEffect() {
		return effect;
	}
	public int getIndex() {
		return index;
	}
	public String getDescription() {
		return description;
	}
	
	public void stock(int num) {
		stock += num;
		if (stock == 0) {
			if (stock == 0) player.getInventory().remove(this);
		}
	}
	public void useItem() {
	}
	
	//Assign to player
	public void setBrawler(Brawler b) {
		player = b;
	}
	
}
