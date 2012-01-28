package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeHead extends SnakeTileSnake {
	private int headDirection;
	Image[] headImages = new Image[1];
	Animation headAnim = new Animation(false);

	public SnakeTileSnakeHead(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect, successor);
		System.out.print(headDirection);

		// Load background for Snake
		try {
			
			}
			
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}

		headAnim.addFrame(headImages[0], 1);
	}

	public void render(Graphics g) {
		headAnim.draw(clipRect_.getX(), clipRect_.getY());
	}

	public void move(int direction) {
		moveRecursive();
		
		headDirection = direction;
		switch (direction) {
		default:
		case SnakeGame.DIRECTION_RIGHT:
			SnakeGame.instance_.moveHead(x_ + 1, y_);
			break;

		case SnakeGame.DIRECTION_LEFT:
			SnakeGame.instance_.moveHead(x_ - 1, y_);
			break;

		case SnakeGame.DIRECTION_UP:
			SnakeGame.instance_.moveHead(x_, y_ - 1);
			break;

		case SnakeGame.DIRECTION_DOWN:
			SnakeGame.instance_.moveHead(x_, y_ + 1);
			break;

		}
	}
}

