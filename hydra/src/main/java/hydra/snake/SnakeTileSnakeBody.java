package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileSnakeBody extends SnakeTileSnake {
	protected Animation[] bodyAnim = new Animation[10];

	public SnakeTileSnakeBody(int x, int y, Rectangle rect, SnakeTileSnake successor, boolean loadTextures) {
		super(x, y, rect, successor);
		
		for (int i = 0; i < 10; i++) {
			bodyAnim[i] = new Animation(false);
		}
		
		if (loadTextures) {
			loadAnimation(0, "snake_tile_vertical.jpg");
			loadAnimation(1, "snake_tile_horizontal.jpg");
			loadAnimation(2, "snake_tile_up_left.jpg");
			loadAnimation(3, "snake_tile_up_right.jpg");
			loadAnimation(4, "snake_tile_down_left.jpg");
			loadAnimation(5, "snake_tile_down_right.jpg");
			loadAnimation(6, "snake_tile_tail_vertical_up.jpg");
			loadAnimation(7, "snake_tile_tail_vertical_down.jpg");
			loadAnimation(8, "snake_tile_tail_horizontal_left.jpg");
			loadAnimation(9, "snake_tile_tail_horizontal_right.jpg");
		}
	}
	
	public void loadAnimation(int index, String name) {
		System.out.println("load anim " + name);
		Image[] bodyImages = new Image[1];

		// Load background for Snakes body
		try {
			bodyImages[0] = new Image(name);
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}

		bodyAnim[index].addFrame(bodyImages[0], 1);
	}

	public void render(Graphics g) {
		int index = 0;
		
		if (successor_ == null) {
			// tail
			if (predecessor_.x_ < x_) {
				// horizontal tail
				index = 8;
			} else if (predecessor_.x_ > x_) {
				index = 9;
			} else {
				// vertical tail
				if (predecessor_.y_ < y_) {
					index = 6;
				} else {
					index = 7;
				}
			}
		} else if (successor_.y_ == predecessor_.y_) {
				// vertical body
				index = 1;
		} else if (successor_.x_ == predecessor_.x_) {
				// horizontal body
				index = 0;
		} else if (successor_.isLeftOf(this)) {
			if (predecessor_.isAbove(this)) {
				index = 2;
			} else {
				index = 4;
			}
		} else if (successor_.isRightOf(this)) {
			if (predecessor_.isAbove(this)) {
				index = 3;
			} else {
				index = 5;
			}
		} else if (successor_.isAbove(this)) {
			if (predecessor_.isLeftOf(this)) {
				index = 2;
			} else {
				index = 3;
			}
		} else if (successor_.isBelow(this)) {
			if (predecessor_.isLeftOf(this)) {
				index = 4;
			} else {
				index = 5;
			}
		}
		
		bodyAnim[index].draw(clipRect_.getX(), clipRect_.getY());
	}
	
	public String toString() {
		return "body";
	}
}
