package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public abstract class SnakeTile {
	public Rectangle clipRect_;
	public int x_;
	public int y_;

	public SnakeTile(int x, int y, Rectangle clipRect) {
		moveTo(x, y, clipRect);
	}

	public void moveTo(SnakeTile other) {
		moveTo(other.x_, other.y_, other.clipRect_);
	}

	public void moveTo(int x, int y, Rectangle clipRect) {
		clipRect_ = clipRect;
		x_ = x;
		y_ = y;
	}

	public abstract void render(Graphics g);

	public abstract int[] onHeadMovement();
	
	public abstract String toString();
	
	public boolean isLeftOf(SnakeTile other) {
		return other.x_ > x_;
	}
	
	public boolean isRightOf(SnakeTile other) {
		return other.x_ < x_;
	}
	
	public boolean isAbove(SnakeTile other) {
		return other.y_ > y_;
	}
	
	public boolean isBelow(SnakeTile other) {
		return other.y_ < y_;
	}
}
