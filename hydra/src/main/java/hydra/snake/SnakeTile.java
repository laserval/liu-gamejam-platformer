package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public abstract class SnakeTile {
	public Rectangle clipRect_;
	
	public SnakeTile(Rectangle clipRect) {
		clipRect_ = clipRect;
	}
	
	public abstract void render(Graphics g);
}
