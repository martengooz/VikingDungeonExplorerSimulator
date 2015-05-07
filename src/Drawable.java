import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public interface Drawable {
	
	/**
	 * Handles drawing objects to screen. 
	 * @param container - The container holing this game.
	 * @param g - The graphics context that can be used to render. However, normal rendering routines can also be used.
	 */
	public void draw(GameContainer gameContainer, Graphics g);
}
