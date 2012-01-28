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


import hydra.BaseSubGame;

public class JumpAndRunGame implements BaseSubGame {
	private JumpAndRunPlayer player_;
	
	private List<JumpAndRunEntity> entities_;
	
	private Rectangle world_;
	
	private Vector2f gravity_;
	private float friction_ = 0.007f;
	
	static Sound jumpfx;
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
		
		// Render entities
		for(JumpAndRunEntity entity : entities_) {
			entity.draw(clip);
		}
		
	}
	
	public void update(GameContainer gc, int delta) {
		
		// Controls
		Input input = gc.getInput();
		player_.resetMoves();

		if (input.isKeyDown(Input.KEY_W)) {
			/*
			if(jumpfx.playing() == false){
				jumpfx.play();
			}
			*/
		if (input.isKeyPressed(Input.KEY_W)) {
			player_.jump();
		}
		if (input.isKeyDown(Input.KEY_A)) {
			player_.left();
		}
		if (input.isKeyDown(Input.KEY_D)) {
			player_.right();
		}
		// Physics
		for(JumpAndRunEntity entity : entities_) {
			if (!entity.applyPhysics()) {
				continue;
			}
			
			Vector2f startPos = entity.getPosition();
			
			boolean inAir = entity.getPosition().y < world_.getY() + world_.getHeight();
			
			if (inAir) {
				// Apply gravity if in air
				entity.impulse(gravity_, delta);
				System.out.println("in air");
			}
			else {
				System.out.println("on ground");
				// Apply friction if on ground
				entity.friction(friction_, delta);
			}
			// Update animation
			entity.updateSprite(inAir, delta);
			// Move
			entity.move(inAir, delta);
			
			// Find collisions for this entity
			for(JumpAndRunEntity otherEntity : entities_) {
				if (!entity.equals(otherEntity)
					&& otherEntity.isSolid()) {
					System.out.println("Checking collisions with " + otherEntity);
					if (entity.collidesWith(otherEntity)) {
						// Move out of collision
						entity.setPosition(startPos.x, startPos.y);
						entity.setSpeed(0.0f, 0.0f);
					}
				}
			}
			
			// Check if outside world boundaries
			if (!world_.contains(entity.getPosition().x, entity.getPosition().y)) {
				System.out.println("outside");
				// Put at closest position inside boundary
				entity.setPosition(entity.getPosition().x, world_.getY() + world_.getHeight());
				entity.multiplySpeed(1.0f, 0.0f);
			}
		}
		
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		try {
			jumpfx = new Sound("SnakeEat.ogg");
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
		player_ = new JumpAndRunPlayer(playerAnim, new Vector2f(100.0f, 20.0f));
		
		entities_.add(player_);
	}
}
