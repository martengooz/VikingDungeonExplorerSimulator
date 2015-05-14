import org.newdawn.slick.geom.Rectangle;


public class MapManager {

	private static Room firstRoom; // The room the player starts in. 
	private static Room lastRoom; // The room the player needs to reach to win the game. 
	
	/**
	 * Get the starting room in the game.
	 * @return The room the game starts in.
	 */
	public static Room getFirstRoom() {return firstRoom;}
	
	/**
	 * Get the last room of the game. The player needs to reach this room to win.
	 * @return the last room of the game.
	 */
	public static Room getLastRoom() {return lastRoom;}
	
	/**
	 * Generate the map of the game.
	 */
	public static void generateMap() {
		//Create rooms
		firstRoom = new Room("res\\images\\Rooms\\Room2.png", "res\\images\\Door.png");
		Room secondRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		Room thirdRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		Room fourthRoom = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png");
		lastRoom = new Room("res\\images\\Rooms\\Outside.png", "res\\images\\Door.png");
		
		// Configure rooms
		firstRoom.setExit(secondRoom, 2);
		Entity rock = new Entity(new Rectangle(950, 550, 95, 75), "res\\images\\Items\\Rock.png", true);
		rock.addInteraction("Rock", "There seems to be something hiding under the rock...", null, 
				new Item("IRON_SHOVEL", "Shovel", "A shovel to dig with.", new Rectangle(0, 0, 100, 100), "res\\images\\Items\\Shovel.png"), false, false, false);
		rock.addInteraction("Rock", "There is nothing here.", null, null, true, false, false);
		firstRoom.addEntity(rock);
		firstRoom.addEntity(new Entity(new Rectangle(350, 500, 100, 100), "res\\images\\Items\\GrassPatch.png", false));
		firstRoom.addEntity(new Entity(new Rectangle(750, 70, 95, 75), "res\\images\\Items\\Rock.png", true));
		
		
		secondRoom.setExit(firstRoom, 0);
		secondRoom.setExit(thirdRoom, 1);
		Entity pileOfDirt = new Entity(new Rectangle(1100, 310, 100, 100), "res\\images\\Items\\PileOfDirt.png", true);
		pileOfDirt.addInteraction("Pile of dirt", "You removed the dirt with your mighty shovel.", "IRON_SHOVEL", null, false, true, false);
		pileOfDirt.addInteraction("Pile fo dirt", "There is a big pile of dirt in the way. If only I had a shovel..", null, null, true, false, false);
		secondRoom.addEntity(pileOfDirt);
		
		
		thirdRoom.setExit(secondRoom, 3);
		thirdRoom.setExit(fourthRoom, 2);
		thirdRoom.addEntity(new Entity(new Rectangle(650, 470, 95, 75), "res\\images\\Items\\GrassPatch.png", false));
		NPC greybeard = new NPC(new Rectangle(300, 300, 128, 128), new String[] {
			"res\\images\\Entities\\greybeardLeft.png",
			"res\\images\\Entities\\greybeardRight.png",
			"res\\images\\Entities\\greybeardRight.png",
			"res\\images\\Entities\\greybeardLeft.png" });
		greybeard.addInteraction("Greybeard", "You found my shiny key! Here have this brass key instead, it will aid you in your travels!", "GOLD_KEY", 
				new Item("BRASS_KEY", "Brass key", "A shiny brass key with the name \"Greybeard\" engraved.", new Rectangle(0, 0, 30, 65), "res\\images\\Items\\Key.png"), false, false, true);
		greybeard.addInteraction("Greybeard", "Thank you, mighty hero Redbeard!", "BRASS_KEY", null, true, false, false);
		greybeard.addInteraction("Greybeard", "I am the mighty greybeard! Give me a gift and i will reward you greatly!", null, null, false, false, false);
		greybeard.addInteraction("Greybeard", "I seem to have missplaced my golden key! How can I pillage without it?", null, null, true, false, false);
		thirdRoom.addNPC(greybeard);
		
		fourthRoom.setExit(thirdRoom, 0);
		fourthRoom.setExit(lastRoom, 2);
		fourthRoom.addItem(new Item("GOLD_KEY", "Golden Key", "A shiny golden key.", new Rectangle(625, 450, 30, 65), "res\\images\\Items\\Key.png"));
		
		lastRoom.setExit(fourthRoom, 0);

	}
}
