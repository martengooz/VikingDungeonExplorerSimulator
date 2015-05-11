import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;


public class MapManager {

	private static Room firstRoom;
	private static Room secondRoom;
	
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
		secondRoom = new Room("res\\images\\Room.png");
		
		firstRoom.addItem(new Item("ID", "Viking", "A viking buddy", new Rectangle(200, 200, 230, 250), new Point(0, 0), new Point(0, 0), "res\\images\\player1.png"));
		
		firstRoom.setExit(secondRoom, 0);
		secondRoom.setExit(firstRoom, 2);
		
		firstRoom.setExit(firstRoom, 1);
		firstRoom.setExit(firstRoom, 3);
	}
}
