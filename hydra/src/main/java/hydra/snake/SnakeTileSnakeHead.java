package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeHead extends SnakeTileSnake {
	private int headDirection = 0;
	Image[] headImages = new Image[4];
	Animation headAnim = new Animation(false);

	public SnakeTileSnakeHead(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect, successor);

		// Load background for Snake
		try {
			headImages[0] = new Image("head_tile_right.jpg");
			headImages[1] = new Image("head_tile_left.jpg");
			headImages[2] = new Image("head_tile_up.jpg");
			headImages[3] = new Image("head_tile_down.jpg");

		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		headAnim.addFrame(headImages[headDirection], 1);
	}

	public void render(Graphics g) {
		headAnim.draw(clipRect_.getX(), clipRect_.getY());
	}

	public void move(int direction) {
		moveRecursive();

		/*
		headAnim.removeFrame(headImages[headDirection], 1);		
		 
		headDirection = direction;
		headAnim.addFrame(headImages[headDirection], 1);
		*/
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

