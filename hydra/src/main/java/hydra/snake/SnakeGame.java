package hydra.snake;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import hydra.BaseSubGame;

public class SnakeGame implements BaseSubGame {
	private SnakeLevel level_;
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
		level_.render(g, clip);
	}
	
	public void update(GameContainer gc, int delta) {
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		level_ = new SnakeLevel((int)clip.getWidth(), (int)clip.getHeight());
	}
}
