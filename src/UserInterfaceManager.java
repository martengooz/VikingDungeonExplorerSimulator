import java.awt.Font;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;


public class UserInterfaceManager {	
	// Inventory properties
	private static boolean inInventory;
	
	private static Item currentItem; 
	private static int currentItemIndex = 0;
	
	private static int inventoryWidth = 300; // Pixles from right corner that inventory should take up
	private static float inventoryItemHeight = 40; // Pixels each item takes up in the list
	private final static int offset = 80; // Start drawing items i px from top 
	private static int itemPadding = 10;
	
	// Message properties
	private static int messageBoxPadding = 20;
	private static int messageBoxWidth = DungeonGame.WIDTH;
	private static int messageBoxHeight = 200; 

	// Other
	private static Color textColor = Color.white;
	private static Color bgColor = Color.lightGray;
	private static Color currentItemBgColor = Color.gray;
	
	private static Font awtTitleFont = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 24);
	private static TrueTypeFont titleFont = new TrueTypeFont(awtTitleFont, false);
	private static Font awtDescriptionFont = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 14);
	private static TrueTypeFont descriptionFont = new TrueTypeFont(awtDescriptionFont, false);
	
	private static Map<String, Item> items;
	private static Queue<Message> messages;
	
	/**
	 * Add a message to the message queue. 
	 * @param g The graphics context used by the container.
	 * @param title The title of the message.
	 * @param message The message to print. 
	 */
	public static void addMessage(Image image, String title, String message) throws IllegalArgumentException{
		if (image == null || title == null || message == null) { throw new IllegalArgumentException("Must provide an image, title and description."); }
		messages.add(new Message(image, title, message));
	}
	
	public static void nextMessage(){
		messages.remove();
	}
	
	public static boolean hasMessage() {
		if (messages == null) {return false;}
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
		
		if (inInventory) {
			boxWidth = messageBoxWidth - inventoryWidth - 2*messageBoxPadding;
			title = currentItem.getName();
			description = currentItem.getDescription();
			image = currentItem.getImage(0);
			g.fillRect(messageBoxPadding, DungeonGame.HEIGHT - messageBoxHeight, messageBoxWidth - inventoryWidth - 2*messageBoxPadding, messageBoxHeight - messageBoxPadding); 
			
			
		}
		else {
			boxWidth = messageBoxWidth - 2*messageBoxPadding;
			g.fillRect(messageBoxPadding, DungeonGame.HEIGHT - messageBoxHeight, messageBoxWidth - 2*messageBoxPadding, messageBoxHeight - messageBoxPadding); 
			title = messages.peek().getTitle();
			description = messages.peek().getDescription();
			image = messages.peek().getImage();
		}
		
		// Background
		g.setColor(bgColor);
		g.fillRect(messageBoxPadding, DungeonGame.HEIGHT - messageBoxHeight, boxWidth, messageBoxHeight - messageBoxPadding); 
		
		// Title
		g.setColor(textColor); 
		titleFont.drawString(8*messageBoxPadding, DungeonGame.HEIGHT - messageBoxHeight + messageBoxPadding, title);
					
		// Description
		descriptionFont.drawString(8*messageBoxPadding, DungeonGame.HEIGHT - messageBoxHeight + 3*messageBoxPadding, description);
		
		//Image
		image.draw(messageBoxPadding, DungeonGame.HEIGHT - messageBoxHeight);
		
	}
	
	/**
	 * 
	 * @param g The graphics context used by the container.
	 * @param items The items in the inventory.
	 */
	public static void showInventory(Map<String, Item> i) {
		
		if (!inInventory){ // Display inventory
			inInventory = true;
			items = i;
			currentItemIndex = 0;
			lookAtItem();
		}
		else { // Closes inventory
			inInventory = false; 
			currentItem = null;
		}
	}
	
	/**
	 * Handles drawing of inventory on screen.
	 * @param g The Graphics context used by the container.
	 */
	public static void drawInventory(Graphics g) {
		// Inventory properties
		int localOffset = offset;
		
		// Draw inventory background
		g.setColor(bgColor); 
		g.fillRect(DungeonGame.WIDTH - (inventoryWidth), 0, (inventoryWidth + itemPadding), DungeonGame.HEIGHT); 
		
		// Draw inventory title
		g.setColor(textColor); 
		titleFont.drawString((DungeonGame.WIDTH - inventoryWidth) + (inventoryItemHeight + itemPadding), 30, "Inventory"); // Draw the name
				
		// Draw current Item background
		if (!items.isEmpty()) {
			g.setColor(currentItemBgColor); 
			g.fillRect(DungeonGame.WIDTH - (inventoryWidth), (currentItemIndex * (inventoryItemHeight + itemPadding/2) + localOffset), (inventoryWidth + itemPadding), inventoryItemHeight);
		}
		
		// Draw items in inventory
		for (Item item : items.values()) {
			item.draw(DungeonGame.WIDTH - inventoryWidth + itemPadding, localOffset, inventoryItemHeight/item.getPosition().getHeight()); // Draw the item
			
			g.setColor(textColor); 
			titleFont.drawString((DungeonGame.WIDTH - inventoryWidth) + (inventoryItemHeight + itemPadding), (localOffset + inventoryItemHeight/2) - (titleFont.getHeight()/2), item.getName()); // Draw the name
			localOffset += inventoryItemHeight + itemPadding/2;
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
		if (direction > 1 || direction < 0) {throw new IllegalArgumentException("Direction must be 0 or 1");}
		
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
		return inInventory;
	}

	/** 
	 * Set the boolean describing if player is in inventory or not
	 * @param inInv Set to true if player is in invetory, false otherwise.
	 */
	public static void setInInventory(boolean inInv) {
		inInventory = inInv;
	}
}
