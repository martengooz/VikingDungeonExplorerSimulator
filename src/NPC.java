import java.util.Random;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;


public class NPC extends Entity {

	private Random random =  new Random();
	private static final int NEWDIRECTIONDICESIZE = 1200; // The size of the "dice" that determines which direction the NPC will move.
	private static final float SPEED = 0.1f; //The speed NPCs move at
	
	/**
	 * Create a new NPC
	 * @param position The position of this NPC.
	 * @param velocity The velocity of this NPC.
	 * @param acceleration The acceleration of this NPC.
	 * @param imageLocation The location of the images of this NPC.
	 * @param doesCollide A boolean signaling if other entities should check against collision with this one.
	 * @param checkCollision A boolean signaling if this entity should check collision against other entities.
	 */
	public NPC(Rectangle position, Point velocity, Point acceleration, String[] imageLocation, boolean doesCollide, boolean checkCollision) {
		super(position, velocity, acceleration, imageLocation);
		setCollide(doesCollide, checkCollision);
	}
	
	/**
	 * Create a new NPC with no velocity or acceleration that collide with other entities.
	 * @param position The position of this NPC.
	 * @param imageLocation The location of the images of this NPC.
	 */
	public NPC(Rectangle position, String[] imageLocation) {
		super(position, new Point(0, 0), new Point(0, 0), imageLocation);
		setCollide(false, true);
	}

	
	/**
	 * {@inheritDoc Entity} 
	 */
	@Override
	public void update(int delta, Room currentRoom) {
		int randomNumber = random.nextInt(NEWDIRECTIONDICESIZE);
		
		switch (randomNumber) { // Determine which direction to move.
		case 0:
			setVelocity(new Point(0, SPEED));
			setDirection(2);
			break;
			
		case 1:
			setVelocity(new Point(0, -SPEED));
			setDirection(0);
			break;
			
		case 2:
			setVelocity(new Point(SPEED, 0));
			setDirection(1);
			break;
			
		case 3:
			setVelocity(new Point(SPEED, SPEED));
			setDirection(1);
			break;
			
		case 4:
			setVelocity(new Point(SPEED, -SPEED));
			setDirection(1);
			break;
		
		case 5:
			setVelocity(new Point(-SPEED, 0));
			setDirection(3);
			break;
			
		case 6:
			setVelocity(new Point(-SPEED, SPEED));
			setDirection(3);
			break;
			
		case 7:
			setVelocity(new Point(-SPEED, -SPEED));
			setDirection(3);
			break;
		
		case 8: case 9: case 10: case 11: case 12: // Stop and stand still
			setVelocity(new Point(0, 0));
			setDirection(0);
			break;
			
		case 13: case 14: case 15: case 16: case 17: // Move towards the center of the map. This is to make sure that the NPC not just stare into walls all the time.
			float speedX = 0;
			float speedY = 0;
			
			if((getPosition().getX() - (DungeonGame.WIDTH / 2)) < - DungeonGame.WIDTH / 4) {
				speedX = SPEED;
				setDirection(1);
			} 
			else if((getPosition().getX() - (DungeonGame.WIDTH / 2)) > DungeonGame.WIDTH / 4) {
				speedX = -SPEED;
				setDirection(3);
			}
			
			if((getPosition().getY() - (DungeonGame.HEIGHT / 2)) < - DungeonGame.HEIGHT / 4) {
				speedY = SPEED;
			} 
			else if((getPosition().getY() - (DungeonGame.HEIGHT / 2)) > DungeonGame.HEIGHT / 4) {
				speedY = -SPEED;
			}
			
			setVelocity(new Point(speedX, speedY));
			break;
		}
		
		super.update(delta, currentRoom);
	}
}
