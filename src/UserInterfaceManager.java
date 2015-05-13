import org.newdawn.slick.Graphics;


public class UserInterfaceManager {
	private final Graphics g;
	
	/**
	 * Create a new UserInterfaceManager for handling drawing UI.
	 * @param g The Graphics context used by the container.
	 */
	public UserInterfaceManager(Graphics g) {
		this.g = g;
	}
	
	/**
	 * Show a message on screen. 
	 * @param title The title of the message.
	 * @param message The message to print. 
	 */
	public void showMessage(String title, String message) {	
		System.out.println("Title: " + title + ", Message: " + message);
	}
}
