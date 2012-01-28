package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileTeleporter extends SnakeTile {
	private SnakeTileTeleporter other_;
	Image[] teleportImages = new Image[1];
	Animation teleportAnim = new Animation(false);
	
	public SnakeTileTeleporter(int x, int y, Rectangle rect) {
		super(x, y, rect);
		
		try {
			teleportImages[0] = new Image("portal.jpg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}

		teleportAnim.addFrame(teleportImages[0], 1);
	}
	
	public void render(Graphics g) {
		teleportAnim.draw(clipRect_.getX(), clipRect_.getY());
	}
	
	public int[] onHeadMovement() {
		switch (SnakeGame.instance_.currentDirection_) {
		default:
		case SnakeGame.DIRECTION_RIGHT:
			return new int[] {other_.x_+1, other_.y_};
			
		case SnakeGame.DIRECTION_LEFT:
			return new int[] {other_.x_-1, other_.y_};
			
		case SnakeGame.DIRECTION_UP:
			return new int[] {other_.x_, other_.y_-1};
			
		case SnakeGame.DIRECTION_DOWN:
			return new int[] {other_.x_, other_.y_+1};
		}
	}
	
	public void link(SnakeTileTeleporter tele) {
		other_ = tele;
		tele.other_ = this;
	}
	
	public String toString() {
		return "teleporter";
	}
}
