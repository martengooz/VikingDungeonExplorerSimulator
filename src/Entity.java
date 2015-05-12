
import java.util.Iterator;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Entity implements Drawable {
	
	private Rectangle position;
	private Point velocity;
	private Point acceleration;
	private Image[] image =  new Image[4];
	private String[] imageLocation = new String[4];
	private int direction = 2;
	
	private boolean checkCollision;
	private boolean doesCollide;
	
	/**
	 * Create a new Entity.
	 * @param position The position of this entity.
	 * @param velocity The velocity of this entity.
	 * @param acceleration The acceleration of this entity.
	 * @param imageLocation An array of 4 strings with the location of the images of this entity looking in different directions (up, right, down, left).
	 */
	public Entity(Rectangle position, Point velocity, Point acceleration, String[] imageLocation) {
		if (imageLocation.length != 4) {throw new IllegalArgumentException("imageLocation must have 4 strings");}
		
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		
		this.imageLocation[0] = imageLocation[0];
		this.imageLocation[1] = imageLocation[1];
		this.imageLocation[2] = imageLocation[2];
		this.imageLocation[3] = imageLocation[3];
	}
	
	/**
	 * Create a new Entity.
	 * @param position The position of this entity.
	 * @param velocity The velocity of this entity.
	 * @param acceleration The acceleration of this entity.
	 * @param imageLocation The location of the image of this entity.
	 */
	public Entity(Rectangle position, Point velocity, Point acceleration, String imageLocation) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		
		this.imageLocation[0] = imageLocation;
		this.imageLocation[1] = imageLocation;
		this.imageLocation[2] = imageLocation;
		this.imageLocation[3] = imageLocation;
	}
	
	/**
	 * Create a new Entity.
	 * @param position The position of this entity.
	 * @param imageLocation
	 * @param doesCollide
	 */
	public Entity(Rectangle position, String imageLocation, boolean doesCollide) {
		this.position = position;
		this.doesCollide = doesCollide;
		
		this.imageLocation[0] = imageLocation;
		this.imageLocation[1] = imageLocation;
		this.imageLocation[2] = imageLocation;
		this.imageLocation[3] = imageLocation;
		
		this.velocity = new Point(0, 0);
		this.acceleration = new Point(0, 0);
	}
	
	/**
	 * Draw this entity.
	 */
	@Override
	public void draw() {
		getImage(direction).draw(getPosition().getX(), getPosition().getY());
	}
	
	/**
	 * Draw this entity at specified position.
	 * @param position The position to draw the entity
	 */
	public void draw(Rectangle position) {
		this.position = position;
		getImage(direction).draw(getPosition().getX(), getPosition().getY(), getPosition().getWidth(), getPosition().getHeight());
	}
	
	/**
	 * Update the position of this entity by moving it according to set velocity and acceleration.
	 * @param delta The number of milliseconds since last update.
	 * @param currentRoom The currentRoom of the game, used to detect collision with other entities in this room.
	 */
	public void update(int delta, Room currentRoom) {	
		//Update velocity
		velocity.setX(velocity.getX() + acceleration.getX() * delta);
		velocity.setY(velocity.getY() + acceleration.getY() * delta);
		
		//Update position X
		Rectangle newPosition = new Rectangle(position.getX() + velocity.getX() * delta, position.getY(), position.getWidth(), position.getHeight()); //Calculate new new position to compare collisions
		if (newPosition.getX() > 0 + DungeonGame.WALLWIDTH[1] && newPosition.getX() < DungeonGame.WIDTH - position.getWidth() - DungeonGame.WALLWIDTH[3]) { //Check that we are within bounds
			boolean willCollide = false;			
			if (checkCollision) {
				Iterator<NPC> itNpcs = currentRoom.getNPCs().iterator();
				Iterator<Item> itItems = currentRoom.getItems().iterator();
				Iterator<Entity> itEntities = currentRoom.getEntities().iterator();
				
				while (itNpcs.hasNext()) { //Check against NPCs
					NPC npc = itNpcs.next();
					if (npc.getDoesCollide() && npc.getPosition().intersects(newPosition)) {willCollide = true;}
				}
				
				while (itItems.hasNext()) { //Check against items 
					Item item = itItems.next();
					if (item.getDoesCollide() && item.getPosition().intersects(newPosition)) {willCollide = true;}
				}
				
				while (itEntities.hasNext()) { //Check against entities
					Entity entity = itEntities.next();
					if (entity.getDoesCollide() && entity.getPosition().intersects(newPosition)) {willCollide = true;}
				}
			}
			
			if (!willCollide) {position = newPosition;}
		}
		
		//Update position Y
		newPosition = new Rectangle(position.getX(), position.getY() + velocity.getY() * delta, position.getWidth(), position.getHeight()); //Calculate new new position to compare collisions
		if (newPosition.getY() > 0 + DungeonGame.WALLWIDTH[0] && newPosition.getY() < DungeonGame.HEIGHT - position.getHeight() - DungeonGame.WALLWIDTH[2]) { //Check that we are within bounds
			boolean willCollide = false;
			if (checkCollision) {
				Iterator<NPC> itNpcs = currentRoom.getNPCs().iterator();
				Iterator<Item> itItems = currentRoom.getItems().iterator();
				Iterator<Entity> itEntities = currentRoom.getEntities().iterator();
				
				while (itNpcs.hasNext()) { //Check against NPCs
					NPC npc = itNpcs.next();
					if (npc.getDoesCollide() && npc.getPosition().intersects(newPosition)) {willCollide = true;}
				}
				
				while (itItems.hasNext()) { //Check against items 
					Item item = itItems.next();
					if (item.getDoesCollide() && item.getPosition().intersects(newPosition)) {willCollide = true;}
				}
				
				while (itEntities.hasNext()) { //Check against entities
					Entity entity = itEntities.next();
					if (entity.getDoesCollide() && entity.getPosition().intersects(newPosition)) {willCollide = true;}
				}
			}
		
			if (!willCollide) {position = newPosition;}
		}		
	}

	/**
	 * Interact with this Entity.
	 * @return The item returned in the interaction.
	 */
	public Item interact() {
		return null;
	}
	
	/**
	 * Returns the position of this entity.
	 * @return The position of this entity.
	 */
	public Rectangle getPosition() {return position;}

	/**
	 * Returns the velocity of this entity.
	 * @return The velocity of this entity.
	 */
	public Point getVelocity() {return velocity;
	}
	
	/**
	 * Returns the acceleration of this entity.
	 * @return The acceleration of this entity.
	 */
	public Point getAcceleration() {return acceleration;}
	
	/**
	 * Return a boolean signaling if other entities should collide with this entity.
	 * @return A boolean signaling if entities does collide with this entity.
	 */
	public boolean getDoesCollide() {return doesCollide;}
	
	/**
	 * Return an integer specifying the direction this entity is facing.
	 * @return An integer in the range 0-3 specifying which direction (up, right, down, left).
	 */
	public int getDirection() {return direction;}
	
	/**
	 * Set the position of this entity.
	 * @param position The new position.
	 */
	public void setPosition(Rectangle position) {this.position = position;}
	
	/**
	 * Set the velocity of this entity.
	 * @param velocity The new velocity.
	 */
	public void setVelocity(Point velocity) {this.velocity = velocity;}

	/**
	 * Set the acceleration of this entity.
	 * @param acceleration The new acceleration.
	 */
	public void setAcceleration(Point acceleration) {this.acceleration = acceleration;}
	
	/**
	 * Set the direction of this entity.
	 * @param direction An integer in the range 0-3 specifying which direction (up, right, down, left).
	 */
	public void setDirection(int direction) {
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		this.direction = direction;
	}

	/**
	 * Set the collision properties of this entity.
	 * @param doesCollide A boolean signaling if other entities should check against collision with this one.
	 * @param checkCollision A boolean signaling if this entity should check collision against other entities.
	 */
	public void setCollide(boolean doesCollide, boolean checkCollision) {
		this.doesCollide = doesCollide;
		this.checkCollision = checkCollision;
	}
	
	/**
	 * Return the image of this entity.
	 * @return The image.
	 */
	public Image getImage(int direction) {
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		return image[direction];
	}

	/**
	 * {@inheritDoc Drawable}
	 */
	public void loadImage() {
		try {
			//Check if all directions have the same texture and save some memory by using the same image.
			if (imageLocation[0].equals(imageLocation[1]) && imageLocation[0].equals(imageLocation[2]) && imageLocation[0].equals(imageLocation[3])) {
				this.image[0] = new Image(imageLocation[0]);
				this.image[1] = this.image[0];
				this.image[2] = this.image[0];
				this.image[3] = this.image[0];
			}
			else {
				this.image[0] = new Image(imageLocation[0]);
				this.image[1] = new Image(imageLocation[1]);
				this.image[2] = new Image(imageLocation[2]);
				this.image[3] = new Image(imageLocation[3]);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
