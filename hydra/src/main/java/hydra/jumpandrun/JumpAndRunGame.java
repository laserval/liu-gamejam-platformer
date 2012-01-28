package hydra.jumpandrun;

import java.util.List;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Sound;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;


import hydra.App;
import hydra.BaseSubGame;
import hydra.snake.SnakeGame;

public class JumpAndRunGame implements BaseSubGame {
	public static JumpAndRunGame instance_;
	
	private JumpAndRunPlayer player_;
	
	private List<JumpAndRunEntity> entities_;
	
	private Rectangle world_;
	
	private Vector2f gravity_;
	private float friction_ = 0.007f;
	
	static Sound jumpfx;
	private boolean soundPlaying = false;
	
	public float scrollPixelsPerSecond_ = 150;
	private float scrollPixelOverlap_ = 0;
	
	private int freezePhysicsTimeout_ = 0;
	private int freezePhysicsInterval_ = 1000;
	public boolean reverseScrolling_ = false;
	
	
	public JumpAndRunGame() {
		instance_ = this;
	}
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
		
		// Render entities
		for(JumpAndRunEntity entity : entities_) {
			entity.draw(g, clip);
		}
		
		player_.draw(g, clip);
	}
	
	public void update(GameContainer gc, int delta) {
		
		if (freezePhysicsTimeout_ <= 0) {
			// Controls
			Input input = gc.getInput();
			player_.resetMoves();

			if (input.isKeyPressed(Input.KEY_W)) {
				if (soundPlaying == false){
					jumpfx.play(1, 3);
					soundPlaying = true;
				}
				player_.jump();
			}
			if (!input.isKeyPressed(input.KEY_W)) {
				soundPlaying = false;
			}
			if (input.isKeyDown(Input.KEY_A)) {
				player_.left();
			}
			if (input.isKeyDown(Input.KEY_D)) {
				player_.right();
			}
		}
		
		
		scrollPixelOverlap_ += scrollPixelsPerSecond_ * (delta / 1000.f);;
		int curScroll = (int)scrollPixelOverlap_;
		scrollPixelOverlap_ -= curScroll;
		
		ArrayList<JumpAndRunEntity> deleteList = new ArrayList();
		
		// scrolling
		for(JumpAndRunEntity entity : entities_) {
			if (entity.pos_ != null && entity.pos_.x < -1000) {
				deleteList.add(entity);
				continue;
			}
			
			if (entity.applyScrolling()) {
				// scrolling
				// TODO: Manage collisions here
				Vector2f newScrollPos = entity.getPosition();
				if (reverseScrolling_) {
					newScrollPos.x += curScroll;
				} else {
					newScrollPos.x -= curScroll;
				}
				entity.setPosition(newScrollPos);
			}
			
			entity.update(delta);
		}
		
		for (JumpAndRunEntity entity : deleteList) {
			entities_.remove(entity);
		}
		
		if (freezePhysicsTimeout_ > 0) {
			freezePhysicsTimeout_ -= delta;
			if (freezePhysicsTimeout_ <= 0) {
				freezePhysicsTimeout_ = 0;
				reverseScrolling_ = false;
				scrollPixelsPerSecond_ /= 4;
			}
			
			return;
		}
			
		Vector2f startPos = player_.getPosition();
		
		boolean inAir = player_.getPosition().y < world_.getY() + world_.getHeight();
		
		if (inAir) {
			// Apply gravity if in air
			player_.impulse(gravity_, delta);
			//System.out.println("in air");
		}
		else {
			//System.out.println("on ground");
			// Apply friction if on ground
			player_.friction(friction_, delta);
		}
		// Update animation
		player_.updateSprite(inAir, delta);
		// Move
		player_.move(inAir, delta);
		
		// Find collisions for this entity
		for(JumpAndRunEntity otherEntity : entities_) {
			if (!player_.equals(otherEntity)) {
				// System.out.println("Checking collisions between player and " + otherEntity);
				if (player_.collidesWith(otherEntity)) {
					System.out.println("Collision!");
					handleCollision(player_, otherEntity);
				}
			}
		}
		
		// Check if outside world boundaries
		if (!world_.contains(player_.getPosition().x, player_.getPosition().y)) {
			//System.out.println("outside");
			
			// if player reached one of the sides, move lump inside snake
			if (player_.getPosition().x < world_.getX()) {
				// left side
				SnakeGame.instance_.moveJRBackward();
				freezePhysicsTimeout_ = freezePhysicsInterval_;
				reverseScrolling_ = true;
				scrollPixelsPerSecond_ *= 4;
			} else if (player_.getPosition().x > world_.getX() + world_.getWidth()) {
				SnakeGame.instance_.moveJRForward();
				freezePhysicsTimeout_ = freezePhysicsInterval_;
				scrollPixelsPerSecond_ *= 4;
			}
			
			// Put at closest position inside boundary
			Vector2f newPos = new Vector2f(player_.getPosition());
			Vector2f newSpeed = new Vector2f(player_.getSpeed());
			
			// can't fall through the ground
			if (player_.getPosition().y > world_.getY() + world_.getHeight()) {
				newPos.y = world_.getY() + world_.getHeight();
				newSpeed.y = 0.0f;
			}
			player_.setPosition(newPos);
			player_.setSpeed(newSpeed);
		}
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		try {
			jumpfx = new Sound("Jump.ogg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		
		gravity_ = new Vector2f(0.0f, 1000.0f);
		
		world_ = new Rectangle(10.0f, 10.0f, clip.getWidth() - 20.0f, clip.getHeight() - 20.0f);
		
		entities_ = new ArrayList<JumpAndRunEntity>();
		
		// Load background for Jumper
		Image[] backgroundImages = new Image[1];
		try {
			backgroundImages[0] = new Image("inside.jpg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		
		Animation backgroundAnim = new Animation(false);
		backgroundAnim.addFrame(backgroundImages[0], 1);
		JumpAndRunBackground background = new JumpAndRunBackground(backgroundAnim);
		entities_.add(background);
		
		
		// Load player
		SpriteSheet playerSpriteSheet;
		try {
			playerSpriteSheet = new SpriteSheet(new Image("apple_sprite.png"), 69, 90);
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		
		Animation playerAnim = new Animation(playerSpriteSheet, 1000);
		player_ = new JumpAndRunPlayer(playerAnim, new Vector2f(400.0f, 150.0f));
		
		entities_.add(player_);
		
		// add barrier
		JumpAndRunEntityInvisibleFixedBarrier barrier = new JumpAndRunEntityInvisibleFixedBarrier(
				new Vector2f(world_.getX() + world_.getWidth()/2, world_.getY() - 5), 
				new Rectangle(0, 0, world_.getWidth(), 20));
		entities_.add(barrier);
	}
	
	private void handleCollision(JumpAndRunPlayer player, JumpAndRunEntity other) {
		if (!other.deadly_) {
			// disentangle
			// check on which side the player should be shoved
			if (other.collisionMask_.contains(player.collisionMask_.getMinX(), player.collisionMask_.getMinY()) && 
					other.collisionMask_.contains(player.collisionMask_.getMaxX(), player.collisionMask_.getMinY())) {
				// player jumped into this obstacle from below
				//System.out.println("jumped in from below");
				player.pos_.y += (4 + other.collisionMask_.getMaxY() - player.collisionMask_.getMinY());
			} else if (other.collisionMask_.contains(player.collisionMask_.getMinX(), player.collisionMask_.getMaxY()) && 
					other.collisionMask_.contains(player.collisionMask_.getMaxX(), player.collisionMask_.getMaxY())) {
				// player landed squarely on top of this obstacle
				//System.out.println("landed on top of it");
				player.pos_.y -= (player.collisionMask_.getMaxY() - other.collisionMask_.getMinY());
			} else if (other.collisionMask_.contains(player.collisionMask_.getMinX(), player.collisionMask_.getMinY()) && 
					other.collisionMask_.contains(player.collisionMask_.getMinX(), player.collisionMask_.getMaxY())) {
				// player ran into an obstacle from the right side
				//System.out.println("from the right side");
				player.pos_.x += (5 + other.collisionMask_.getMaxX() - player.collisionMask_.getMinX());
			} else if (other.collisionMask_.contains(player.collisionMask_.getMaxX(), player.collisionMask_.getMinY()) && 
					other.collisionMask_.contains(player.collisionMask_.getMaxX(), player.collisionMask_.getMaxY())) {
				// player ran into obstacle from the left side
				//System.out.println("from the left side");
				player.pos_.x -= (player.collisionMask_.getMaxX() - other.collisionMask_.getMinX());
			}
			
			player_.speed_.set(0, 0);
		} else {
			System.out.println("Jump&Run player dies because of collision with obstacle");
			SnakeGame.instance_.moveJRBackward();
			player_.pos_.x += (10 + other.collisionMask_.getMaxX() - player.collisionMask_.getMinX());
		}
	}
	
	public void spawnObject(String type) {
		JumpAndRunObstacle obstacle = new JumpAndRunObstacle(new Vector2f(1000, world_.getHeight() + 5), type);
		entities_.add(obstacle);
	}
}
