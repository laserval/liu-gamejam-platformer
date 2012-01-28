package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileWallLeft extends SnakeTile {
	Image[] wallImages = new Image[1];
	Animation wallAnim = new Animation(false);

	public SnakeTileWallLeft(int x, int y, Rectangle rect) {
		super(x, y, rect);

		try {
			wallImages[0] = new Image("border_tile_left.jpg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}

		wallAnim.addFrame(wallImages[0], 1);
	}

	public void render(Graphics g) {
		wallAnim.draw(clipRect_.getX(), clipRect_.getY());
	}

	public int[] onHeadMovement() {
		SnakeGame.instance_.onCrash();
		return new int[] {x_, y_};
	}

	public String toString() {
		return "wall";
	}
}
