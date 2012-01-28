package hydra;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import hydra.snake.SnakeGame;

public class App extends BasicGame {
	public static App instance_;
	
	boolean exitRequested_ = false;
	
	private SnakeGame snakeGame_ = new SnakeGame();;
	private JumpAndRunGame jumpAndRunGame_ = new JumpAndRunGame();
	
	private Rectangle snakeRectangle_ = new Rectangle(0, 0, 900, 400);
	private Rectangle jumpAndRunRectangle_ = new Rectangle(0, 400, 900, 700);
	
	public App() {
		super("<insert awesome name here>");
		instance_ = this;
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
		if (exitRequested_) {
			gc.exit();
		} else {
			snakeGame_.update(gc, delta);
			jumpAndRunGame_.update(gc, delta);
		}
	}
	
	public void init(GameContainer gc) {
		snakeGame_.init(gc, snakeRectangle_);
		jumpAndRunGame_.init(gc, jumpAndRunRectangle_);
	}
	
	public void gameOver(boolean snakeWins) {
		if (snakeWins) {
			System.out.println("Snake wins!");
		} else {
			System.out.println("Jump&run wins!");
		}
		
		exitRequested_ = true;
	}
}

