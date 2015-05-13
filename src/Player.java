import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;


public class Player extends Entity {
	private final String name;
	private float speed = 0.4f;
	private Room currentRoom;
	private Map<String, Item> items = new HashMap<>();
	private boolean inInventory = false;

	private int currentItemIndex = 0;

	
	private boolean[] directionsToMove = new boolean[4];
	
	/**
	 * Create a new player.
	 * @param name The name of the player. Also used to identify players.
	 * @param imageLocation The location of the image of this player.
	 * @param position The starting position and size of the Player. 
	 * @param velocity The velocity of this player.
	 * @param acceleration The acceleration of this player.
	 */
	public Player(String name, String[] imageLocation, Rectangle position, Point velocity, Point acceleration) {
		super(position, velocity, acceleration, imageLocation);
		this.name = name;
		this.setCurrentRoom(MapManager.getFirstRoom()); // Player is starting in the first room. 
		
		setCollide(true, true); //The player always has collision
	}
	
	/**
	 * Returns the players name.
	 * @return The players name. 
	 */
	public String getName() {return name;}
	
	/**
	 * Specify that the player should move in this direction on the next update.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 */
	public void move(int direction) {
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		directionsToMove[direction] = true;
		setDirection(direction);
	}
	
	/**
	 * {@inheritDoc Entity} 
	 */
	@Override
	public void update(int delta, Room currentRoom) {
		// Movement
		Point velocity = new Point(0, 0);
		
		if (directionsToMove[0] && !directionsToMove[2]) { velocity.setY(-speed);} //Up
		if (directionsToMove[2] && !directionsToMove[0]) { velocity.setY(speed);} //Down
		
		if (directionsToMove[3] && !directionsToMove[1]) { velocity.setX(-speed);} //Left
		if (directionsToMove[1] && !directionsToMove[3]) { velocity.setX(speed);} //Right
		
		setVelocity(velocity);
		
		
		// Check if going through door by check that the two corners of the player are in the door area
		if (directionsToMove[0] && !directionsToMove[2] && DungeonGame.DOORAREA[0].contains(getPosition().getX(), getPosition().getY()) && DungeonGame.DOORAREA[0].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY())) {changeRoom(0);} // Up
		if (directionsToMove[2] && !directionsToMove[0] && DungeonGame.DOORAREA[2].contains(getPosition().getX(), getPosition().getY() + getPosition().getHeight()) && DungeonGame.DOORAREA[2].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY() + getPosition().getHeight())) {changeRoom(2);} // Down
		if (directionsToMove[3] && !directionsToMove[1] && DungeonGame.DOORAREA[3].contains(getPosition().getX(), getPosition().getY()) && DungeonGame.DOORAREA[3].contains(getPosition().getX(), getPosition().getY() + getPosition().getHeight())) {changeRoom(3);} // Left
		if (directionsToMove[1] && !directionsToMove[3] && DungeonGame.DOORAREA[1].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY()) && DungeonGame.DOORAREA[1].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY() + getPosition().getHeight())) {changeRoom(1);} // Right
		
		super.update(delta, currentRoom);
		
		directionsToMove[0] = false;
		directionsToMove[1] = false;
		directionsToMove[2] = false;
		directionsToMove[3] = false;
	}

	/**
	 * Change the current room to the room in the specified direction, if there is such a room.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 * @return true if the room was changed. 
	 */
	private boolean changeRoom(int direction) throws IllegalArgumentException {
		if (currentRoom.getNeighbor(direction) != null) {
			currentRoom.getNeighbor(direction).loadImage(); 
			setCurrentRoom(currentRoom.getNeighbor(direction));
			
			switch (direction) {
			case 0: 
				setPosition(new Rectangle((DungeonGame.WIDTH - getPosition().getWidth())/2, DungeonGame.HEIGHT - DungeonGame.WALLWIDTH[2] - getPosition().getHeight(),getPosition().getWidth(), getPosition().getHeight()));
				break;
				
			case 1: 
				setPosition(new Rectangle(DungeonGame.WALLWIDTH[3], (DungeonGame.HEIGHT - getPosition().getHeight())/2,getPosition().getWidth(), getPosition().getHeight()));
				break;
				
			case 2: 
				setPosition(new Rectangle((DungeonGame.WIDTH - getPosition().getWidth())/2, DungeonGame.WALLWIDTH[0],getPosition().getWidth(), getPosition().getHeight()));
				break;
				
			case 3: 
				setPosition(new Rectangle(DungeonGame.WIDTH - DungeonGame.WALLWIDTH[1] - getPosition().getWidth(), (DungeonGame.HEIGHT - getPosition().getHeight())/2, getPosition().getWidth(), getPosition().getHeight()));
				break;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Navigate in the inventory.
	 * @param direction The direction to navigate. 0 = up, 1 = down.
	 */
	public void navigateInventory(int direction) throws IllegalArgumentException {
		if (direction > 1 || direction < 0) {throw new IllegalArgumentException("Direction must be 0 or 1");}
		
		if (!items.isEmpty()) {
			if (currentItemIndex == 0 && direction == 0) {return;} // If at top and pressing up, do nothing
			if (currentItemIndex == items.size() -1  && direction == 1) {return;} // If at bottom and pressing down, do nothing
			
			if (direction == 0) {currentItemIndex--;} // Up
			else if (direction == 1) {currentItemIndex++;} // Down
		}
	}
	
	/**
	 * Look at current item in inventory.
	 */
	public void lookAtItem() {
		if (items.isEmpty()) {return;} // Return if empty
		
		Item currentItem;
		Iterator<Item> it = items.values().iterator();
		for (int i = 0; i < currentItemIndex && it.hasNext(); i++) {
			it.next();
		}
		currentItem = it.next();
		
		System.out.println(currentItem.getDescription()); // REMOVE
	}
	
	/**
	 * Toggles inventory. 
	 */
	public void inventory() {
		if (!inInventory){ // Display inventory
			inInventory = true;
			currentItemIndex = 0;
		}
		else { // Closes inventory
			inInventory = false; 
		}
	}
	
	/**
	 * Handles drawing of inventory on screen.
	 * @param g The Graphics context used by the container.
	 */
	public void drawInventory(Graphics g) {
		// Inventory properties
		int inventoryWidth = 300; // Pixles from right corner that inventory should take up
		float inventoryItemHeight = 40; // Pixels each item takes up in the list
		int offset = 80; // Start drawing items i px from top 
		int padding = 10;
		Font awtFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
		TrueTypeFont font = new TrueTypeFont(awtFont, false);
		
		// Draw inventory background
		g.setColor(Color.lightGray); 
		g.fillRect(DungeonGame.WIDTH - (inventoryWidth), 0, (inventoryWidth + padding), DungeonGame.HEIGHT); 
		
		// Draw inventory title
		g.setColor(Color.white); // Text color
		font.drawString((DungeonGame.WIDTH - inventoryWidth) + (inventoryItemHeight + padding), 30, "Inventory"); // Draw the name
				
		// Draw current Item background
		if (!items.isEmpty()) {
			g.setColor(Color.gray); 
			g.fillRect(DungeonGame.WIDTH - (inventoryWidth), (currentItemIndex * (inventoryItemHeight + padding/2) + offset), (inventoryWidth + padding), inventoryItemHeight);
		}
		
		// Draw items in inventory
		for (Item item : items.values()) {
			item.draw(DungeonGame.WIDTH - inventoryWidth + padding, offset, inventoryItemHeight/item.getPosition().getHeight()); // Draw the item
			
			g.setColor(Color.white); // Text color
			font.drawString((DungeonGame.WIDTH - inventoryWidth) + (inventoryItemHeight + padding), (offset + inventoryItemHeight/2) - (font.getHeight()/2), item.getName()); // Draw the name
			offset += inventoryItemHeight + padding/2;
		}
	}
	
	/**
	 * Interact with the object in front of the player.
	 */
	public void act() {
		Rectangle area = null;
		int actAreaSize = 30;
		
		// Create the area where player can interact with an Entity wherein.
		switch (getDirection()) {
		case 0: 
			area = new Rectangle(getPosition().getX(), getPosition().getY() - actAreaSize, getPosition().getWidth(), actAreaSize*2);
			break;
			
		case 1:
			area = new Rectangle(getPosition().getX() + getPosition().getWidth() - actAreaSize, getPosition().getY(), actAreaSize*2, getPosition().getHeight());
			break;
			
		case 2: 
			area = new Rectangle(getPosition().getX(), getPosition().getY() + getPosition().getHeight() - actAreaSize, getPosition().getWidth(), actAreaSize*2);
			break;
			
		case 3: 
			area = new Rectangle(getPosition().getX() - actAreaSize, getPosition().getY(), actAreaSize*2, getPosition().getHeight());
			break;
		}
		
		Iterator<NPC> itNpcs = currentRoom.getNPCs().iterator();
		Iterator<Item> itItems = currentRoom.getItems().iterator();
		Iterator<Entity> itEntities = currentRoom.getEntities().iterator();
		
		while (itNpcs.hasNext()) { //Check against NPCs
			NPC npc = itNpcs.next();
			if (npc.getPosition().intersects(area)) {
				npc.interact();
			}
		}
		
		while (itItems.hasNext()) { //Check against items 
			Item item = itItems.next();
			
			if (item.getPosition().intersects(area)) {
				currentRoom.removeItem(item);
				items.put(item.getID(), item);
			}
		}
		
		while (itEntities.hasNext()) { //Check against entities
			Entity entity = itEntities.next();
			if (entity.getPosition().intersects(area)) {
				entity.interact();
			}
		}
		
	}
		
	/**
	 * Return the room the Player currently is in.
	 * @return The Room this Player is currently in.
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Set the current room the Player is in.
	 * @param currentRoom The new Room Player should be in. 
	 */
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	/**
	 * Return a boolean describing if player is in inventory or not
	 * @return the inInventory. True if player is in Inventory, false otherwises.
	 */
	public boolean isInInventory() {
		return inInventory;
	}

	/** 
	 * Set the boolean describing if player is in inventory or not
	 * @param inInventory Set to true if player is in invetory, false otherwise.
	 */
	public void setInInventory(boolean inInventory) {
		this.inInventory = inInventory;
	}
}
