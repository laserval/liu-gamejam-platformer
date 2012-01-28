package hydra.snake;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.Input;

import hydra.BaseSubGame;
import hydra.App;

public class SnakeGame implements BaseSubGame {
	public static SnakeGame instance_;
	
	private static final int TILE_SIZE = 20;
	
	public static final int DIRECTION_LEFT = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_UP = 2;
	public static final int DIRECTION_DOWN = 3;
	
	private int width_;
	private int height_;
	
	private SnakeTile[][] tiles_;
	private SnakeTileSnakeHead snakeHead_;
	
	private int moveTimer_;
	private int moveInterval_ = 100; // 100 is good
	
	private int snakeGrowth_;
	private boolean jrMovePossible_ = true;
	
	public int currentDirection_ = DIRECTION_RIGHT;
	public int lastDirection_ = DIRECTION_RIGHT;
	
	
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
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT) && lastDirection_ != DIRECTION_LEFT) {
			currentDirection_ = DIRECTION_RIGHT;
		} else if (gc.getInput().isKeyDown(Input.KEY_LEFT) && lastDirection_ != DIRECTION_RIGHT) {
			currentDirection_ = DIRECTION_LEFT;
		} else if (gc.getInput().isKeyDown(Input.KEY_UP) && lastDirection_ != DIRECTION_DOWN) {
			currentDirection_ = DIRECTION_UP;
		} else if (gc.getInput().isKeyDown(Input.KEY_DOWN) && lastDirection_ != DIRECTION_UP) {
			currentDirection_ = DIRECTION_DOWN;
		}
		
		if (jrMovePossible_) {
			if (gc.getInput().isKeyDown(Input.KEY_B)) {
				moveJRBackward();
				jrMovePossible_ = false;
			} else if (gc.getInput().isKeyDown(Input.KEY_N)) {
				moveJRForward();
				jrMovePossible_ = false;
			}
		}
		
		moveTimer_ -= delta;
		if (moveTimer_ <= 0) {
			moveTimer_ = moveInterval_;
			jrMovePossible_ = true;
			
			// move snake
			snakeHead_.move(currentDirection_); // moves the body recursively
			lastDirection_ = currentDirection_;
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
	
	public void moveHead(int x, int y) {
		// check events on the next tile
		if (tiles_[x][y] != null) {
			int[] nextPosition = tiles_[x][y].onHeadMovement();
			if (nextPosition[0] != x || nextPosition[1] != y) {
				// check modified tile for events
				moveHead(nextPosition[0], nextPosition[1]);
			} else {
				moveTile(snakeHead_, nextPosition[0], nextPosition[1]);
			}
		} else {
			moveTile(snakeHead_, x, y);
		}
	}
	
	public void clearTile(int x, int y) {
		tiles_[x][y] = new SnakeTileEmpty(x, y, new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE));
	}
	
	public void growSnake(int length) {
		snakeGrowth_ += length;
	}
	
	public boolean shouldSnakeGrow() {
		return snakeGrowth_ > 0;
	}
	
	public void onSnakeGrown() {
		snakeGrowth_--;
	}
	
	
	public void onSnakeEatSelf(int x, int y) {
		App.instance_.gameOver(false);
	}
	
	public void onCrash() {
		App.instance_.gameOver(false);
	}
	
	
	public void moveJRForward() {
		SnakeTileSnake curPart = snakeHead_;
		while (curPart != null && !(curPart instanceof SnakeTileSnakeBodyJR)) {
			curPart = curPart.successor_;
		}
		
		if (curPart == null) {
			System.err.println("J&R snake body part not found!");
			System.exit(1);
		}
		
		SnakeTileSnake predec = curPart.predecessor_;
		if (predec instanceof SnakeTileSnakeHead) {
			System.out.println("J&R player reached snake head!");
			App.instance_.gameOver(false);
		} else {
			switchBodyParts(predec, curPart);
		}
	}
	
	public void moveJRBackward() {
		SnakeTileSnake curPart = snakeHead_;
		while (curPart != null) {
			curPart = curPart.successor_;
			
			if (curPart instanceof SnakeTileSnakeBodyJR) {
				break;
			}
		}
		
		if (curPart == null) {
			System.err.println("J&R snake body part not found!");
			System.exit(1);
		} else {
		}
		
		SnakeTileSnake succ = curPart.successor_;
		if (succ == null || succ.successor_ == null) {
			System.out.println("J&R player reached snake ass!");
			App.instance_.gameOver(true);
		} else {
			switchBodyParts(curPart, succ);
		}
	}
	
	public void switchBodyParts(SnakeTileSnake front, SnakeTileSnake back) {
		front.predecessor_.successor_ = back;
		
		if (back.successor_ != null) {
			back.successor_.predecessor_ = front;
		}
		
		SnakeTileSnake tmp = back.successor_;
		back.successor_ = front;
		back.predecessor_ = front.predecessor_;
		
		front.predecessor_ = back;
		front.successor_ = tmp;
		
		int tmpX = front.x_;
		int tmpY = front.y_;
		moveTile(front, back.x_, back.y_);
		moveTile(back, tmpX, tmpY);
	}
}
