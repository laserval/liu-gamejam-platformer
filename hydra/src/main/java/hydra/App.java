package hydra;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Input;

import hydra.snake.SnakeGame;
import hydra.jumpandrun.JumpAndRunGame;

public class App extends BasicGame {
	public static App instance_;
	
	boolean exitRequested_ = false;
	
	private SnakeGame snakeGame_ = new SnakeGame();
	private JumpAndRunGame jumpAndRunGame_ = new JumpAndRunGame();
	
	private Rectangle snakeRectangle_ = new Rectangle(0, 0, 900, 400);
	private Rectangle jumpAndRunRectangle_ = new Rectangle(0, 400, 900, 300);
	
	public boolean showStartScreenNext_ = true;
	private boolean showStartScreen_ = true;
	private boolean lastStartScreen_ = true;
	
	
	private StartScreen startScreen_;
	public boolean snakeWins_ = true;
	
	public App() {
		super("<insert awesome name here>");
		instance_ = this;
	}
	
    public static void main(String[] args) throws Exception {
		AppGameContainer container = new AppGameContainer(new App(), 900, 700, false);
		container.setTargetFrameRate(60);
		container.start();
    }
    
    public void render(GameContainer gc, Graphics g) {
		if (showStartScreen_) {
			startScreen_.render(gc, g);
		} else {
			snakeGame_.render(gc, g, snakeRectangle_);
			jumpAndRunGame_.render(gc, g, jumpAndRunRectangle_);
		}
	}
	
	public void update(GameContainer gc, int delta) {
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
			return;
		}
		
		showStartScreen_ = showStartScreenNext_;
		if (showStartScreen_) {
			if (!lastStartScreen_) {
				// just switched to start screen
				initStartScreen(gc);
			}
			startScreen_.update(gc, delta);
		} else {
			if (lastStartScreen_) {
				// just switched to the games
				initSubGames(gc);
			}
			snakeGame_.update(gc, delta);
			jumpAndRunGame_.update(gc, delta);
		}
		
		lastStartScreen_ = showStartScreen_;
	}
	
	public void init(GameContainer gc) {
		initStartScreen(gc);
		StartScreen.instance_.initScreen = true;
	}
	
	public void initSubGames(GameContainer gc) {
		snakeGame_ = new SnakeGame();
		jumpAndRunGame_ = new JumpAndRunGame();
		
		snakeGame_.init(gc, snakeRectangle_);
		jumpAndRunGame_.init(gc, jumpAndRunRectangle_);
	}
	
	public void initStartScreen(GameContainer gc) {
		startScreen_ = new StartScreen();
		startScreen_.init(gc);
	}
	
	public void gameOver(boolean snakeWins) {
		if (snakeWins) {
			snakeWins_ = true;
			System.out.println("Snake wins!");
		} else {
			snakeWins_ = false;
			System.out.println("Jump&run wins!");
		}
		
		showStartScreenNext_ = true;
	}
}

