package storage;

public class Story {

	//Bosses Defeated
	public static boolean[] bossesDefeated = new boolean[] {false, false, false};
	
	//Cutscene arrays
	public static boolean[] docks = new boolean[2];
	public static boolean[] mall = new boolean[1];
	
	//Story stake
	public static int docks_Story = 0;
	/*
	 * 0 - Hutch in the starting room
	 * 1 - Hutch permanently moves to the mall
	 */
	public static int mall_Story = 0;
}
