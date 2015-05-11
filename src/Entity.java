
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Entity implements Drawable {
	
	private Rectangle position;
	private Point velocity;
	private Point acceleration;
	private String imageLocation;
	private Image image;
	private int direction;
	
	private boolean checkCollision;
	private boolean doesCollide;
	
	public Entity(Rectangle position, Point velocity, Point acceleration, String imageLocation) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.imageLocation = imageLocation;
	}
	
	/**
	 * Draw this entity.
	 */
	@Override
	public void draw() {
		getImage().draw(getPosition().getX(), getPosition().getY());
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
	 * Set the direction of this entity
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
	 * @return the image
	 */
	public Image getImage() {return image;	}

	/**
	 * {@inheritDoc Drawable}
	 */
	public void loadImage() {
		try {
			this.image = new Image(imageLocation);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
