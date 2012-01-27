package hydra;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public interface BaseSubGame {
	public void render(GameContainer gc, Graphics g, Rectangle clip);
	public void update(GameContainer gc, int delta);
	public void init(GameContainer gc, Rectangle clip);
}
