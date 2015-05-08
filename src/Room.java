import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Room implements Drawable {
	
	private Set<NPC> npcs = new HashSet<>();
	private Set<Item> items = new HashSet<>();
	private Set<Entity> entities = new HashSet<>(); 
	private String imageLocation;
	private Image image;
	
	Room[] neighbors = new Room[4];
	
	/**
	 * Create a new room
	 */
	public Room(String imageLocation) {this.imageLocation = imageLocation;};
	
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
	public Room getNeighbor(int direction) {
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		return neighbors[direction];
	}
	
	/**
	 * Add an item to the room.
	 * @param item The item to add.
	 */
	public void setItem(Item item) {items.add(item);}
	
	/**
	 * Add an exit to this room.
	 * @param room The neighboring room.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 */
	public void setExit(Room room, int direction) {
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		neighbors[direction] = room;
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
	}
	
	
	/**
	 * {@inheritDoc Drawable}
	 */
	public void loadTexture() {
		try {
			this.image = new Image(imageLocation); // Load room image
			
			Iterator itNpcs = npcs.iterator();
			Iterator itItems = items.iterator();
			Iterator itEntities = entities.iterator();
			
			// Does not work yet
			/*while (itNpcs.hasNext()){itNpcs.next().loadImage();} // Load npcs images
			while (itItems.hasNext()){itItems.next().loadImage();} // Load npcs images
			while (itEntities.hasNext()){itEntities.next().loadImage();} // Load npcs images
			*/
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
