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
		
		// create teleporters
		Rectangle teleRect1 = new Rectangle(5 * tileSize, 5 * tileSize, tileSize, tileSize);
		SnakeTileTeleporter tele1 = new SnakeTileTeleporter(5, 5, teleRect1);
		tiles[5][5] = tele1;
		
		Rectangle teleRect2 = new Rectangle(20 * tileSize, 5 * tileSize, tileSize, tileSize);
		SnakeTileTeleporter tele2 = new SnakeTileTeleporter(20, 5, teleRect2);
		tiles[20][5] = tele2;
		
		tele1.link(tele2);
		
		Rectangle teleRect3 = new Rectangle(5 * tileSize, 6 * tileSize, tileSize, tileSize);
		SnakeTileTeleporter tele3 = new SnakeTileTeleporter(5, 6, teleRect3);
		tiles[5][6] = tele3;
		
		Rectangle teleRect4 = new Rectangle(40 * tileSize, 15 * tileSize, tileSize, tileSize);
		SnakeTileTeleporter tele4 = new SnakeTileTeleporter(40, 15, teleRect4);
		tiles[40][15] = tele4;
		
		tele3.link(tele4);
		
		
		// create snake
		int y = height/2 - 1;
		int x = width/2 - 5;
		SnakeTileSnakeBody last = null;
		for (; x < width/2 + 4; x++) {
			Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
			tiles[x][y] = last = new SnakeTileSnakeBody(x, y, rect, last);
		}
		
		Rectangle headRect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
		SnakeTileSnakeHead head = new SnakeTileSnakeHead(x, y, headRect, last);
		tiles[x][y] = head;
		
		return head;
	}
}
