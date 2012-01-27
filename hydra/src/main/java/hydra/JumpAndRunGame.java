package hydra;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

public class JumpAndRunGame {
	
	private Vector2f origin_;
	
	private JumpAndRunPlayer player_;
	
	private JumpAndRunEntity entities_;
	
	private Vector2f gravity_;
	
	public void render(GameContainer gc, Graphics g, Rectangle clip) {
		
		
		
	}
	
	public void update(GameContainer gc, int delta) {
		
		
		
	}
	
	public void init(GameContainer gc, Rectangle clip) {
		origin_ = new Vector2f(clip.x, clip.y);
		
		gravity_ = new Vector2f(0.0, 0.981);
	}
}
