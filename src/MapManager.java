import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;


public class MapManager {

	private static Room firstRoom;
	
	/**
	 * Get the starting room in the game.
	 * @return The room the game starts in.
	 */
	public static Room getFirstRoom() {return firstRoom;}
	
	/**
	 * Generate the map of the game.
	 */
	public static void generateMap() {
		//Create rooms
		firstRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		Room secondRoom = new Room("res\\images\\Rooms\\Room2.png", "res\\images\\Door.png");
		Room thirdRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		Room fourthRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		
		//Add exits
		firstRoom.setExit(secondRoom, 0);
		secondRoom.setExit(firstRoom, 2);
		
		firstRoom.setExit(thirdRoom, 1);
		thirdRoom.setExit(firstRoom, 3);
		
		thirdRoom.setExit(fourthRoom, 2);
		fourthRoom.setExit(thirdRoom, 0);
			
		//Add items and entities
		firstRoom.addItem(new Item("IRON_SHOVEL", "Shovel", "A shovel to dig with.", new Rectangle(200, 200, 100, 100), new Point(0, 0), new Point(0, 0), "res\\images\\Items\\Shovel.png"));
		
		secondRoom.addEntity(new Entity(new Rectangle(350, 500, 100, 100), "res\\images\\Items\\GrassPatch.png", false));
		secondRoom.addEntity(new Entity(new Rectangle(950, 550, 95, 75), "res\\images\\Items\\Rock.png", true));
		secondRoom.addEntity(new Entity(new Rectangle(750, 70, 95, 75), "res\\images\\Items\\Rock.png", true));
		
		thirdRoom.addEntity(new Entity(new Rectangle(650, 470, 95, 75), "res\\images\\Items\\GrassPatch.png", false));
		
		fourthRoom.addItem(new Item("GOLD_KEY", "Golden Key", "A shiny golden key.", new Rectangle(625, 450, 30, 65), new Point(0, 0), new Point(0, 0), "res\\images\\Items\\Key.png"));
	}
}
