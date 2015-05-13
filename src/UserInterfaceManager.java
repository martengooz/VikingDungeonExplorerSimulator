import org.newdawn.slick.Graphics;


public class UserInterfaceManager {	
	
	/**
	 * Show a message on screen. 
	 * @param g The graphics context used by the container.
	 * @param title The title of the message.
	 * @param message The message to print. 
	 */
	public static void showMessage(Graphics g, String title, String message) {	
		System.out.println("Title: " + title + ", Message: " + message);
	}
}
