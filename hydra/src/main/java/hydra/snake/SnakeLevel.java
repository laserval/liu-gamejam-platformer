package hydra.snake;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

public class SnakeLevel {
	private static Random rand_ = new Random();

	public static SnakeTileSnakeHead initBasicLevel(SnakeTile[][] tiles, int width, int height, int tileSize, int numFood) {
		// create walls and food
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
				if (y == 0) {
					// Up
					tiles[x][y] = new SnakeTileWallUp(x, y, rect);
					if (x == 0){
						// Up-Left
						tiles[x][y] = new SnakeTileWallLeftTop(x, y, rect);
					}
				} else if(x == 0){
					// Left
					tiles[x][y] = new SnakeTileWallLeft(x, y, rect);
					if (y == (height-1)){
						//Down-Left
						tiles[x][y] = new SnakeTileWallLeftDown(x, y, rect);
					}
				} else if(y == (height - 1)){
					// Down
					tiles[x][y] = new SnakeTileWallDown(x, y, rect);

				} else if(x == (width - 1)){
					// Right
					tiles[x][y] = new SnakeTileWallRight(x, y, rect);
				} else {
					tiles[x][y] = new SnakeTileEmpty(x, y, rect);
				}
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {


				// Right-Top
				if ((y == 0) && (x == (width - 1))){
					Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
					tiles[x][y] = new SnakeTileWallRightTop(x, y, rect);
				}

				// Right-Bottom
				else if ((x == (width - 1)) && (y == (height - 1))){
					Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
					tiles[x][y] = new SnakeTileWallRightDown(x, y, rect);
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
		int y = 5;
		int x = 7;
		SnakeTileSnake last = null;
		for (; x < 15; x++) {
			Rectangle rect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
			SnakeTileSnake current;
			if (x == 10) {
				current = new SnakeTileSnakeBodyJR(x, y, rect, last);
			} else {
				current = new SnakeTileSnakeBody(x, y, rect, last, true);
			}

			if (last != null) {
				last.predecessor_ = current;
			}
			tiles[x][y] = current;
			last = current;
		}


		Rectangle headRect = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
		SnakeTileSnakeHead head = new SnakeTileSnakeHead(x, y, headRect, last);
		tiles[x][y] = head;
		last.predecessor_ = head;
		
		
		for (int i = 0; i < numFood; i++) {
			SnakeGame.instance_.addFoodItem();
		}

		return head;
	}
}
