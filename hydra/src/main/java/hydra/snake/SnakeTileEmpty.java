
package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileEmpty extends SnakeTile {
	public SnakeTileEmpty(int x, int y, Rectangle rect) {
		super(x, y, rect);
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(100, 250, 100));
		g.fillRect(clipRect_.getX(), clipRect_.getY(), clipRect_.getWidth(), clipRect_.getHeight());
	}
	
	public int[] onHeadMovement() {
		return new int[] {x_, y_};
	}
}
