package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeHead extends SnakeTileSnake {
	public SnakeTileSnakeHead(Rectangle rect) {
		super(rect);
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(80, 80, 0));
		g.fillOval(clipRect_.getX(), clipRect_.getY(), clipRect_.getWidth(), clipRect_.getHeight());
	}
}

