package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileTeleporter extends SnakeTile {
	private SnakeTileTeleporter other_;
	
	public SnakeTileTeleporter(int x, int y, Rectangle rect) {
		super(x, y, rect);
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(220, 0, 0));
		g.fillRect(clipRect_.getX(), clipRect_.getY(), clipRect_.getWidth(), clipRect_.getHeight());
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
