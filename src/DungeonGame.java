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
	public static final String VERSION = "Alpha";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static int DOORWIDTH = 180;
	private static int DOORHEIGHT = 20;
    public static final int[] WALLWIDTH = {40, 40, 60, 40}; //How close the player can be to the walls in following order: up, right, down, left.
    public static final Rectangle[] DOORAREA = { //The area the player can stand in to trigger going through a door
    	new Rectangle((WIDTH - DOORWIDTH)/2, WALLWIDTH[0], DOORWIDTH, DOORHEIGHT), //Up
    	new Rectangle(WIDTH - WALLWIDTH[1] - DOORHEIGHT, (HEIGHT - DOORWIDTH)/2, DOORHEIGHT, DOORWIDTH), //Right
    	new Rectangle((WIDTH - DOORWIDTH)/2, HEIGHT - WALLWIDTH[2] - DOORHEIGHT, DOORWIDTH, DOORHEIGHT), //Down
    	new Rectangle(WALLWIDTH[3], (HEIGHT - DOORWIDTH)/2, DOORHEIGHT, DOORWIDTH)}; //Left
    
    // Input handling
    Input input = new Input(HEIGHT);
    public final int[] keyMoveUp = {Input.KEY_W, Input.KEY_UP};
    public final int[] keyMoveDown = {Input.KEY_S, Input.KEY_DOWN} ;
    public final int[] keyMoveLeft = {Input.KEY_A, Input.KEY_LEFT};
    public final int[] keyMoveRight = {Input.KEY_D, Input.KEY_RIGHT};
    public final int keyAct = Input.KEY_E;
    public final int keyInventory = Input.KEY_I;
    public final int keyExit = Input.KEY_ESCAPE;
    
    // Players
    private static Player redbeard; 
    private static String[] redbeardImageLocation = {"res\\images\\Player\\playerUp.png",
    	"res\\images\\Player\\playerRight.png",
    	"res\\images\\Player\\playerDown.png", 
    	"res\\images\\Player\\playerLeft.png"};
        
	public DungeonGame(String game) {
		super(game);
	}

	@Override
	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		redbeard.getCurrentRoom().draw();
		redbeard.draw();
		
		if (redbeard.isInInventory()){
			redbeard.drawInventory(g);
		}
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {
	    
		MapManager.generateMap();
		
		redbeard = new Player("Redbeard", redbeardImageLocation, new Rectangle((WIDTH - 128)/2, (HEIGHT - 128)/2, 128, 128), new Point(0, 0), new Point(0, 0));
		redbeard.setCurrentRoom(MapManager.getFirstRoom());
		
		redbeard.loadImage(); //Load player texture
		redbeard.getCurrentRoom().loadImage(); // Load first room 

	}

	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		//Update input
		Input input = gameContainer.getInput();
		
		// While in inventory
		if (redbeard.isInInventory()){
			//Closes inventory
			if(input.isKeyPressed(keyInventory)) {redbeard.inventory();} 
			if(input.isKeyPressed(keyExit)) {redbeard.inventory();}
			
			// Navigating in inventory
			for(int key : keyMoveUp){if(input.isKeyPressed(key)) {redbeard.navigateInventory(0);}} // Up
			for(int key : keyMoveDown){if(input.isKeyPressed(key)) {redbeard.navigateInventory(1);}} // Down
			if(input.isKeyPressed(keyAct)) {redbeard.lookAtItem();} // Look at item
		} 
		
		// While in game
		else {  
			// Player movement
			for(int key : keyMoveUp){if(input.isKeyDown(key)) {redbeard.move(0);}}
			for(int key : keyMoveDown){if(input.isKeyDown(key)) {redbeard.move(2);}}
			for(int key : keyMoveLeft){if(input.isKeyDown(key)) {redbeard.move(3);}}
			for(int key : keyMoveRight){if(input.isKeyDown(key)) {redbeard.move(1);}}
					
			//Others
			if(input.isKeyPressed(keyAct)) {redbeard.act();}
			if(input.isKeyPressed(keyInventory)) {redbeard.inventory();}
			if(input.isKeyPressed(keyExit)) {System.exit(1);}
		}
		
		
		redbeard.update(delta, redbeard.getCurrentRoom()); //Update player
		redbeard.getCurrentRoom().removeMarked(); //Update room
	}

	public static void main(String[] args) throws SlickException {
	     DungeonGame game = new DungeonGame("Viking Dungeon Explorer Simulator " + VERSION);
	     try {
	          AppGameContainer container = new AppGameContainer(game, WIDTH, HEIGHT, false);
	          //container.setShowFPS(false); // Removes FPS counter
	          container.setVSync(true);
	          container.start();
	     } catch (SlickException e) {
	          e.printStackTrace();
	     }
	}

}
