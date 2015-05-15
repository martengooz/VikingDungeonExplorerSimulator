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
		// Create rooms
		firstRoom = new Room("res\\images\\Rooms\\Room2_keys.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		lastRoom = new Room("res\\images\\Rooms\\Outside.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		
		Room[] level1 = new Room[3];
		level1[0] = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		level1[1] = new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		level1[2] = (Room) new Room("res\\images\\Rooms\\Room.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		
		Room[] level2 = new Room[4];
		level2[0] = new Room("res\\images\\Rooms\\Room3.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		level2[1] = new Room("res\\images\\Rooms\\Room3.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		level2[2] = new Room("res\\images\\Rooms\\Room3.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		level2[3] = new Room("res\\images\\Rooms\\Room3.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		
		lastRoom = new Room("res\\images\\Rooms\\Outside.png", "res\\images\\Door.png", "res\\images\\LockedDoor.png");
		
		// Configure rooms
		// Level 1 
		firstRoom.setExit(level1[0], 2);
		firstRoom.addEntity(new Entity(new Rectangle(350, 500, 100, 100), "res\\images\\Items\\GrassPatch.png", false));
		firstRoom.addEntity(new Entity(new Rectangle(750, 70, 95, 75), "res\\images\\Items\\Rock.png", true));
		firstRoom.addItem(new Item("IRON_SHOVEL", "Shovel", "A shovel to dig with.", new Rectangle(800, 400, 100, 100), "res\\images\\Items\\Shovel.png"));
		
		
		level1[0].setExit(firstRoom, 0);
		level1[0].setExit(level1[1], 1);
		level1[0].setExit(level2[0], 3, "GREYBEARD_KEY");
		Entity pileOfDirt = new Entity(new Rectangle(1100, 310, 100, 100), "res\\images\\Items\\PileOfDirt.png", true);
		pileOfDirt.addInteraction("Pile of dirt", "You removed the dirt with your mighty shovel.", "IRON_SHOVEL", null, false, true, false);
		pileOfDirt.addInteraction("Pile fo dirt", "There is a big pile of dirt in the way. If only I had a shovel..", null, null, true, false, false);
		level1[0].addEntity(pileOfDirt);
		Entity runestone = new Entity(new Rectangle(100, 150, 102, 133), "res\\images\\Items\\Runestone.png", true);
		runestone.addInteraction("Runestone", "\"Greybeards treasurechamber\"", null, null, true, false, false);
		level1[0].addEntity(runestone);
		
		level1[1].setExit(level1[0], 3);
		level1[1].setExit(level1[2], 2);
		level1[1].addEntity(new Entity(new Rectangle(650, 470, 95, 75), "res\\images\\Items\\GrassPatch.png", false));
		NPC greybeard = new NPC(new Rectangle(300, 300, 128, 128), new String[] {
			"res\\images\\Entities\\greybeardLeft.png",
			"res\\images\\Entities\\greybeardRight.png",
			"res\\images\\Entities\\greybeardRight.png",
			"res\\images\\Entities\\greybeardLeft.png" });
		greybeard.addInteraction("Greybeard", "You found my shiny key! Here have this key instead, it will aid you in your travels!", "GOLD_KEY", 
				new Item("GREYBEARD_KEY", "Greybeard key", "A shiny key with the name \"Greybeard\" engraved.", new Rectangle(0, 0, 30, 65), "res\\images\\Items\\Key.png"), false, false, true);
		greybeard.addInteraction("Greybeard", "Thank you, mighty hero Redbeard!", "BRASS_KEY", null, true, false, false);
		greybeard.addInteraction("Greybeard", "I am the mighty greybeard! Give me a gift and i will reward you greatly!", null, null, false, false, false);
		greybeard.addInteraction("Greybeard", "I seem to have missplaced my golden key! How can I pillage without it?", null, null, true, false, false);
		level1[1].addNPC(greybeard);
		
		
		level1[2].setExit(level1[1], 0);
		level1[2].addItem(new Item("GOLD_KEY", "Golden Key", "A shiny golden key.", new Rectangle(625, 450, 30, 65), "res\\images\\Items\\Key.png"));
		
		
		// Level 2
		level2[0].setExit(level2[1], 2);
		level2[0].setExit(level2[3], 3);
		level2[0].setExit(level1[0], 1);
		level2[0].addItem(new Item("COINS_1", "A pile of coins", "A shiny pile of coins.", new Rectangle(125, 450, 100, 100), "res\\images\\Items\\Coins.png"));
		level2[0].addItem(new Item("COINS_2", "A pile of coins", "A shiny pile of coins.", new Rectangle(525, 100, 100, 100), "res\\images\\Items\\Coins.png"));
		level2[0].addItem(new Item("COINS_3", "A pile of coins", "A shiny pile of coins.", new Rectangle(825, 450, 100, 100), "res\\images\\Items\\Coins.png"));

		
		level2[1].setExit(level2[0], 0);
		level2[1].setExit(level2[2], 3);
		Entity rock = new Entity(new Rectangle(1050, 550, 95, 75), "res\\images\\Items\\Rock.png", true);
		rock.addInteraction("Rock", "There seems to be something hiding under the rock...", null, 
				new Item("MUSHROOM", "Rare mushroom", "A rare mushroom.", new Rectangle(0, 0, 50, 50), "res\\images\\Items\\Mushroom.png"), false, false, false);
		rock.addInteraction("Rock", "There is nothing here.", null, null, true, false, false);
		level2[1].addEntity(rock);
		level2[1].addEntity(new Entity(new Rectangle(750, 470, 95, 75), "res\\images\\Items\\Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(850, 390, 95, 75), "res\\images\\Items\\Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(930, 300, 95, 75), "res\\images\\Items\\Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(450, 500, 95, 75), "res\\images\\Items\\Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(890, 480, 95, 75), "res\\images\\Items\\Rock.png", true));
		
		level2[2].setExit(level2[3], 0);
		level2[2].setExit(level2[1], 1);
		level2[2].setExit(lastRoom, 2);
		level2[2].addEntity(new Entity(new Rectangle(470, 350, 95, 75), "res\\images\\Items\\GrassPatch.png", false));
		level2[2].addEntity(new Entity(new Rectangle(570, 390, 95, 75), "res\\images\\Items\\GrassPatch.png", false));
		
		level2[3].setExit(level2[0], 1);
		level2[3].setExit(level2[2], 2);
		NPC oldbeard = new NPC(new Rectangle(400, 400, 128, 128), new String[] {
			"res\\images\\Entities\\oldbeardLeft.png",
			"res\\images\\Entities\\oldbeardRight.png",
			"res\\images\\Entities\\oldbeardRight.png",
			"res\\images\\Entities\\oldbeardLeft.png" });
		oldbeard.addInteraction("Oldbeard", "Thank you for the mushrooms! Here is your key for your reward.", "MUSHROOM", 
				new Item("BRASS_KEY", "Brass key", "A big shiny brass key.", new Rectangle(0, 0, 30, 65), "res\\images\\Items\\Key.png"), false, false, true);
		oldbeard.addInteraction("Oldbeard", "I am in dept to you, son.", "BRASS_KEY", null, true, false, false);
		oldbeard.addInteraction("Oldbeard", "I see you are seeking for a way out... I can help you if you bring me some mushrooms, I am to old to get them myself.", null, null, false, false, false);
		oldbeard.addInteraction("Oldbeard", "There should be some mushrooms growing among the rocks nearby. Bring them to me.", null, null, true, false, false);
		level2[3].addNPC(oldbeard);
		
		lastRoom.setExit(level2[2], 0);
	}
}
