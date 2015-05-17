package se.gabmartdev.dungeonexplorer;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Item extends Entity {
	
	private final String ID;
	private final String NAME;
	private final String DESCRIPTION;
	
	/**
	 * Create a new item.
	 * @param id A unique identifier for this Item.
	 * @param name The name that should be displayed to the user.
	 * @param description A description of this Item.
	 * @param velocity The velocity of this Item.
	 * @param acceleration The acceleration of this Item.
	 * @param imageLocation The location of the image of this Item.
	 */
	public Item(String id, String name, String description, Rectangle position,
			Point velocity, Point acceleration, String imageLocation) {
		super(position, velocity, acceleration, imageLocation);
		
		this.ID = id;
		this.NAME = name;
		this.DESCRIPTION = description;
	}
	
	/**
	 * Create a new item with no velocity or acceleration.
	 * @param id A unique identifier for this Item.
	 * @param name The name that should be displayed to the user.
	 * @param description A description of this Item.
	 * @param imageLocation The location of the image of this Item.
	 */
	public Item(String id, String name, String description, Rectangle position, String imageLocation) {
		super(position, new Point(0, 0), new Point(0, 0), imageLocation);
		
		this.ID = id;
		this.NAME = name;
		this.DESCRIPTION = description;
	}
	
	/**
	 * Get a unique identifier of this item.
	 * @return The id of this item.
	 */
	public String getID() {return ID;}
	
	/**
	 * Return the name of this item.
	 * @return The name of this item.
	 */
	public String getName() {return NAME;}
	
	/**
	 * Returns a description of this item.
	 * @return The description of this item.
	 */
	public String getDescription() {return DESCRIPTION;}
	
	/**
	 * Return the name of this item.
	 * @return The name of this item.
	 */
	public String toString() {return getName();}
}
