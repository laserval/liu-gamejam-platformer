package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeHead extends SnakeTileSnake {
	public SnakeTileSnakeHead(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect, successor);
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(220, 220, 0));
		g.fillOval(clipRect_.getX(), clipRect_.getY(), clipRect_.getWidth(), clipRect_.getHeight());
	}
	
	public void move(int direction) {
		moveRecursive();
		
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

