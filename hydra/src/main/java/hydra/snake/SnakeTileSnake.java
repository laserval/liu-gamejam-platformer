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
		} else if (SnakeGame.instance_.shouldSnakeGrow()) {
			successor_ = new SnakeTileSnakeBody(x_, y_, clipRect_, null);
			SnakeGame.instance_.moveTile(successor_, x_, y_);
			SnakeGame.instance_.onSnakeGrown();
		} else {
			SnakeGame.instance_.clearTile(x_, y_);
		}
	}
	
	public int[] onHeadMovement() {
		SnakeGame.instance_.onSnakeEatSelf(x_, y_);
		return new int[] {x_, y_};
	}
}

