import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;


public class UserInterfaceManager {	
	
	private static boolean inInventort;
	
	private static Item currentItem; 
	private static int currentItemIndex = 0;
	
	private static Map<String, Item> items;
	private static Queue<Message> messages = new LinkedList<Message>();
	
	// Inventory properties
	private static final int INVENTORY_WIDTH = 300; // Pixels from right corner that inventory should take up
	private static final float INVENTORY_ITEM_HEIGHT = 40; // Pixels each item takes up in the list
	private static final int OFFSET = 80; // Start drawing items i px from top 
	private static final int ITEM_PADDING = 10;
	
	// Message properties
	private static final int MESSAGEBOK_PADDING = 20;
	private static final int MESSAGEBOX_WIDTH = DungeonGame.WIDTH;
	private static final int MESSAGEBOX_HEIGHT = 200; 

	// Color
	private static final Color TEXT_COLOR = Color.white;
	private static final Color BG_COLOR = Color.lightGray;
	private static final Color CURRENT_ITEM_BG_COLOR = Color.gray;
	
	// Fonts
	private static final Font AWT_TITLE_FONT = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 24);
	private static final TrueTypeFont TITLE_FONT = new TrueTypeFont(AWT_TITLE_FONT, false);
	private static final Font AWR_DESCRIPTION_FONT = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 14);
	private static final TrueTypeFont DESCRIPTION_FONT = new TrueTypeFont(AWR_DESCRIPTION_FONT, false);
	
	/**
	 * Add a message to the message queue. 
	 * @param g The graphics context used by the container.
	 * @param title The title of the message.
	 * @param message The message to print. 
	 */
	public static void addMessage(Image image, String title, String message) throws IllegalArgumentException {
		if (image == null || title == null || message == null) { 
			throw new IllegalArgumentException("Must provide an image, title and description."); 
		}
		messages.add(new Message(image, title, message));
	}
	
	/**
	 * Displays next message.
	 */
	public static void nextMessage() {
		if (hasMessage()) {messages.remove();}
	}
	
	/**
	 * Removes all messages.
	 */
	public static void removeAllMessages() {
		if (hasMessage()) {messages.clear();}
	}
	
	/**
	 * Returns boolean signifying if theres a message to show.
	 * @return True if there is at least one message to show, false otherwise. 
	 */
	public static boolean hasMessage() {
		return !messages.isEmpty();
	}
	
	/**
	 * Add a message to the message queue. 
	 * @param g The graphics context used by the container.
	 * @param title The title of the message.
	 * @param message The message to print. 
	 */
	public static void drawMessage(Graphics g) {	
		int boxWidth;
		String title;
		String description;
		Image image;
		
		if (inInventort) {
			boxWidth = MESSAGEBOX_WIDTH - INVENTORY_WIDTH - 2*MESSAGEBOK_PADDING;
			title = currentItem.getName();
			description = currentItem.getDescription();
			image = currentItem.getImage(2);
			g.fillRect(MESSAGEBOK_PADDING, DungeonGame.HEIGHT - MESSAGEBOX_HEIGHT, MESSAGEBOX_WIDTH - INVENTORY_WIDTH - 2*MESSAGEBOK_PADDING,
					MESSAGEBOX_HEIGHT - MESSAGEBOK_PADDING); 
		}
		else {
			boxWidth = MESSAGEBOX_WIDTH - 2*MESSAGEBOK_PADDING;
			g.fillRect(MESSAGEBOK_PADDING, DungeonGame.HEIGHT - MESSAGEBOX_HEIGHT,
					MESSAGEBOX_WIDTH - 2*MESSAGEBOK_PADDING, MESSAGEBOX_HEIGHT - MESSAGEBOK_PADDING); 
			title = messages.peek().getTitle();
			description = messages.peek().getDescription();
			image = messages.peek().getImage();
		}
		
		// Image Background
		g.setColor(BG_COLOR);
		g.fillRect(MESSAGEBOK_PADDING, DungeonGame.HEIGHT - MESSAGEBOX_HEIGHT, MESSAGEBOK_PADDING*7, MESSAGEBOX_HEIGHT - MESSAGEBOK_PADDING); 
		
		//Image
		image.draw(2*MESSAGEBOK_PADDING, DungeonGame.HEIGHT - MESSAGEBOX_HEIGHT + MESSAGEBOK_PADDING);
		
		// Text Background
		g.setColor(BG_COLOR);
		g.fillRect(8*MESSAGEBOK_PADDING, DungeonGame.HEIGHT - MESSAGEBOX_HEIGHT, boxWidth - 7*MESSAGEBOK_PADDING, MESSAGEBOX_HEIGHT - MESSAGEBOK_PADDING); 
				
		// Title
		g.setColor(TEXT_COLOR); 
		TITLE_FONT.drawString(9*MESSAGEBOK_PADDING, DungeonGame.HEIGHT - MESSAGEBOX_HEIGHT + MESSAGEBOK_PADDING, title);
					
		// Description
		DESCRIPTION_FONT.drawString(9*MESSAGEBOK_PADDING, DungeonGame.HEIGHT - MESSAGEBOX_HEIGHT + 3*MESSAGEBOK_PADDING, description);
		
		
		
	}
	
	/**
	 * 
	 * @param g The graphics context used by the container.
	 * @param items The items in the inventory.
	 */
	public static void showInventory(Map<String, Item> i) {
		
		if (!inInventort){ // Display inventory
			inInventort = true;
			items = i;
			currentItemIndex = 0;
			lookAtItem();
		}
		else { // Closes inventory
			inInventort = false; 
			currentItem = null;
		}
	}
	
	/**
	 * Handles drawing of inventory on screen.
	 * @param g The Graphics context used by the container.
	 */
	public static void drawInventory(Graphics g) {
		// Inventory properties
		int localOffset = OFFSET;
		
		// Draw inventory background
		g.setColor(BG_COLOR); 
		g.fillRect(DungeonGame.WIDTH - (INVENTORY_WIDTH), 0, (INVENTORY_WIDTH + ITEM_PADDING), DungeonGame.HEIGHT); 
		
		// Draw inventory title
		g.setColor(TEXT_COLOR); 
		TITLE_FONT.drawString((DungeonGame.WIDTH - INVENTORY_WIDTH) + (INVENTORY_ITEM_HEIGHT + ITEM_PADDING), OFFSET/3, "Inventory"); // Draw the name
				
		// Draw current Item background
		if (!items.isEmpty()) {
			g.setColor(CURRENT_ITEM_BG_COLOR); 
			g.fillRect(DungeonGame.WIDTH - (INVENTORY_WIDTH), (currentItemIndex * (INVENTORY_ITEM_HEIGHT + ITEM_PADDING/2) + localOffset),
					(INVENTORY_WIDTH + ITEM_PADDING), INVENTORY_ITEM_HEIGHT);
		}
		
		// Draw items in inventory
		for (Item item : items.values()) {
			item.draw(DungeonGame.WIDTH - INVENTORY_WIDTH + ITEM_PADDING, localOffset, INVENTORY_ITEM_HEIGHT/item.getPosition().getHeight()); // Draw the item
			
			// Draw the name
			g.setColor(TEXT_COLOR); 
			TITLE_FONT.drawString((DungeonGame.WIDTH - INVENTORY_WIDTH) + (INVENTORY_ITEM_HEIGHT + ITEM_PADDING), 
					(localOffset + INVENTORY_ITEM_HEIGHT/2) - (TITLE_FONT.getHeight()/2), item.getName()); 
			localOffset += INVENTORY_ITEM_HEIGHT + ITEM_PADDING/2;
		}
		
		if (currentItem != null) {
			drawMessage(g);
		}
	}
	
	/**
	 * Navigate in the inventory.
	 * @param direction The direction to navigate. 0 = up, 1 = down.
	 */
	public static void navigateInventory(int direction) throws IllegalArgumentException {
		if (direction > 1 || direction < 0) {
			throw new IllegalArgumentException("Direction must be 0 or 1");
		}
		
		if (!items.isEmpty()) {
			if (currentItemIndex == 0 && direction == 0) {return;} // If at top and pressing up, do nothing
			if (currentItemIndex == items.size() -1  && direction == 1) {return;} // If at bottom and pressing down, do nothing
			
			if (direction == 0) {currentItemIndex--;} // Up
			else if (direction == 1) {currentItemIndex++;} // Down
		}
	
		lookAtItem();
	}
	
	/**
	 * Look at current item in inventory.
	 */
	public static void lookAtItem() {
		if (items.isEmpty()) {return;} // Return if empty
		
		Iterator<Item> it = items.values().iterator();
		for (int i = 0; i < currentItemIndex && it.hasNext(); i++) { // Calls it.next() until item just before out current item
			it.next();
		}
		currentItem = it.next(); // Returns the current item. 
	}
	
	/**
	 * Return a boolean describing if player is in inventory or not
	 * @return the inInventory. True if player is in Inventory, false otherwises.
	 */
	public static boolean isInInventory() {
		return inInventort;
	}

	/** 
	 * Set the boolean describing if player is in inventory or not
	 * @param inInv Set to true if player is in invetory, false otherwise.
	 */
	public static void setInInventory(boolean inInv) {
		inInventort = inInv;
	}
}
