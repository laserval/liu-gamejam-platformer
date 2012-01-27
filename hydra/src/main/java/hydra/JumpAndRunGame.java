package hydra;

import java.util.List;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

import org.newdawn.slick.Animation;

public class JumpAndRunGame {
	
	private Vector2f origin_;
	
	private JumpAndRunPlayer player_;
	
	private List<JumpAndRunEntity> entities_;
	
	private Vector2f gravity_;
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
		
		
		
	}
	
	public void update(GameContainer gc, int delta) {
		
		for(JumpAndRunEntity entity : entities_) {
			System.out.println(entity);
		}
		
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		origin_ = new Vector2f(clip.getX(), clip.getY());
		
		gravity_ = new Vector2f(0.0f, 0.981f);
		
		
		entities_ = new ArrayList<JumpAndRunEntity>();
		
		
		Animation playerAnim = new Animation();
		player_ = new JumpAndRunPlayer(playerAnim, new Vector2f(0.0f, 0.0f));
		
		entities_.add(player_);
	}
}
