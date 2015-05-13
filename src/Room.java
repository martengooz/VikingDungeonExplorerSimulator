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
	private Image image;
	private Image doorImage;
	
	Room[] neighbors = new Room[4];
	
	/**
	 * Create a new room
	 */
	public Room(String imageLocation, String doorImageLocation) {
		this.imageLocation = imageLocation;
		this.doorImageLocation = doorImageLocation;
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
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
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
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		neighbors[direction] = room;
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
		if (neighbors[0] != null) {doorImage.setRotation(0); doorImage.draw((DungeonGame.WIDTH - doorImage.getWidth())/2,0);}
		if (neighbors[2] != null) {doorImage.setRotation(180); doorImage.draw((DungeonGame.WIDTH - doorImage.getWidth())/2, DungeonGame.HEIGHT - doorImage.getHeight());}		
		if (neighbors[1] != null) {doorImage.setRotation(90); doorImage.draw(DungeonGame.WIDTH - 2*doorImage.getHeight() + 8, (DungeonGame.HEIGHT - doorImage.getHeight())/2);}
		if (neighbors[3] != null) {doorImage.setRotation(270); doorImage.draw(8-doorImage.getHeight(), (DungeonGame.HEIGHT - doorImage.getHeight())/2);}
		
		Iterator<NPC> itNpcs = npcs.iterator();
		Iterator<Item> itItems = items.iterator();
		Iterator<Entity> itEntities = entities.iterator();
		
		while (itNpcs.hasNext()){itNpcs.next().draw();} // Draw npcs images
		while (itItems.hasNext()){itItems.next().draw();} // Draw item images
		while (itEntities.hasNext()){itEntities.next().draw();} // Draw entity images
	}
	
	
	/**
	 * {@inheritDoc Drawable}
	 */
	public void loadImage() {
		try {
			this.image = new Image(imageLocation); // Load room image
			this.doorImage = new Image(doorImageLocation); // Load door image
			
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
