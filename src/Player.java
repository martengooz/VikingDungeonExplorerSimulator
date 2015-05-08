import org.newdawn.slick.geom.Point;


public class Player extends Entity {
	private final String name;
	private float speed = 0.2f;
	
	private boolean[] directionsToMove = new boolean[4];
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
	 * Returns the players image.
	 * @return The image of the player.
	 */
	
	/**
	 * Specify that the player should move in this direction on the next update.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 */
	public void move(int direction) {
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		directionsToMove[direction] = true;
	}
	
	/**
	 * {@inheritDoc Entity} 
	 */
	@Override
	public void update() {
		Point velocity = new Point(0,0);
		
		if (directionsToMove[0] && !directionsToMove[2]) { velocity.setY(speed);} //Up
		if (directionsToMove[2] && !directionsToMove[0]) { velocity.setY(-speed);} //Down
		
		if (directionsToMove[3] && !directionsToMove[1]) { velocity.setX(-speed);} //Left
		if (directionsToMove[1] && !directionsToMove[3]) { velocity.setX(speed);} //Right
		
		setVelocity(velocity);
		super.update();
	}
}
