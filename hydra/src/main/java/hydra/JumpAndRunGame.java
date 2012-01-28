package hydra;

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

public class JumpAndRunGame {
	
	private Vector2f origin_;
	
	private JumpAndRunPlayer player_;
	
	private List<JumpAndRunEntity> entities_;
	
	private Rectangle world_;
	
	private Vector2f gravity_;
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
		
		// Render entities
		for(JumpAndRunEntity entity : entities_) {
			entity.draw(origin_.x, origin_.y);
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
			entity.move(inAir);
			
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
		origin_ = new Vector2f(clip.getX(), clip.getY());
		
		gravity_ = new Vector2f(0.0f, 0.981f);
		
		world_ = new Rectangle(0.0f, 0.0f, clip.getWidth(), clip.getHeight());
		
		
		entities_ = new ArrayList<JumpAndRunEntity>();
		
		Image[] playerImages = new Image[1];
		try {
			playerImages[0] = new Image("square.png");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		
		Animation playerAnim = new Animation(false);
		playerAnim.addFrame(playerImages[0], 1);
		player_ = new JumpAndRunPlayer(playerAnim, new Vector2f(1.0f, 1.0f));
		
		entities_.add(player_);
	}
}
