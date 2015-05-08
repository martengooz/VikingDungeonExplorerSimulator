import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;


public class DungeonGame extends BasicGame {
	
	// Application Properties
	public static final String VERSION = "Alpha";
    private static final int WIDTH   = 1280;
    private static final int HEIGHT  = 720;
    
    //Input handling
    Input input = new Input(HEIGHT);
    public final int keyMoveUp = Input.KEY_W;
    public final int keyMoveDown = Input.KEY_S;
    public final int keyMoveLeft = Input.KEY_A;
    public final int keyMoveRight = Input.KEY_D;
    public final int keyExit = Input.KEY_ESCAPE;
    
    // Players
    private static Player redbeard; 
    private static String redbeardImageLocation = "res\\images\\player1.png";
    	
	public DungeonGame(String game) {
		super(game);
	   

		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		//redbeardImage.draw();
		redbeard.getImage().draw(redbeard.getPosition().getX(), redbeard.getPosition().getY());
		
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		// TODO Auto-generated method stub
		redbeard = new Player("Redbeard", redbeardImageLocation, new Point(100, 100), new Point(0, 0), new Point(0, 0));
		redbeard.loadTexture();
		//MapManager.getFirstRoom().loadTexture(); // Load first room 
	}

	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		//Update input
		if(input.isKeyDown(keyMoveUp)) {redbeard.move(0);}
		if(input.isKeyDown(keyMoveDown)) {redbeard.move(2);}
		if(input.isKeyDown(keyMoveLeft)) {redbeard.move(1);}
		if(input.isKeyDown(keyMoveRight)) {redbeard.move(3);}
		
		if(input.isKeyDown(keyExit)) {System.exit(1);}
		
		redbeard.update(delta);
	}

	public static void main(String[] args) throws SlickException {
	     DungeonGame game = new DungeonGame("Viking Dungeon Explorer Simulator " + VERSION);
	     try {
	          AppGameContainer container = new AppGameContainer(game, WIDTH, HEIGHT, false);
	          container.setShowFPS(false); // Removes FPS counter
	          container.start();
	     } catch (SlickException e) {
	          e.printStackTrace();
	     }
	}

}
