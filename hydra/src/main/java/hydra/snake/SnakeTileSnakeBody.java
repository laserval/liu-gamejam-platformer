package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeBody extends SnakeTileSnake {
	public SnakeTileSnakeBody(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect, successor);
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(150, 150, 0));
		g.fillOval(clipRect_.getX(), clipRect_.getY(), clipRect_.getWidth(), clipRect_.getHeight());
	}
	
	public String toString() {
		return "body";
	}
}
