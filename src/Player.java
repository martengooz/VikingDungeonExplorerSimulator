import org.newdawn.slick.geom.Point;
import org.newdawn.slick.opengl.Texture;


public class Player extends Entity {
	private final String name;
	private final Texture texture;
	private Point position;
	private Point velocity;
	
	
	/**
	 * Create a new Player.
	 * @param name The name of the player. Also used to identify players.
	 * @param position The starting position of the Player. 
	 * @param description A description of this item.
	 * @param texture A texture of this item.
	 */
	public Player(String name,Texture texture, Point position) {
		this.name = name;
		this.texture = texture;
		this.setPosition(position);
		this.setVelocity(new Point(0, 0));
	}
	/**
	 * Returns the players position on screen.
	 * @return The players position. 
	 */
	public Point getPosition() {return position;}
	
	/**
	 * Returns the players name.
	 * @return The players name. 
	 */
	public String getName() {return name;}

	/**
	 * Sets the players position on screen. 
	 * @param position - The new position of the Player. 
	 */
	public void setPosition(Point position) {this.position = position;};
	
	/**
	 * Returns the players texture.
	 * @return The texture of the player.
	 */
	public Texture getTexture() {return texture;}
	
	/**
	 * Returns the players velocity.
	 * @return the players velocity.
	 */
	public Point getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity The new velocity of the player.
	 */
	public void setVelocity(Point velocity) {
		this.velocity = velocity;
	}

	/**
	 * Handles player movement. 
	 */
	public void move() {
		
	}
}
