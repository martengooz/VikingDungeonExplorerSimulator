import org.newdawn.slick.geom.Point;
import org.newdawn.slick.opengl.Texture;


public class Player extends Entity {
	private final String name;
	private final Texture texture;
	
	/**
	 * Create a new Player.
	 * @param name The name of the player. Also used to identify players.
	 * @param position The starting position of the Player. 
	 * @param description A description of this item.
	 * @param texture A texture of this item.
	 */
	public Player(String name, Texture texture, Point position, Point velocity, Point acceleration) {
		super(position, velocity, acceleration);
		
		this.name = name;
		this.texture = texture;
	}
	
	/**
	 * Returns the players name.
	 * @return The players name. 
	 */
	public String getName() {return name;}
	
	/**
	 * Returns the players texture.
	 * @return The texture of the player.
	 */
	public Texture getTexture() {return texture;}

	/**
	 * Handles player movement. 
	 */
	public void move() {
		
	}
}
