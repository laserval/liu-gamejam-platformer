package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileFood extends SnakeTile {
	public SnakeTileFood(int x, int y, Rectangle rect) {
		super(x, y, rect);
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 220));
		g.fillOval(clipRect_.getX() + 1, clipRect_.getY() + 1, clipRect_.getWidth() - 2, clipRect_.getHeight() - 2);
	}
	
	public int[] onHeadMovement() {
		SnakeGame.instance_.growSnake(2);
		return new int[] {x_, y_};
	}
}

