
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Entity implements Drawable {
	
	private Point position;
	private Point velocity;
	private Point acceleration;
	private String imageLocation;
	private Image image;
	
	public Entity(Point position, Point velocity, Point acceleration, String imageLocation) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.imageLocation = imageLocation;
	}
	
	@Override
	public void draw() {
		
	}
	
	public void update() {
		//Update position
		position.setX(velocity.getX() + velocity.getX());
		position.setY(velocity.getY() + velocity.getY());
		
		//Update velocity
		velocity.setX(velocity.getX() + acceleration.getX());
		velocity.setY(velocity.getY() + acceleration.getY());
	}

	/**
	 * Returns the position of this entity.
	 * @return The position of this entity.
	 */
	public Point getPosition() {return position;}

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
	 * Set the position of this entity.
	 * @param position The new position.
	 */
	public void setPosition(Point position) {this.position = position;}
	
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
	 * @return the image
	 */
	public Image getImage() {return image;	}

	/**
	 * {@inheritDoc Drawable}
	 */
	public void loadTexture() {
		try {
			this.image = new Image(imageLocation);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
