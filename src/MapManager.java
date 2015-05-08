
public class MapManager {

	private static Room firstRoom;
	
	/**
	 * Get the starting room in the game
	 * @return The room the game starts in.
	 */
	public static Room getFirstRoom() {return firstRoom;}
	
	/**
	 * Generate the map of the game.
	 */
	public static void generateMap() {
		firstRoom = new Room("res\\images\\Room.png");
	}
}
