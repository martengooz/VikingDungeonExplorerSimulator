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
		firstRoom = new Room("res\\images\\Rooms\\Room2.png", "res\\images\\Door.png");
		Room secondRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		Room thirdRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		Room fourthRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		
		//Add exits
		firstRoom.setExit(secondRoom, 2);
		secondRoom.setExit(firstRoom, 0);
		
		secondRoom.setExit(thirdRoom, 1);
		thirdRoom.setExit(secondRoom, 3);
		
		thirdRoom.setExit(fourthRoom, 2);
		fourthRoom.setExit(thirdRoom, 0);
			
		//Create Entities
		Entity rock = new Entity(new Rectangle(950, 550, 95, 75), "res\\images\\Items\\Rock.png", true);
		rock.addInteraction("Rock", "There seems to be something hiding under the rock...", null, 
				new Item("IRON_SHOVEL", "Shovel", "A shovel to dig with.", new Rectangle(200, 200, 100, 100), new Point(0, 0), new Point(0, 0), "res\\images\\Items\\Shovel.png"), false, false);
		rock.addInteraction("Rock", "There is nothing here.", null, null, true, false);
		
		Entity pileOfDirt = new Entity(new Rectangle(1100, 310, 100, 100), "res\\images\\Items\\PileOfDirt.png", true);
		pileOfDirt.addInteraction("Rock", "You removed the dirt with your mighty shovel.", "IRON_SHOVEL", null, false, true);
		pileOfDirt.addInteraction("Rock", "There is a big pile of dirt in the way. If only I had a shovel..", null, null, true, false);
		
		//Add items and entities
		firstRoom.addEntity(rock);
		firstRoom.addEntity(new Entity(new Rectangle(350, 500, 100, 100), "res\\images\\Items\\GrassPatch.png", false));
		firstRoom.addEntity(new Entity(new Rectangle(750, 70, 95, 75), "res\\images\\Items\\Rock.png", true));
		
		secondRoom.addEntity(pileOfDirt);
		
		thirdRoom.addEntity(new Entity(new Rectangle(650, 470, 95, 75), "res\\images\\Items\\GrassPatch.png", false));
		
		fourthRoom.addItem(new Item("GOLD_KEY", "Golden Key", "A shiny golden key.", new Rectangle(625, 450, 30, 65), new Point(0, 0), new Point(0, 0), "res\\images\\Items\\Key.png"));
	}
}
