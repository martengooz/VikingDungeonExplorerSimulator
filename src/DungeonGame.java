import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class DungeonGame extends BasicGame {

	public DungeonGame(String title) {
		super(title);
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
	     try {
	          AppGameContainer container = new AppGameContainer(game);
	          container.start();
	     } catch (SlickException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	     }
	     
	}

}
