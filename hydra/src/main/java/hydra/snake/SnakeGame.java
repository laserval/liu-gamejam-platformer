package hydra.snake;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;

import hydra.BaseSubGame;
import hydra.App;
import hydra.jumpandrun.JumpAndRunGame;

public class SnakeGame implements BaseSubGame {
	public static SnakeGame instance_;
	
	private static final int TILE_SIZE = 20;
	
	public static final int DIRECTION_LEFT = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_UP = 2;
	public static final int DIRECTION_DOWN = 3;
	
	public static final int NUM_FOOD = 4;
	
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
	
	private Music backgroundMusic;
	
	Sound fallBackSound_;
	Sound advanceSound_;
	
	Random rand = new Random();
	
	public SnakeGame() {
		instance_ = this;
		try {
			backgroundMusic = new Music("Intesties.ogg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		
		try {
			fallBackSound_ = new Sound("ScreamShort.ogg");
		} catch(SlickException e) {
			System.out.println(e);
		}
		
		try {
			advanceSound_ = new Sound("InsaneLaughShort.ogg");
		} catch(SlickException e) {
			System.out.println(e);
		}
		
		backgroundMusic.loop();
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
		
		snakeHead_ = SnakeLevel.initBasicLevel(tiles_, width_, height_, TILE_SIZE, NUM_FOOD);
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
	
	public void growSnake(int length, String type) {
		snakeGrowth_ += length;
		JumpAndRunGame.instance_.spawnObject(type);
		
		addFoodItem();
	}
	
	public void addFoodItem() {
		int maxtries = (width_ * height_) * 2;
		for (int i = 0; i < maxtries; ++i) {
			int x = rand.nextInt(width_);
			int y = rand.nextInt(height_);
			
			if (tiles_[x][y] instanceof SnakeTileEmpty) {
				Rectangle rect = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				switch (rand.nextInt(6)) {
				case 0:
					tiles_[x][y] = new SnakeTileFood(x, y, rect);
					break;
				case 1:
					tiles_[x][y] = new SnakeTileRat(x, y, rect);
					break;
				default:
					tiles_[x][y] = new SnakeTileChili(x, y, rect);
					break;
				}
				
				break;
			}
		}
	}
	
	public boolean shouldSnakeGrowFront() {
		return snakeGrowth_ == 1;
	}
	
	public boolean shouldSnakeGrowBack() {
		return snakeGrowth_ == 2;
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
		advanceSound_.play();
		SnakeTileSnake curPart = snakeHead_;
		while (curPart != null && !(curPart instanceof SnakeTileSnakeBodyJR)) {
			curPart = curPart.successor_;
		}
		
		if (curPart == null) {
			System.err.println("J&R snake body part not found!");
			System.exit(1);
		}
		
		int successors = countSuccessors(curPart);
		int predecessors = countPredecessors(curPart);
		//System.out.println("before: succ: " + successors + " predec: " + predecessors);
		
		int moveForward = (int)(predecessors / 2.f + 0.5f); 
		if (moveForward <= 0) {
			moveForward = 1;
		}
		
		SnakeTileSnake pre = curPart; 
		for (int i = 0; i < moveForward && pre != null; ++i) {
			pre = pre.predecessor_;
		}
		
		if (pre == null || pre instanceof SnakeTileSnakeHead) {
			System.out.println("J&R player reached snake head!");
			App.instance_.gameOver(false);
		} else {
			switchBodyParts(pre, curPart);
		}
		
		//System.out.println("after: succ: " + countSuccessors(curPart) + " predec: " + countPredecessors(curPart));
	}
	
	public void moveJRBackward() {
		fallBackSound_.play();
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
		
		int successors = countSuccessors(curPart);
		int predecessors = countPredecessors(curPart);
		//System.out.println("before: succ: " + successors + " predec: " + predecessors);
		
		int moveBack = (int)((successors + predecessors) / 4.f + 0.5f); 
		if (moveBack <= 0) {
			moveBack = 1;
		}
		
		SnakeTileSnake succ = curPart; 
		for (int i = 0; i < moveBack && succ != null; ++i) {
			succ = succ.successor_;
		}
		
		if (succ == null || succ.successor_ == null) {
			System.out.println("J&R player reached snake ass!");
			App.instance_.gameOver(true);
		} else {
			switchBodyParts(curPart, succ);
		}
		
		//System.out.println("after: succ: " + countSuccessors(curPart) + " predec: " + countPredecessors(curPart));
	}
	
	public void switchBodyParts(SnakeTileSnake front, SnakeTileSnake back) {
		// special case if they are adjacent
		if (front.successor_ == back) {
			front.predecessor_.successor_ = back;
		
			if (back.successor_ != null) {
				back.successor_.predecessor_ = front;
			}
			
			SnakeTileSnake tmp = back.successor_;
			back.successor_ = front;
			back.predecessor_ = front.predecessor_;
			
			front.predecessor_ = back;
			front.successor_ = tmp;
		} else {
			SnakeTileSnake frontSuc = front.successor_;
			SnakeTileSnake frontPre = front.predecessor_;
			SnakeTileSnake backSuc = back.successor_;
			SnakeTileSnake backPre = back.predecessor_;
			
			
			front.successor_ = backSuc;
			if (front.successor_ != null) {
				front.successor_.predecessor_ = front;
			}
			front.predecessor_ = backPre;
			front.predecessor_.successor_ = front;
			
			
			back.successor_ = frontSuc;
			if (back.successor_ != null) {
				back.successor_.predecessor_ = back;
			}
			back.predecessor_ = frontPre;
			back.predecessor_.successor_ = back;
		}
		
		
		int tmpX = front.x_;
		int tmpY = front.y_;
		moveTile(front, back.x_, back.y_);
		moveTile(back, tmpX, tmpY);
	}
	
	public int countSuccessors(SnakeTileSnake origin) {
		int count = -1; // tail does not count
		while (origin.successor_ != null) {
			origin = origin.successor_;
			count += 1;
		}
		return count;
	}
	
	public int countPredecessors(SnakeTileSnake origin) {
		int count = -1; // head does not count
		while (origin.predecessor_ != null) {
			origin = origin.predecessor_;
			count += 1;
		}
		return count;
	}
}
