
package hydra;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import hydra.BaseSubGame;
import hydra.App;
import hydra.jumpandrun.JumpAndRunGame;

public class StartScreen {
	public static StartScreen instance_;
	
	private static Image initImage;
	private static Image gameOverAppleImage;
	private static Image gameOverSnakeImage;
	
	static {
		try {
			initImage = new Image("game_start.jpg");
		} catch(SlickException e) {
			System.out.println(e);
		}
		
		try {
			gameOverAppleImage = new Image("game_over_apple.jpg");
		} catch(SlickException e) {
			System.out.println(e);
		}
		
		try {
			gameOverSnakeImage = new Image("game_over_snake.jpg");
		} catch(SlickException e) {
			System.out.println(e);
		}
	}
	
	public StartScreen() {
		instance_ = this;
	}
	
	public boolean initScreen = false;
	
	public void render(GameContainer gc, Graphics g) {
		if (initScreen) {
			initImage.draw(0, 0);
		} else if (App.instance_.snakeWins_) {
			gameOverSnakeImage.draw(0, 0);
		} else {
			gameOverAppleImage.draw(0, 0);
		}
	}
	
	public void update(GameContainer gc, int delta) {
		if (gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			App.instance_.showStartScreenNext_ = false;
		}
	}
	
	public void init(GameContainer gc) {
	}
	
}
