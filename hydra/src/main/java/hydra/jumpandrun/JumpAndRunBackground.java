package hydra.jumpandrun;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

public class JumpAndRunBackground extends JumpAndRunEntity {
    
    private int offset_ = 0;
    private float movePixelsPerSecond_ = 200;
    private Animation sprite_;
    
    public JumpAndRunBackground(Animation sprite) {
        sprite_ = sprite;
        applyPhysics_ = false;
        solid_ = false;
    }
    
    public String toString() {
        return "background";
    }
    
    public void draw(Rectangle rect) {
		for (float x = rect.getX() - offset_; x < rect.getMaxX(); x += sprite_.getWidth()) {
			sprite_.draw(x, rect.getY());
		}
    }
}
