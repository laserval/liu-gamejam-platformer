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
}

