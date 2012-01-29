
package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileEmpty extends SnakeTile {
	static Animation tilesAnim = new Animation(false);


	public SnakeTileEmpty(int x, int y, Rectangle rect) {
		super(x, y, rect);
	}
	
	static {
		Image[] tilesImages = new Image[1];
		// Load background for Snake
		try {
			tilesImages[0] = new Image("bg_tile.jpg");
		} catch(SlickException e) {
			System.out.println(e);
		}

		tilesAnim.addFrame(tilesImages[0], 1);
	}

	public void render(Graphics g) {
		tilesAnim.draw(clipRect_.getX(), clipRect_.getY());
	}

	public int[] onHeadMovement() {
		return new int[] {x_, y_};
	}
	
	public String toString() {
		return "empty";
	}
}
