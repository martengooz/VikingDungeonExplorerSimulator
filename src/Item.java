import org.newdawn.slick.opengl.Texture;


public class Item extends Entity {
	
	final String id;
	final String name;
	final String description;
	final Texture texture;
	
	/**
	 * Create a new item.
	 * @param id A unique identifier for this item.
	 * @param name The name that should be displayed to the user.
	 * @param description A description of this item.
	 * @param texture A texture of this item.
	 */
	public Item(String id, String name, String description, Texture texture) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.texture = texture;
	};
	
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
	 * Return the texture of this item.
	 * @return The texture of this item.
	 */
	public Texture getTexture() {return texture;}
	
	/**
	 * Return the name of this item.
	 * @return The name of this item.
	 */
	public String toString() {return getName();}
}
