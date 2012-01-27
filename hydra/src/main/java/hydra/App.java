package hydra;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class App extends BasicGame {
	private SnakeGame snakeGame_ = new SnakeGame();;
	private JumpAndRunGame jumpAndRunGame_ = new JumpAndRunGame();
	
	private Rectangle snakeRectangle_ = new Rectangle(0, 0, 400, 900);
	private Rectangle jumpAndRunRectangle_ = new Rectangle(0, 400, 700, 900);
	
	public App() {
		super("<insert awesome name here>");
	}
	
    public static void main(String[] args) throws Exception {
		AppGameContainer container = new AppGameContainer(new App(), 900, 700, false);
		container.start();
    }
    
    public void render(GameContainer gc, Graphics g) {
		snakeGame_.render(gc, g, snakeRectangle_);
		jumpAndRunGame_.render(gc, g, jumpAndRunRectangle_);
	}
	
	public void update(GameContainer gc, int delta) {
		snakeGame_.update(gc, delta);
		jumpAndRunGame_.update(gc, delta);
	}
	
	public void init(GameContainer gc) {
		snakeGame_.init(gc, snakeRectangle_);
		jumpAndRunGame_.init(gc, jumpAndRunRectangle_);
	}
}

