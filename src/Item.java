import org.newdawn.slick.geom.Point;

public class Item extends Entity {
	
	private final String id;
	private final String name;
	private final String description;
	
	/**
	 * Create a new item.
	 * @param id A unique identifier for this item.
	 * @param name The name that should be displayed to the user.
	 * @param description A description of this item.
	 * @param texture A texture of this item.
	 */
	public Item(String id, String name, String description, Point position, Point velocity, Point acceleration, String imageLocation) {
		super(position, velocity, acceleration, imageLocation);
		
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	/**
	 *  Get a unique identifier of this item.
	 * @return The id of this item.
	 */
	public String getID() {return id;}
	
	/**
	 * Return the name of this item.
	 * @return The name of this item.
	 */
	public String getName() {return name;}
	
	/**
	 * Returns a description of this item.
	 * @return The description of this item.
	 */
	public String getDescription() {return description;}
	
	/**
	 * Return the name of this item.
	 * @return The name of this item.
	 */
	public String toString() {return getName();}
}
