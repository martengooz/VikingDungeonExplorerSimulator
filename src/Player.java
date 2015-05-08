import org.newdawn.slick.geom.Point;


public class Player extends Entity {
	private final String name;
	
	/**
	 * Create a new Player.
	 * @param name The name of the player. Also used to identify players.
	 * @param position The starting position of the Player. 
	 * @param description A description of this item.
	 * @param image A image of this item.
	 */
	public Player(String name, String imageLocation, Point position, Point velocity, Point acceleration) {
		super(position, velocity, acceleration, imageLocation);
		this.name = name;
	}
	
	/**
	 * Returns the players name.
	 * @return The players name. 
	 */
	public String getName() {return name;}
	
	/**
	 * Handles player movement. 
	 */
	public void move() {
		
	}
}
