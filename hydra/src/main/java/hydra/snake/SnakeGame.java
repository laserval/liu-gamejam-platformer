package hydra.snake;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.Input;

import hydra.BaseSubGame;

public class SnakeGame implements BaseSubGame {
	public static SnakeGame instance_;
	
	private static final int TILE_SIZE = 10;
	
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final int DIRECTION_UP = 3;
	public static final int DIRECTION_DOWN = 4;
	
	private int width_;
	private int height_;
	
	private SnakeTile[][] tiles_;
	private SnakeTileSnakeHead snakeHead_;
	
	private int moveTimer_;
	private int moveInterval_ = 200;
	
	
	private int currentDirection = DIRECTION_RIGHT;
	
	
	public SnakeGame() {
		instance_ = this;
	}
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
		for (int y = 0; y < height_; y++) {
			for (int x = 0; x < width_; x++) {
				if (tiles_[x][y] != null) {
					tiles_[x][y].render(g);
				}
			}
		}
	}
	
	public void update(GameContainer gc, int delta) {
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			currentDirection = DIRECTION_RIGHT;
		} else if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			currentDirection = DIRECTION_LEFT;
		} else if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			currentDirection = DIRECTION_UP;
		} else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			currentDirection = DIRECTION_DOWN;
		}
		
		moveTimer_ -= delta;
		if (moveTimer_ <= 0) {
			moveTimer_ = moveInterval_;
			
			// move snake
			snakeHead_.move(currentDirection); // moves the body recursively
		}
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		width_ = (int)clip.getWidth() / TILE_SIZE;
		height_ = (int)clip.getHeight() / TILE_SIZE;
		
		tiles_ = new SnakeTile[width_][height_];
		
		snakeHead_ = SnakeLevel.initBasicLevel(tiles_, width_, height_, TILE_SIZE);
	}
	
	public void moveTile(SnakeTile tile, int x, int y) {
		tiles_[x][y] = tile;
		tile.moveTo(x, y, new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE));
	}
	
	public void clearTile(int x, int y) {
		tiles_[x][y] = null;
	}
}
