package hydra;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class SnakeGame implements BaseSubGame {
	private SnakeLevel level_;
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
	}
	
	public void update(GameContainer gc, int delta) {
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		level_ = new SnakeLevel((int)clip.getWidth(), (int)clip.getHeight());
	}
}
