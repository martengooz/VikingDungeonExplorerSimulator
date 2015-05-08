import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.opengl.Texture;


public class Player extends Entity {
	private final String name;
	private final Image image;
	
	/**
	 * Create a new Player.
	 * @param name The name of the player. Also used to identify players.
	 * @param position The starting position of the Player. 
	 * @param description A description of this item.
	 * @param image A image of this item.
	 */
	public Player(String name, Image image, Point position, Point velocity, Point acceleration) {
		super(position, velocity, acceleration);
		
		this.name = name;
		this.image = image;
	}
	
	/**
	 * Returns the players name.
	 * @return The players name. 
	 */
	public String getName() {return name;}
	
	/**
	 * Returns the players image.
	 * @return The image of the player.
	 */
	public Image getImage() {return image;}
	
	/**
	 * Handles player movement. 
	 */
	public void move() {
		
	}
}
