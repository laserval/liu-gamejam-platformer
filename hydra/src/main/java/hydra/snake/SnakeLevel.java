package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

public class SnakeLevel {
	private static Random rand_ = new Random();
	
	public static SnakeTileSnakeHead initBasicLevel(SnakeTile[][] tiles, int width, int height, int tileSize) {
		// create walls and food
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
				if (y == 0 || x == 0 || y == (height-1) || x == (width - 1)) {
					tiles[x][y] = new SnakeTileWall(x, y, rect);
				} else if (rand_.nextInt(100) == 27) {
					tiles[x][y] = new SnakeTileFood(x, y, rect);
				}
			}
		}
		
		// create snake
		int y = height/2 - 1;
		int x = width/2 - 5;
		SnakeTileSnakeBody last = null;
		for (; x < width/2 + 4; x++) {
			Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
			tiles[x][y] = last = new SnakeTileSnakeBody(x, y, rect, last);
		}
		
		Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
		SnakeTileSnakeHead head = new SnakeTileSnakeHead(x, y, rect, last);
		tiles[x][y] = head;
		
		return head;
	}
}
