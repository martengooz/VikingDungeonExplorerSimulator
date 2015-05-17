package se.gabmartdev.dungeonexplorer;

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
		firstRoom = new Room("res/images/Rooms/Room2_keys.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		lastRoom = new Room("res/images/Rooms/Outside.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		
		Room[] level1 = new Room[3];
		level1[0] = new Room("res/images/Rooms/Room.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level1[1] = new Room("res/images/Rooms/Room.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level1[2] = (Room) new Room("res/images/Rooms/Room.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		
		Room[] level2 = new Room[4];
		level2[0] = new Room("res/images/Rooms/Room3.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level2[1] = new Room("res/images/Rooms/Room3.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level2[2] = new Room("res/images/Rooms/Room3.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level2[3] = new Room("res/images/Rooms/Room3.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		
		Room[] level3 = new Room[5];
		level3[0] = new Room("res/images/Rooms/Room2.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level3[1] = new Room("res/images/Rooms/Room2.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level3[2] = new Room("res/images/Rooms/Room2.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level3[3] = new Room("res/images/Rooms/Room2.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level3[4] = new Room("res/images/Rooms/Room2.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		
		Room[] level4 = new Room[4];
		level4[0] = new Room("res/images/Rooms/Room4.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level4[1] = new Room("res/images/Rooms/Room4.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level4[2] = new Room("res/images/Rooms/Room4.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		level4[3] = new Room("res/images/Rooms/Room4.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		
		//firstRoom= level4[0];
		
		lastRoom = new Room("res/images/Rooms/Outside.png", "res/images/Doors/Door.png", "res/images/Doors/LockedDoor.png");
		
		// Configure rooms
		// Level 1 
		firstRoom.setExit(level1[0], 2);
		firstRoom.addEntity(new Entity(new Rectangle(350, 500, 100, 100), "res/images/Items/GrassPatch.png", false));
		firstRoom.addEntity(new Entity(new Rectangle(750, 70, 95, 75), "res/images/Items/Rock.png", true));
		firstRoom.addItem(new Item("IRON_SHOVEL", "Shovel", "A shovel to dig with.", new Rectangle(800, 400, 100, 100), "res/images/Items/Shovel.png"));
		
		level1[0].setExit(firstRoom, 0);
		level1[0].setExit(level1[1], 1);
		level1[0].setExit(level2[0], 3, "GREYBEARD_KEY");
		Entity pileOfDirt = new Entity(new Rectangle(1100, 310, 100, 100), "res/images/Items/PileOfDirt.png", true);
		pileOfDirt.addInteraction("Pile of dirt", "You removed the dirt with your mighty shovel.", "IRON_SHOVEL", null, false, true, false);
		pileOfDirt.addInteraction("Pile of dirt", "There is a big pile of dirt in the way. If only I had a shovel..", null, null, true, false, false);
		level1[0].addEntity(pileOfDirt);
		Entity runestone = new Entity(new Rectangle(100, 150, 102, 133), "res/images/Items/Runestone.png", true);
		runestone.addInteraction("Runestone", "\"Greybeards treasure chamber\"", null, null, true, false, false);
		level1[0].addEntity(runestone);
		
		level1[1].setExit(level1[0], 3);
		level1[1].setExit(level1[2], 2);
		level1[1].addEntity(new Entity(new Rectangle(650, 470, 95, 75), "res/images/Items/GrassPatch.png", false));
		NPC greybeard = new NPC(new Rectangle(300, 300, 128, 128), new String[] {
			"res/images/Entities/greybeardLeft.png",
			"res/images/Entities/greybeardRight.png",
			"res/images/Entities/greybeardRight.png",
			"res/images/Entities/greybeardLeft.png" });
		greybeard.addInteraction("Greybeard", "You found my golden key! Here have this key instead, it will aid you in your travels!", "GOLD_KEY", 
				new Item("GREYBEARD_KEY", "Greybeard key", "A shiny key with the name \"Greybeard\" engraved.", new Rectangle(0, 0, 30, 65), "res/images/Items/Key.png"), false, false, true);
		greybeard.addInteraction("Greybeard", "Thank you, mighty hero Redbeard!", "GREYBEARD_KEY", null, true, false, false);
		greybeard.addInteraction("Greybeard", "I am the mighty greybeard! Give me a gift and i will reward you greatly!", null, null, false, false, false);
		greybeard.addInteraction("Greybeard", "I seem to have missplaced my golden key! How can I pillage without it?", null, null, true, false, false);
		level1[1].addNPC(greybeard);
		
		level1[2].setExit(level1[1], 0);
		level1[2].addItem(new Item("GOLD_KEY", "Golden Key", "A shiny golden key.", new Rectangle(625, 450, 30, 65), "res/images/Items/Key.png"));
		
		
		// Level 2
		level2[0].setExit(level2[1], 2);
		level2[0].setExit(level2[3], 3);
		level2[0].setExit(level1[0], 1);
		level2[0].addItem(new Item("COINS", "Pile of coins", "A shiny pile of coins.", new Rectangle(125, 450, 100, 100), "res/images/Items/Coins.png"));
		level2[0].addItem(new Item("COINS", "Pile of coins", "A shiny pile of coins.", new Rectangle(525, 100, 100, 100), "res/images/Items/Coins.png"));
		level2[0].addItem(new Item("COINS", "Pile of coins", "A shiny pile of coins.", new Rectangle(825, 450, 100, 100), "res/images/Items/Coins.png"));
		
		level2[1].setExit(level2[0], 0);
		level2[1].setExit(level2[2], 3);
		Entity rock = new Entity(new Rectangle(1000, 550, 95, 75), "res/images/Items/Rock.png", true);
		rock.addInteraction("Rock", "There seems to be something hiding under the rock...", null, 
				new Item("MUSHROOM", "Rare mushroom", "A rare mushroom.", new Rectangle(0, 0, 50, 50), "res/images/Items/Mushroom.png"), false, false, false);
		level2[1].addEntity(rock);
		level2[1].addEntity(new Entity(new Rectangle(800, 560, 95, 75), "res/images/Items/Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(850, 390, 95, 75), "res/images/Items/Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(930, 300, 95, 75), "res/images/Items/Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(450, 560, 95, 75), "res/images/Items/Rock.png", true));
		level2[1].addEntity(new Entity(new Rectangle(890, 480, 95, 75), "res/images/Items/Rock.png", true));
		
		level2[2].setExit(level2[3], 0);
		level2[2].setExit(level2[1], 1);
		level2[2].setExit(level3[0], 2, "BRASS_KEY");
		level2[2].addEntity(new Entity(new Rectangle(470, 350, 95, 75), "res/images/Items/GrassPatch.png", false));
		level2[2].addEntity(new Entity(new Rectangle(570, 390, 95, 75), "res/images/Items/GrassPatch.png", false));
		
		level2[3].setExit(level2[0], 1);
		level2[3].setExit(level2[2], 2);
		NPC oldbeard = new NPC(new Rectangle(400, 400, 128, 128), new String[] {
			"res/images/Entities/oldbeardLeft.png",
			"res/images/Entities/oldbeardRight.png",
			"res/images/Entities/oldbeardRight.png",
			"res/images/Entities/oldbeardLeft.png" });
		oldbeard.addInteraction("Oldbeard - Guardian of the treasure", "Thank you for the mushrooms! Here is your key for your reward.", "MUSHROOM", 
				new Item("BRASS_KEY", "Brass key", "A big shiny brass key.", new Rectangle(0, 0, 30, 65), "res/images/Items/Key.png"), false, false, true);
		oldbeard.addInteraction("Oldbeard - Guardian of the treasure", "I am in dept to you, son.", "BRASS_KEY", null, true, false, false);
		oldbeard.addInteraction("Oldbeard - Guardian of the treasure", "I see you are seeking for a way out... I can help you if you bring me some mushrooms, \nI am too old to get them myself.", null, null, false, false, false);
		oldbeard.addInteraction("Oldbeard - Guardian of the treasure", "There should be some mushrooms growing among the rocks nearby. Bring them to me.", null, null, true, false, false);
		level2[3].addNPC(oldbeard);
		
		
		// Level 3
		level3[0].setExit(level2[2], 0);
		level3[0].setExit(level3[2], 1);
		level3[0].setExit(level3[1], 2);
		Item sawdust1 = new Item("SAWDUST_SPECIAL", "Special Sawdust", "A pile of special sawdust.", new Rectangle(0, 0, 45, 45), "res/images/Items/Sawdust.png");
		level3[0].addEntity(new Entity(new Rectangle(180, 490, 95, 75), "res/images/Items/Grasspatch.png", false));
		level3[0].addEntity(new Entity(new Rectangle(100, 280, 95, 75), "res/images/Items/Rock.png", true));
		Entity pileOfLogs = new Entity(new Rectangle(800, 520, 300, 100), "res/images/Items/PileOfLogs.png", true);
		pileOfLogs.addInteraction("You smashed the logs into pieces", "Your axe swiftly turns the logs into dust.", "IRON_AXE", 
				sawdust1, false, true, false);
		level3[0].addEntity(pileOfLogs);
		
		level3[1].setExit(level3[0], 0);
		Item sawdust2 = new Item("SAWDUST", "Pile of sawdust", "A pile of sawdust.", new Rectangle(0, 0, 45, 45), "res/images/Items/Sawdust.png");
		Item sawdust3 = new Item("SAWDUST", "Pile of sawdust", "A pile of sawdust.", new Rectangle(0, 0, 45, 45), "res/images/Items/Sawdust.png");

		level3[1].addEntity(new Entity(new Rectangle(100, 80, 95, 75), "res/images/Items/Rock.png", true));
		level3[1].addEntity(new Entity(new Rectangle(360, 500, 80, 90), "res/images/Items/Stump.png", true));
		Entity pileOfLogs1 = new Entity(new Rectangle(890, 420, 300, 100), "res/images/Items/PileOfLogs.png", true);
		pileOfLogs1.addInteraction("You smashed the logs into pieces", "Your axe swiftly turns the logs into dust.", "IRON_AXE", 
				sawdust2, false, true, false);
		Entity pileOfLogs2 = new Entity(new Rectangle(860, 520, 300, 100), "res/images/Items/PileOfLogs.png", true);
		pileOfLogs2.addInteraction("You smashed the logs into pieces", "Your axe swiftly turns the logs into dust.", "IRON_AXE", 
				sawdust3, false, true, false);
		level3[1].addEntity(pileOfLogs1);
		level3[1].addEntity(pileOfLogs2);
		level3[1].addItem(new Item("IRON_AXE", "Axe", "An iron axe used to make firewood.", new Rectangle(325, 400, 30, 65), "res/images/Items/Axe.png"));
		
		level3[2].setExit(level3[3], 0);
		level3[2].setExit(level3[4], 1);
		level3[2].setExit(level3[0], 3);
		Item sawdust4 = new Item("SAWDUST", "Pile of sawdust", "Pile of sawdust.", new Rectangle(0, 0, 45, 45), "res/images/Items/Sawdust.png");
		Entity log = new Entity(new Rectangle(540, 100, 280, 50), "res/images/Items/Log.png", true);
		log.addInteraction("You smashed the log into pieces", "Your axe swiftly turns the log into dust.", "IRON_AXE", 
				sawdust4, false, true, false);
		level3[2].addEntity(log);
		
		level3[3].setExit(level3[2], 2);
		level3[3].addItem(new Item("STONE_KEY", "Stone Key", "A key made of stone.", new Rectangle(625, 150, 30, 65), "res/images/Items/Key.png"));
		level3[3].addEntity(new Entity(new Rectangle(500, 130, 95, 75), "res/images/Items/Rock.png", true));
		level3[3].addEntity(new Entity(new Rectangle(690, 130, 95, 75), "res/images/Items/Rock.png", true));
		
		level3[4].setExit(level3[2], 3);
		level3[4].setExit(level4[0], 1, "STONE_KEY");
		
		
		// Level 4
		level4[0].setExit(level3[4], 3);
		level4[0].setExit(level4[1], 2);
		NPC sawbeard = new NPC(new Rectangle(400, 400, 128, 128), new String[] {
			"res/images/Entities/sawbeardLeft.png",
			"res/images/Entities/sawbeardRight.png",
			"res/images/Entities/sawbeardRight.png",
			"res/images/Entities/sawbeardLeft.png" });
		sawbeard.addInteraction("Sawbeard", "I lovey sawdust! Gimmey and I givey shiney key. But only special sawdusty!", null, null, false, false, false);
		sawbeard.addInteraction("Sawbeard", "Yay, sawydusty! Dusty dusty dust...", "SAWDUST_SPECIAL", 
				new Item("WOODEN_KEY", "Wooden Key", "A key made of wood.", new Rectangle(625, 150, 30, 65), "res/images/Items/KeyWood.png"), false, false, true);
		sawbeard.addInteraction("Sawbeard", "Me lovey some special sawdusty!", null, null, true, false, false);
		level4[0].addNPC(sawbeard);
		Entity pileOfLogs3 = new Entity(new Rectangle(790, 100, 300, 100), "res/images/Items/PileOfLogs.png", true);
		pileOfLogs3.addInteraction("You smashed the logs into pieces", "Your axe swiftly turns the logs into dust.", "IRON_AXE", 
				sawdust2, false, true, false);
		level4[0].addEntity(pileOfLogs3);
		level4[0].addEntity(new Entity(new Rectangle(1100, 100, 50, 50), "res/images/Items/sawdust.png", false));
		level4[0].addEntity(new Entity(new Rectangle(1120, 120, 50, 50), "res/images/Items/sawdust.png", false));
		level4[0].addEntity(new Entity(new Rectangle(240, 520, 50, 50), "res/images/Items/sawdust.png", false));
		Entity runestoneSawbeard = new Entity(new Rectangle(100, 150, 102, 133), "res/images/Items/Runestone.png", true);
		runestoneSawbeard.addInteraction("Runestone", "\"Sawbeards' awesomey carpentery\"", null, null, true, false, false);
		
		level4[1].setExit(level4[0], 0);
		level4[1].setExit(level4[2], 1, "WOODEN_KEY");
		level4[1].setExit(level4[3], 3);
		level4[1].addEntity(new Entity(new Rectangle(230, 180, 95, 75), "res/images/Items/Rock.png", true));
		NPC sawbeardKid1 = new NPC(new Rectangle(700, 500, 73, 80), new String[] {
			"res/images/Entities/kidbeardLeft.png",
			"res/images/Entities/kidbeardRight.png",
			"res/images/Entities/kidbeardRight.png",
			"res/images/Entities/kidbeardLeft.png" });
		sawbeardKid1.addInteraction("Bobby", "When I growy up I wanny be dusty.", null, null, true, false, false);
		level4[1].addNPC(sawbeardKid1);
		NPC sawbeardKid2 = new NPC(new Rectangle(400, 400, 73, 80), new String[] {
			"res/images/Entities/kidbeardLeft.png",
			"res/images/Entities/kidbeardRight.png",
			"res/images/Entities/kidbeardRight.png",
			"res/images/Entities/kidbeardLeft.png" });
		sawbeardKid2.addInteraction("Hobby", "Hehehe, dust...", null, null, true, false, false);
		level4[1].addNPC(sawbeardKid2);
		NPC sawbeardKid3 = new NPC(new Rectangle(940, 120, 73, 80), new String[] {
			"res/images/Entities/kidbeardLeft.png",
			"res/images/Entities/kidbeardRight.png",
			"res/images/Entities/kidbeardRight.png",
			"res/images/Entities/kidbeardLeft.png" });
		sawbeardKid3.addInteraction("Robby", "Dusty is lovey, dusty is lifey.", null, null, true, false, false);
		level4[1].addNPC(sawbeardKid3);

		level4[2].setExit(level4[1], 3);
		level4[2].addItem(new Item("EXIT_KEY", "Exit Key", "The key to exit the dungeons.", new Rectangle(990, 345, 30, 65), "res/images/Items/Key.png"));
		
		level4[3].setExit(level4[1], 1);
		level4[3].setExit(lastRoom, 2, "EXIT_KEY");
		level4[3].addEntity(new Entity(new Rectangle(90, 289, 80, 90), "res/images/Items/Stump.png", true));
		level4[3].addEntity(new Entity(new Rectangle(730, 142, 80, 90), "res/images/Items/Stump.png", true));
		level4[3].addEntity(new Entity(new Rectangle(820, 180, 80, 90), "res/images/Items/Stump.png", true));
		level4[3].addEntity(new Entity(new Rectangle(90, 546, 80, 90), "res/images/Items/Stump.png", true));
		level4[3].addEntity(new Entity(new Rectangle(450, 546, 80, 90), "res/images/Items/Stump.png", true));
		level4[3].addEntity(new Entity(new Rectangle(1090, 200, 80, 90), "res/images/Items/Stump.png", true));
		level4[3].addEntity(new Entity(new Rectangle(1100, 100, 50, 50), "res/images/Items/sawdust.png", false));
		level4[3].addEntity(new Entity(new Rectangle(1120, 120, 50, 50), "res/images/Items/sawdust.png", false));
		level4[3].addEntity(new Entity(new Rectangle(240, 420, 50, 50), "res/images/Items/sawdust.png", false));
		level4[3].addEntity(new Entity(new Rectangle(256, 415, 50, 50), "res/images/Items/sawdust.png", false));

		
		lastRoom.setExit(level4[3], 0);
	}
}
