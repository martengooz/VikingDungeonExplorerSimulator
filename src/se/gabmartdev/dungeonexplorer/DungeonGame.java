package se.gabmartdev.dungeonexplorer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class DungeonGame extends BasicGame {

	// Application Properties
	public static final String VERSION = "Beta";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static int DOORWIDTH = 180;
	private static int DOORHEIGHT = 20;
	public static final int[] WALLWIDTH = { 40, 40, 60, 40 }; // How close the player can be to the walls in following order: up, right, down, left.
	public static final Rectangle[] DOORAREA = { // The area the player can stand in to trigger going through a door
			new Rectangle((WIDTH - DOORWIDTH) / 2, WALLWIDTH[0] - 5, 
					DOORWIDTH,	DOORHEIGHT), // Up
			new Rectangle(WIDTH - WALLWIDTH[1] - DOORHEIGHT + 5, (HEIGHT - DOORWIDTH) / 2,
					DOORHEIGHT, DOORWIDTH), // Right
			new Rectangle((WIDTH - DOORWIDTH) / 2, HEIGHT - WALLWIDTH[2] + 5
					- DOORHEIGHT, DOORWIDTH, DOORHEIGHT), // Down
			new Rectangle(WALLWIDTH[3] - 5, (HEIGHT - DOORWIDTH) / 2,
					DOORHEIGHT,	DOORWIDTH) }; // Left

	// Input handling
	Input input = new Input(HEIGHT);
	public final int[] KEY_MOVE_UP = { Input.KEY_W, Input.KEY_UP };
	public final int[] KEY_MOVE_DOWN = { Input.KEY_S, Input.KEY_DOWN };
	public final int[] KEY_MOVE_LEFT = { Input.KEY_A, Input.KEY_LEFT };
	public final int[] KEY_MOVE_RIGHT = { Input.KEY_D, Input.KEY_RIGHT };
	public final int KEY_ACT = Input.KEY_E;
	public final int KEY_INVENTORY = Input.KEY_I;
	public final int KEY_EXIT = Input.KEY_ESCAPE;

	// Players
	private static Player redbeard;
	private static String[] redbeardImageLocation = {
			"res/images/Player/playerUp.png",
			"res/images/Player/playerRight.png",
			"res/images/Player/playerDown.png",
			"res/images/Player/playerLeft.png" };

	public DungeonGame(String game) {
		super(game);
	}

	@Override
	public void render(GameContainer gameContainer, Graphics g)
			throws SlickException {
		redbeard.getCurrentRoom().draw();
		redbeard.draw();

		if (UserInterfaceManager.hasMessage() && !UserInterfaceManager.isInInventory()) {
			UserInterfaceManager.drawMessage(g);
		}
		if (UserInterfaceManager.isInInventory()) {
			UserInterfaceManager.drawInventory(g);
		}
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {

		MapManager.generateMap();

		redbeard = new Player("Redbeard", redbeardImageLocation, new Rectangle(
				(WIDTH - 128) / 2, (HEIGHT - 128) / 2, 128, 128), new Point(0, 0), new Point(0, 0));
		redbeard.setCurrentRoom(MapManager.getFirstRoom());

		redbeard.loadImage(); // Load player texture
		redbeard.getCurrentRoom().loadImage(); // Load first room

	}

	@Override
	public void update(GameContainer gameContainer, int delta)
			throws SlickException {
	
		// Update input
		Input input = gameContainer.getInput();
		
		// Check if player has won the game
		if (redbeard.getCurrentRoom() == MapManager.getLastRoom()) {
			if (!UserInterfaceManager.hasMessage()) {
				UserInterfaceManager.addMessage(redbeard.getImage(2), "\"I am free, finally free!\"", "Congratulations, you have completed the game! Press ESC to exit.");
			}
			redbeard.setCollide(false, false); //Disable Collision
			redbeard.setAcceleration(new Point(0f, 0.03f)); // Automatically move redbeard to bottom of screen
			redbeard.update(delta, redbeard.getCurrentRoom()); // Update player
			
			if (input.isKeyPressed(KEY_EXIT)) { // Close game of ESC is pressed
				System.exit(0);
			}
		}
		else {

			// While in inventory
			if (UserInterfaceManager.isInInventory()) {
				// Closes inventory
				if (input.isKeyPressed(KEY_INVENTORY)) {
					UserInterfaceManager.showInventory(redbeard.getItems());
				}
				if (input.isKeyPressed(KEY_EXIT)) {
					UserInterfaceManager.showInventory(redbeard.getItems());
				}
	
				// Navigating in inventory
				for (int key : KEY_MOVE_UP) { // Up
					if (input.isKeyPressed(key)) {
						UserInterfaceManager.navigateInventory(0);
					}
				}
				for (int key : KEY_MOVE_DOWN) { // Down
					if (input.isKeyPressed(key)) {
						UserInterfaceManager.navigateInventory(1);
					}
				}
			}
	
			// While in game
			else {
				// Player movement
				for (int key : KEY_MOVE_UP) {
					if (input.isKeyDown(key)) {
						redbeard.move(0);
					}
				}
				for (int key : KEY_MOVE_DOWN) {
					if (input.isKeyDown(key)) {
						redbeard.move(2);
					}
				}
				for (int key : KEY_MOVE_LEFT) {
					if (input.isKeyDown(key)) {
						redbeard.move(3);
					}
				}
				for (int key : KEY_MOVE_RIGHT) {
					if (input.isKeyDown(key)) {
						redbeard.move(1);
					}
				}
	
				// Others
				if (input.isKeyPressed(KEY_ACT)) {
					if (UserInterfaceManager.hasMessage()) {
						UserInterfaceManager.nextMessage();
					} else {
						redbeard.act();
					}
				}
				if (input.isKeyPressed(KEY_EXIT)) {
					if (UserInterfaceManager.hasMessage()) {
						UserInterfaceManager.nextMessage();
					}
				}
				if (input.isKeyPressed(KEY_INVENTORY)) {
					UserInterfaceManager.showInventory(redbeard.getItems());
				}
			}
	
			redbeard.update(delta, redbeard.getCurrentRoom()); // Update player
			redbeard.getCurrentRoom().update(delta); // Update room
		}
	}

	public static void main(String[] args) throws SlickException {
		DungeonGame game = new DungeonGame("Viking Dungeon Explorer Simulator "	+ VERSION);
		try {
			AppGameContainer container = new AppGameContainer(game, WIDTH, HEIGHT, false);
			container.setShowFPS(true); // Show FPS counter
			container.setVSync(true);
			container.setIcons(new String[] {"res/images/Icons/icon16.png", "res/images/Icons/icon24.png", "res/images/Icons/icon32.png"});
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
