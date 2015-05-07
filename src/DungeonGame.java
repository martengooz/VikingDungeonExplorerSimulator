import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class DungeonGame extends BasicGame {
	
	// Application Properties
    public static final int WIDTH   = 1280;
    public static final int HEIGHT  = 720;
    public static final String VERSION = "Alpha";
    	
	public DungeonGame(String game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("Hello World!",200,200);

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
	     DungeonGame game = new DungeonGame("Slick Tutorial");
	     DungeonGame game = new DungeonGame("Viking Dungeon Explorer Simulator " + VERSION);
	     try {
	          AppGameContainer container = new AppGameContainer(game);
	          AppGameContainer container = new AppGameContainer(game, WIDTH, HEIGHT, false);
	          container.setShowFPS(false); // Removes FPS counter
	          container.start();
	     } catch (SlickException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	     }
	     
	}

}
