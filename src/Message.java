import org.newdawn.slick.Image;

/**
 * Creates a message object to be used in UserInterfaceManager.
 */
public class Message {
	private final Image image;
	private final String title;
	private final String description;
	
	/**
	 * Create a new message.
	 * @param image The image of this messages' author.
	 * @param title	The title of this message.
	 * @param description The content in this message. 
	 */
	public Message(Image image, String title, String description) {
		this.image = image;
		this.title = title;
		this.description = description;
	}
	
	/**
	 * Return the image of this messages' author.
	 * @return the title
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Return the title in this message.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the content in this message.
	 * @return the content.
	 */
	public String getDescription() {
		return description;
	}
}