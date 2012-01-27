package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

public class SnakeLevel {
	private static final int TILE_SIZE = 10;
	
	private int width_;
	private int height_;
	
	private SnakeTile[] tiles_;
	
	private Random rand_ = new Random();
	
	public SnakeLevel(int canvasWidth, int canvasHeight) {
		width_ = canvasWidth / TILE_SIZE;
		height_ = canvasHeight / TILE_SIZE;
		
		tiles_ = new SnakeTile[width_ * height_];
		for (int y = 0; y < height_; y++) {
			for (int x = 0; x < width_; x++) {
				Rectangle rect = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				if (y == 0 || x == 0 || y == (height_-1) || x == (width_ - 1)) {
					tiles_[y * width_ + x] = new SnakeTileWall(rect);
				} else if (rand_.nextInt(100) == 27) {
					tiles_[y * width_ + x] = new SnakeTileFood(rect);
				} else {
					tiles_[y * width_ + x] = new SnakeTileEmpty(rect);
				}
			}
		}
		
		
	}
	
	//public abstract void initLevel();
	//public abstract void ini
	
	public void render(Graphics g, Rectangle clip) {
		for (int y = 0; y < height_; y++) {
			for (int x = 0; x < width_; x++) {
				tiles_[y * width_ + x].render(g);
			}
		}
	}
}
