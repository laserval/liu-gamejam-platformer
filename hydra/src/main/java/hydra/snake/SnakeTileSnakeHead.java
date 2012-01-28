package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeHead extends SnakeTileSnake {
	private int headDirection = 2;
	private Animation[] headAnim = new Animation[4];
	
	public SnakeTileSnakeHead(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect, successor);

		for (int i = 0; i < 4; i++) {
			headAnim[i] = new Animation(false);
		}
		loadAnimation(0, "head_tile_head_left.jpg");
		loadAnimation(1, "head_tile_head_right.jpg");
		loadAnimation(2, "head_tile_head_up.jpg");
		loadAnimation(3, "head_tile_head_down.jpg");
	}

	public void loadAnimation(int index, String name) {
		Image[] headImages = new Image[1];

		// Load background for Snakes head
		try {
			headImages[0] = new Image(name);
		} catch(SlickException e) {
			System.out.println(e);
			System.exit(1);
			//return;
			
		}
		
		headAnim[index].addFrame(headImages[0], 1);
	}
	
	public void render(Graphics g) {
		headAnim[headDirection].draw(clipRect_.getX(), clipRect_.getY());
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
	
	public String toString() {
		return "head";
	}
}

