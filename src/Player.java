import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;


public class Player extends Entity {
	private final String name;
	private float speed = 0.2f;
	private Room currentRoom;
	
	private boolean[] directionsToMove = new boolean[4];
	/**
	 * Create a new Player.
	 * @param name The name of the player. Also used to identify players.
	 * @param position The starting position and size of the Player. 
	 * @param description A description of this item.
	 * @param image A image of this item.
	 */
	public Player(String name, String imageLocation, Rectangle position, Point velocity, Point acceleration) {
		super(position, velocity, acceleration, imageLocation);
		this.name = name;
		this.setCurrentRoom(MapManager.getFirstRoom()); // Player is starting in the first room. 
		
		setCollide(true, true); //The player always has collision
	}
	
	/**
	 * Returns the players name.
	 * @return The players name. 
	 */
	public String getName() {return name;}
	
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
	public void update(int delta, Room currentRoom) {
		Point velocity = new Point(0, 0);
		
		if (directionsToMove[0] && !directionsToMove[2]) { velocity.setY(-speed);} //Up
		if (directionsToMove[2] && !directionsToMove[0]) { velocity.setY(speed);} //Down
		
		if (directionsToMove[3] && !directionsToMove[1]) { velocity.setX(speed);} //Left
		if (directionsToMove[1] && !directionsToMove[3]) { velocity.setX(-speed);} //Right
		
		directionsToMove[0] = false;
		directionsToMove[1] = false;
		directionsToMove[2] = false;
		directionsToMove[3] = false;
		
		setVelocity(velocity);
		super.update(delta, currentRoom);
	}

	/**
	 * @return the Room Player is currently in.
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * @param currentRoom the new Room Player should be in. 
	 */
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
}
