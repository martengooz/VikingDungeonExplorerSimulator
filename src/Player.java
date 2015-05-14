import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;


public class Player extends Entity {
	private final String name;
	private float speed = 0.4f;
	private Room currentRoom;
	private Map<String, Item> items = new HashMap<>();
	
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
	public void move(int direction) throws IllegalArgumentException {
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
		if (directionsToMove[0] && !directionsToMove[2] && // Up
				DungeonGame.DOORAREA[0].contains(getPosition().getX(), getPosition().getY()) && 
				DungeonGame.DOORAREA[0].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY())) {
			changeRoom(0);
		} 
		if (directionsToMove[2] && !directionsToMove[0] && // Down
				DungeonGame.DOORAREA[2].contains(getPosition().getX(), getPosition().getY() + getPosition().getHeight()) && 
				DungeonGame.DOORAREA[2].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY() + getPosition().getHeight())) {
			changeRoom(2);
		} 
		if (directionsToMove[3] && !directionsToMove[1] && // Left
				DungeonGame.DOORAREA[3].contains(getPosition().getX(), getPosition().getY()) && 
				DungeonGame.DOORAREA[3].contains(getPosition().getX(), getPosition().getY() + getPosition().getHeight())) {
			changeRoom(3);
		} 
		if (directionsToMove[1] && !directionsToMove[3] && // Right
				DungeonGame.DOORAREA[1].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY()) && 
				DungeonGame.DOORAREA[1].contains(getPosition().getX() + getPosition().getWidth(), getPosition().getY() + getPosition().getHeight())) {
			changeRoom(1);
		} 
		
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
				setPosition(new Rectangle((DungeonGame.WIDTH - getPosition().getWidth())/2, 
						DungeonGame.HEIGHT - DungeonGame.WALLWIDTH[2] - getPosition().getHeight(), 
						getPosition().getWidth(), getPosition().getHeight()));
				break;
				
			case 1: 
				setPosition(new Rectangle(DungeonGame.WALLWIDTH[3], 
						(DungeonGame.HEIGHT - getPosition().getHeight())/2,
						getPosition().getWidth(), getPosition().getHeight()));
				break;
				
			case 2: 
				setPosition(new Rectangle((DungeonGame.WIDTH - getPosition().getWidth())/2,
						DungeonGame.WALLWIDTH[0],
						getPosition().getWidth(), getPosition().getHeight()));
				break;
				
			case 3: 
				setPosition(new Rectangle(DungeonGame.WIDTH - DungeonGame.WALLWIDTH[1] - getPosition().getWidth(),
						(DungeonGame.HEIGHT - getPosition().getHeight())/2,
						getPosition().getWidth(), getPosition().getHeight()));
				break;
			}
			
			UserInterfaceManager.removeAllMessages();
			return true;
		}
		return false;
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
			area = new Rectangle(getPosition().getX(), getPosition().getY() - actAreaSize,
					getPosition().getWidth(), actAreaSize*2);
			break;
			
		case 1:
			area = new Rectangle(getPosition().getX() + getPosition().getWidth() - actAreaSize, getPosition().getY(),
					actAreaSize*2, getPosition().getHeight());
			break;
			
		case 2: 
			area = new Rectangle(getPosition().getX(), getPosition().getY() + getPosition().getHeight() - actAreaSize,
					getPosition().getWidth(), actAreaSize*2);
			break;
			
		case 3: 
			area = new Rectangle(getPosition().getX() - actAreaSize, getPosition().getY(),
					actAreaSize*2, getPosition().getHeight());
			break;
		}
		
		Iterator<NPC> itNpcs = currentRoom.getNPCs().iterator();
		Iterator<Item> itItems = currentRoom.getItems().iterator();
		Iterator<Entity> itEntities = currentRoom.getEntities().iterator();
		
		while (itNpcs.hasNext()) { //Check against NPCs
			NPC npc = itNpcs.next();
			if (npc.getPosition().intersects(area)) {
				Item reward = npc.interact(this);
				if (reward != null) {pickUpItem(reward);}
			}
		}
		
		while (itItems.hasNext()) { //Check against items 
			Item item = itItems.next();
			
			if (item.getPosition().intersects(area)) {
				currentRoom.markForRemoval(item);
				pickUpItem(item);
			}
		}
		
		while (itEntities.hasNext()) { //Check against entities
			Entity entity = itEntities.next();
			if (entity.getPosition().intersects(area)) {
				Item reward = entity.interact(this);
				if (reward != null) {pickUpItem(reward);}
			}
		}
		
	}
	
	/**
	 * Add Item to the inventory.
	 * @param item The Item to add.
	 */
	public void pickUpItem(Item item) {
		item.loadImage(); // Load image
		items.put(item.getID(), item); // Put item in inventory
		UserInterfaceManager.addMessage(item.getImage(2),"You found one " + item.getName().toLowerCase() + ".", item.getDescription());
	}
	
	/**
	 * Remove an item from the inventory.
	 * @param item The id of the item to remove.
	 */
	public void removeItem(String itemId) {
		Item item = items.remove(itemId); // Put item in inventory
		UserInterfaceManager.addMessage(item.getImage(2),"Removed one " + item.getName().toLowerCase() + " from inventory.", item.getDescription());
	}
	
	/**
	 * Returns a boolean signaling if this Player has an Item with the specified id.
	 * @param id The id of the Item to check for.
	 * @return True if the player has the specified Item.
	 */
	public boolean hasItem(String id) {
		return items.containsKey(id);
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
	 * Return the players items. 
	 * @return The players items
	 */
	public Map<String, Item> getItems() {
		return items;
	}
	
	/**
	 * Return a boolean signifying of player is moving.
	 * @return True if moving, false otherwidse.
	 */
	public boolean isMoving() {
		return (getVelocity().getX() != 0 || getVelocity().getY() != 0);
	}

}
