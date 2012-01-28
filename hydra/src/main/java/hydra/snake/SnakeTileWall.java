package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileWall extends SnakeTile {
	public SnakeTileWall(int x, int y, Rectangle rect) {
		super(x, y, rect);
		
		//System.out.println("create wall at x/y/width/height" + clipRect_.getX() + "/" + clipRect_.getY()+ "/" + clipRect_.getWidth()+ "/" + clipRect_.getHeight());
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(clipRect_.getX(), clipRect_.getY(), clipRect_.getWidth(), clipRect_.getHeight());
		
		g.setColor(new Color(150, 150, 150));
		g.fillRect(clipRect_.getX() + 2, clipRect_.getY() + 2, clipRect_.getWidth() - 4, clipRect_.getHeight() - 4);
	}
	
	public int[] onHeadMovement() {
		SnakeGame.instance_.onCrash();
		return new int[] {x_, y_};
	}
}
