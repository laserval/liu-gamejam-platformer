package hydra.jumpandrun;

import java.util.List;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;


import hydra.BaseSubGame;

public class JumpAndRunGame implements BaseSubGame {
	private JumpAndRunPlayer player_;
	
	private List<JumpAndRunEntity> entities_;
	
	private Rectangle world_;
	
	private Vector2f gravity_;
	
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
			
			boolean inAir = world_.getHeight() > entity.getPosition().y + entity.getHeight();
			
			if (inAir) {
				// Apply gravity if in air
				entity.impulse(gravity_);
			}
			else {
				// Apply friction if on ground
				entity.friction(0.1f);
			}
			// Move
			entity.move(inAir, delta);
			
			// Check if inside world boundaries
			if (world_.contains(entity.getPosition().x + entity.getWidth()/2.0f, 
				entity.getPosition().y + entity.getHeight())) {
					// it is!
			}
			else {
				entity.setPosition(startPos.x, startPos.y);
				entity.setSpeed(0.0f, 0.0f);
				
			}
		}
		
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		gravity_ = new Vector2f(0.0f, 0.981f);
		
		world_ = new Rectangle(0.0f, 0.0f, clip.getWidth(), clip.getHeight());
		
		entities_ = new ArrayList<JumpAndRunEntity>();
		
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
		
		
		Image[] playerImages = new Image[1];
		try {
			playerImages[0] = new Image("square.png");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		
		Animation playerAnim = new Animation(false);
		playerAnim.addFrame(playerImages[0], 1);
		player_ = new JumpAndRunPlayer(playerAnim, new Vector2f(100.0f, 1.0f));
		
		entities_.add(player_);
	}
}
