package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public abstract class SnakeTileSnake extends SnakeTile {
	private SnakeTileSnake successor_;
	
	public SnakeTileSnake(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect);
		successor_ = successor;
	}
	
	public SnakeTileSnake getSuccessor() {
		return successor_;
	}
	
	public void moveRecursive() {
		if (successor_ != null) {
			successor_.moveRecursive();
			SnakeGame.instance_.moveTile(successor_, x_, y_);
		} else {
			SnakeGame.instance_.clearTile(x_, y_);
		}
	}
	
	public void move(int direction) {
		moveRecursive();
		
		switch (direction) {
		default:
		case SnakeGame.DIRECTION_RIGHT:
			SnakeGame.instance_.moveTile(this, x_ + 1, y_);
			break;
			
		case SnakeGame.DIRECTION_LEFT:
		SnakeGame.instance_.moveTile(this, x_ - 1, y_);
			break;
			
		case SnakeGame.DIRECTION_UP:
		SnakeGame.instance_.moveTile(this, x_, y_ - 1);
			break;
			
		case SnakeGame.DIRECTION_DOWN:
		SnakeGame.instance_.moveTile(this, x_, y_ + 1);
			break;
			
		}
	}
}

