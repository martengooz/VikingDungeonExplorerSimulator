package se.gabmartdev.dungeonexplorer;

import org.newdawn.slick.Image;

/**
 * Creates a message object to be used in UserInterfaceManager.
 */
public class Message {
	private final Image IMAGE;
	private final String TITLE;
	private final String DESCRIPTION;
	
	/**
	 * Create a new message.
	 * @param image The image of this messages' author.
	 * @param title	The title of this message.
	 * @param description The content in this message. 
	 */
	public Message(Image image, String title, String description) {
		this.IMAGE = image;
		this.TITLE = title;
		this.DESCRIPTION = description;
	}
	
	/**
	 * Return the image of this messages' author.
	 * @return the title
	 */
	public Image getImage() {
		return IMAGE;
	}

	/**
	 * Return the title in this message.
	 * @return the title
	 */
	public String getTitle() {
		return TITLE;
	}

	/**
	 * Returns the content in this message.
	 * @return the content.
	 */
	public String getDescription() {
		return DESCRIPTION;
	}
}