package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeBody extends SnakeTileSnake {
	Image[] bodyImages = new Image[1];
	Animation bodyAnim = new Animation(false);

	public SnakeTileSnakeBody(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect, successor);

		// Load background for Snakes body
		try {
			bodyImages[0] = new Image("snake_tile.jpg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}

		bodyAnim.addFrame(bodyImages[0], 1);
	}

	public void render(Graphics g) {
		bodyAnim.draw(clipRect_.getX(), clipRect_.getY());
	}
}
