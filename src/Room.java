import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Room implements Drawable {
	
	private Set<NPC> npcs = new HashSet<>();
	private Set<Item> items = new HashSet<>();
	private Set<Entity> entities = new HashSet<>();
	
	private Set<Entity> markedForRemoval = new HashSet<>();
	
	private String imageLocation;
	private String doorImageLocation;
	private String lockedDoorImageLocation;
	private Image image;
	private Image doorImage;
	private Image lockedDoorImage;
	
	Room[] neighbors = new Room[4];
	String[] keys = new String[4];
	
	/**
	 * Create a new room
	 */
	public Room(String imageLocation, String doorImageLocation, String lockedDoorImageLocation) {
		this.imageLocation = imageLocation;
		this.doorImageLocation = doorImageLocation;
		this.lockedDoorImageLocation = lockedDoorImageLocation;
	}
	
	public void update(int delta) {
		removeMarked();
		
		Iterator<NPC> itNpcs = getNPCs().iterator();
		Iterator<Item> itItems = getItems().iterator();
		Iterator<Entity> itEntities = getEntities().iterator();
		
		while (itNpcs.hasNext()) { // Update NPCs
			itNpcs.next().update(delta, this);
		}
		
		while (itItems.hasNext()) { // Update items 
			itItems.next().update(delta, this);
		}
		
		while (itEntities.hasNext()) { // Update entities
			itEntities.next().update(delta, this);
		}
	}
	
	/**
	 * Get a set of the NPCs in this room.
	 * @return A set of the NPCs in the room.
	 */
	public Set<NPC> getNPCs() {return npcs;}
	
	/**
	 * Get a set of the items in this room.
	 * @return A set of the items in the room.
	 */
	public Set<Item> getItems() {return items;}
	
	/**
	 * Get a set of the entities in this room.
	 * @return A set of the entities in the room.
	 */
	public Set<Entity> getEntities() {return entities;}
	
	/**
	 * Get the neighbor in the specified direction.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 * @return
	 */
	public Room getNeighbor(int direction) throws IllegalArgumentException{
		if (direction > 3 || direction < 0) {
			throw new IllegalArgumentException("Direction must be between 0 and 3");
		}
		return neighbors[direction];
	}
	
	/**
	 * Add an item to the room.
	 * @param item The item to add.
	 */
	public void addItem(Item item) {items.add(item);}
	
	/**
	 * Add a NPC to the room.
	 * @param npc The npc to add.
	 */
	public void addNPC(NPC npc) {npcs.add(npc);}
	
	/**
	 * Add an entity to the room.
	 * @param entity The entity to add.
	 */
	public void addEntity(Entity entity) {entities.add(entity);}
	
	/**
	 * Add an exit to this room.
	 * @param room The neighboring room.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 */
	public void setExit(Room room, int direction) throws IllegalArgumentException {
		if (direction > 3 || direction < 0) {
			throw new IllegalArgumentException("Direction must be between 0 and 3");
		}
		neighbors[direction] = room;
	}
	
	/**
	 * Add an locked exit to this room.
	 * @param room The neighboring room.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 * @param key The key that unlock this door.
	 */
	public void setExit(Room room, int direction, String key) throws IllegalArgumentException {
		if (direction > 3 || direction < 0) {
			throw new IllegalArgumentException("Direction must be between 0 and 3");
		}
		neighbors[direction] = room;
		keys[direction] = key;
	}
	
	/**
	 * Check if the door in specified direction is locked.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 * @return True if the door is unlocked.
	 */
	public boolean isDoorLocked(int direction) throws IllegalArgumentException {
		if (direction > 3 || direction < 0) {
			throw new IllegalArgumentException("Direction must be between 0 and 3");
		}
		return keys[direction] != null;
	}
	
	/**
	 * Let a Player try to unlock this door.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 * @param player The player that interacts with this Entity.
	 * @return The item that unlocked the door, or null if the door is still locked.
	 */
	public Item unlockDoor(int direction, Player player) throws IllegalArgumentException {
		if (direction > 3 || direction < 0) {
			throw new IllegalArgumentException("Direction must be between 0 and 3");
		}
		if (player.hasItem(keys[direction])) {
			String key = keys[direction]; 
			keys[direction] = null;
			return player.getItems().get(key);
		}
		return null;
	}
	
	/**
	 * Mark this Entity, Item or NPC for removal from this room next update.
	 * @param entity The Entity, Item or NPC to remove.
	 */
	public void markForRemoval(Entity entity) {
		markedForRemoval.add(entity);
	}
	
	/**
	 * Remove all marked Entities, Items and NPCs
	 */
	public void removeMarked() {
		for(Entity e: markedForRemoval) {
			npcs.remove(e);
			items.remove(e);
			entities.remove(e);
		}
	}
	
	/**
	 * Remove an item room the current room.
	 * @param item The item to remove.
	 * @return true if this room contained the specified item.
	 */
	public boolean removeItem(Item item) {
		return items.remove(item);
	}
	
	/**
	 * {@inheritDoc Drawable}
	 */
	public void draw() {
		image.draw(0, 0);
		doorImage.setCenterOfRotation(doorImage.getWidth()/2, doorImage.getHeight()/2);
		
		//Draw the doors
		if (neighbors[0] != null && !isDoorLocked(0)) {
			doorImage.setRotation(0); 
			doorImage.draw((DungeonGame.WIDTH - doorImage.getWidth())/2,0);
		}
		if (neighbors[2] != null && !isDoorLocked(2)) {
			doorImage.setRotation(180);
			doorImage.draw((DungeonGame.WIDTH - doorImage.getWidth())/2, DungeonGame.HEIGHT - doorImage.getHeight());
		}		
		if (neighbors[1] != null && !isDoorLocked(1)) {
			doorImage.setRotation(90); 
			doorImage.draw(DungeonGame.WIDTH - 2*doorImage.getHeight() + 8, (DungeonGame.HEIGHT - doorImage.getHeight())/2);
		}
		if (neighbors[3] != null && !isDoorLocked(3)) {
			doorImage.setRotation(270); 
			doorImage.draw(8-doorImage.getHeight(), (DungeonGame.HEIGHT - doorImage.getHeight())/2);
		}
		
		//Draw the locked doors
		if (neighbors[0] != null && isDoorLocked(0)) {
			lockedDoorImage.setRotation(0); 
			lockedDoorImage.draw((DungeonGame.WIDTH - lockedDoorImage.getWidth())/2,0);
		}
		if (neighbors[2] != null && isDoorLocked(2)) {
			lockedDoorImage.setRotation(180);
			lockedDoorImage.draw((DungeonGame.WIDTH - lockedDoorImage.getWidth())/2, DungeonGame.HEIGHT - lockedDoorImage.getHeight());
		}		
		if (neighbors[1] != null && isDoorLocked(1)) {
			lockedDoorImage.setRotation(90); 
			lockedDoorImage.draw(DungeonGame.WIDTH - 2*lockedDoorImage.getHeight() + 8, (DungeonGame.HEIGHT - lockedDoorImage.getHeight())/2);
		}
		if (neighbors[3] != null && isDoorLocked(3)) {
			lockedDoorImage.setRotation(270); 
			lockedDoorImage.draw(8-lockedDoorImage.getHeight(), (DungeonGame.HEIGHT - lockedDoorImage.getHeight())/2);
		}
		
		Iterator<Item> itItems = items.iterator();
		Iterator<Entity> itEntities = entities.iterator();
		Iterator<NPC> itNpcs = npcs.iterator();
		
		while (itItems.hasNext()){itItems.next().draw();} // Draw item images
		while (itEntities.hasNext()){itEntities.next().draw();} // Draw entity images
		while (itNpcs.hasNext()){itNpcs.next().draw();} // Draw npcs images
	}
	
	
	/**
	 * {@inheritDoc Drawable}
	 */
	public void loadImage() {
		try {
			this.image = new Image(imageLocation); // Load room image
			this.doorImage = new Image(doorImageLocation); // Load door image
			this.lockedDoorImage = new Image(lockedDoorImageLocation); // Load locked door image
			
			Iterator<NPC> itNpcs = npcs.iterator();
			Iterator<Item> itItems = items.iterator();
			Iterator<Entity> itEntities = entities.iterator();
			
			while (itNpcs.hasNext()){itNpcs.next().loadImage();} // Load npcs images
			while (itItems.hasNext()){itItems.next().loadImage();} // Load item images
			while (itEntities.hasNext()){itEntities.next().loadImage();} // Load entity images
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
