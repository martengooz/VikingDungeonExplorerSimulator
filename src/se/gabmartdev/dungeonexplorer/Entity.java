package se.gabmartdev.dungeonexplorer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	private List<Interaction> interactions = new ArrayList<Interaction>();
	
	private boolean checkCollision;
	private boolean doesCollide;
	
	/**
	 * Create a new Entity.
	 * @param position The position of this entity.
	 * @param velocity The velocity of this entity.
	 * @param acceleration The acceleration of this entity.
	 * @param imageLocation An array of 4 strings with the location of the images of this entity looking in different directions (up, right, down, left).
	 */
	public Entity(Rectangle position, Point velocity, Point acceleration, String[] imageLocation) throws IllegalArgumentException{
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
	 * Let a Player interact with this Entity.
	 * @param player The player that interacts with this Entity.
	 * @return The item returned in the interaction.
	 */
	public Item interact(Player player) {
		Interaction interaction = null;
		Iterator<Interaction> it = interactions.iterator();
		
		while (it.hasNext() && interaction == null) { // Loop until we find an applicable interaction.
			Interaction interactionTemp = it.next();
			if (interactionTemp.getRequiredItemId() == null || player.hasItem(interactionTemp.getRequiredItemId())) {
				interaction = interactionTemp;
			}
		}
		if (interaction == null) {return null;} // No interaction was found.
		
		UserInterfaceManager.addMessage(image[2], interaction.getTitle(), interaction.getMessage());
		
		if (!interaction.getPersistient()) {
			interactions.remove(interaction);
		}
		
		if (interaction.getRemoveEntity()) {
			player.getCurrentRoom().markForRemoval(this);
		}
		
		if (interaction.getRemoveRequiredItem()) {
			player.removeItem(interaction.getRequiredItemId());
		}
		return interaction.getReward();
	}
	
	/**
	 * A private class used to represent interactions.
	 */
	private class Interaction {
		private String title;
		private String message;
		private String requiredItemId;
		private Item reward;
		private boolean persistent;
		private boolean removeEntity;
		private boolean removeRequireditem;
		
		public Interaction(String title, String message, String requiredItemId, Item reward, 
				boolean persistent, boolean removeEntity, boolean removeRequireditem) {
			this.title = title;
			this.message = message;
			this.requiredItemId = requiredItemId;
			this.reward = reward;
			this.persistent = persistent;
			this.removeEntity = removeEntity;
			this.removeRequireditem = removeRequireditem;
		}

		/**
		 * Returns the title of this interaction.
		 * @return The title of this interaction.
		 */
		public String getTitle() {return title;}

		/**
		 * Returns the message of this interaction.
		 * @return The message of this interaction.
		 */
		public String getMessage() {return message;}

		/**
		 * Returns the id of the required item of this interaction.
		 * @return The id of the required item of this interaction.
		 */
		public String getRequiredItemId() {return requiredItemId;}

		/**
		 * Returns the reward of this interaction.
		 * @return An item as an reward of this interaction.
		 */
		public Item getReward() {return reward;}
		
		/**
		 * Returns a boolean signaling if this interaction should stay after being completed.
		 * @return True if this interaction should stay after being completed.
		 */
		public boolean getPersistient() {return persistent;}
		
		/**
		 * Returns a boolean signaling if this interaction should remove the entity after completion.
		 * @return True if this interaction should remove this Entity after completion.
		 */
		public boolean getRemoveEntity() {return removeEntity;}
		
		/**
		 * Returns a boolean signaling if this interaction should remove the required item after completion.
		 * @return True if this interaction should remove the required item after completion.
		 */
		public boolean getRemoveRequiredItem() {return removeRequireditem;}
	}
	
	/**
	 * Add an interaction to this Entity.
	 * @param title The title of the interaction.
	 * @param message The message to display.
	 * @param requiredItemId The id of the Item required to display this interaction, or null if no item is required.
	 * @param reward The reward for this interaction.
	 * @param persistent A boolean signaling if this interaction should stay after being completed.
	 * @param removeEntity A boolean signaling if boolean signaling of this interaction should remove the entity after completion.
	 * @param removeRequireItem A boolean signaling of this interaction should remove the required Item after being completed.
	 */
	public void addInteraction(String title, String message, String requiredItemId, 
			Item reward, boolean persistent, boolean removeEntity, boolean removeRequireItem) {
		interactions.add(new Interaction(title, message, requiredItemId, reward, persistent, removeEntity, removeRequireItem));
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
		getImage(direction).draw(position.getX(), position.getY(), position.getWidth(), position.getHeight());
	}
	
	/**
	 * Draw this entity at specified position and scale.
	 * @param x The X coordinate to draw the entity.
	 * @param y The Y coordinate to draw the entity.
	 * @param scale The scale the of the image.
	 */
	public void draw(float x, float y, float scale) {
		getImage(direction).draw(x, y, scale);
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
		Rectangle newPosition = new Rectangle(position.getX() + velocity.getX() * delta, position.getY(), //Calculate new new position to compare collisions
				position.getWidth(), position.getHeight()); 
		
		boolean willCollide = false;
		if (checkCollision) {
			if (newPosition.getX() < 0 + DungeonGame.WALLWIDTH[1] || newPosition.getX() > DungeonGame.WIDTH - position.getWidth() - DungeonGame.WALLWIDTH[3]) { // Check that we are within bounds
				willCollide = true;
			}
			Iterator<NPC> itNpcs = currentRoom.getNPCs().iterator();
			Iterator<Item> itItems = currentRoom.getItems().iterator();
			Iterator<Entity> itEntities = currentRoom.getEntities().iterator();
			
			while (itNpcs.hasNext()) { //Check against NPCs
				NPC npc = itNpcs.next();
				if (npc.getDoesCollide() && npc.getPosition().intersects(newPosition)) {
					willCollide = true;
				}
			}
			
			while (itItems.hasNext()) { //Check against items 
				Item item = itItems.next();
				if (item.getDoesCollide() && item.getPosition().intersects(newPosition)) {
					willCollide = true;
				}
			}
			
			while (itEntities.hasNext()) { //Check against entities
				Entity entity = itEntities.next();
				if (entity.getDoesCollide() && entity.getPosition().intersects(newPosition)) {
					willCollide = true;
				}
			}
		}
		if (!willCollide) {position = newPosition;}
		
		//Update position Y
		newPosition = new Rectangle(position.getX(), position.getY() + velocity.getY() * delta, //Calculate new new position to compare collisions
				position.getWidth(), position.getHeight()); 
		
		willCollide = false;
		if (checkCollision) {
			if (newPosition.getY() < 0 + DungeonGame.WALLWIDTH[0] || newPosition.getY() > DungeonGame.HEIGHT - position.getHeight() - DungeonGame.WALLWIDTH[2]) { //Check that we are within bounds
				willCollide = true;
			}
			if (checkCollision) {
				Iterator<NPC> itNpcs = currentRoom.getNPCs().iterator();
				Iterator<Item> itItems = currentRoom.getItems().iterator();
				Iterator<Entity> itEntities = currentRoom.getEntities().iterator();
				
				while (itNpcs.hasNext()) { //Check against NPCs
					NPC npc = itNpcs.next();
					if (npc.getDoesCollide() && npc.getPosition().intersects(newPosition)) {
						willCollide = true;
					}
				}
				
				while (itItems.hasNext()) { //Check against items 
					Item item = itItems.next();
					if (item.getDoesCollide() && item.getPosition().intersects(newPosition)) {
						willCollide = true;
					}
				}
				
				while (itEntities.hasNext()) { //Check against entities
					Entity entity = itEntities.next();
					if (entity.getDoesCollide() && entity.getPosition().intersects(newPosition)) {
						willCollide = true;
					}
				}
			}
		}	
		if (!willCollide) {position = newPosition;}
	}
	
	/**
	 * {@inheritDoc Drawable}
	 */
	public void loadImage() {
		try {
			//Check if all directions have the same texture and save some memory by using the same image.
			if (imageLocation[0].equals(imageLocation[1]) && 
					imageLocation[0].equals(imageLocation[2]) && 
					imageLocation[0].equals(imageLocation[3])) {
				
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
	public void setDirection(int direction) throws IllegalArgumentException {
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
	public Image getImage(int direction) throws IllegalArgumentException {
		if (direction > 3 || direction < 0) {throw new IllegalArgumentException("Direction must be between 0 and 3");}
		return image[direction];
	}
}
