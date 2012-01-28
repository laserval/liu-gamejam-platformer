package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SnakeTileSnakeBodyJR extends SnakeTileSnakeBody {
	public SnakeTileSnakeBodyJR(int x, int y, Rectangle rect, SnakeTileSnake successor) {
		super(x, y, rect, successor, false);
		
		loadAnimation(0, "snake_tile_vertical_spot.jpg");
		loadAnimation(1, "snake_tile_horizontal_spot.jpg");
		loadAnimation(2, "snake_tile.jpg");
		loadAnimation(3, "snake_tile.jpg");
		loadAnimation(4, "snake_tile.jpg");
		loadAnimation(5, "snake_tile.jpg");
		loadAnimation(6, "snake_tile.jpg");
		loadAnimation(7, "snake_tile.jpg");
		loadAnimation(8, "snake_tile.jpg");
		loadAnimation(9, "snake_tile.jpg");
	}
	
	
	public String toString() {
		return "body J&R";
	}
}
