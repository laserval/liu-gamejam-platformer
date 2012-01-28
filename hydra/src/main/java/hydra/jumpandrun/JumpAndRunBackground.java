package hydra.jumpandrun;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class JumpAndRunBackground extends JumpAndRunEntity {
    
    private float offset_ = 0;
    private Animation sprite_;
    
    public JumpAndRunBackground(Animation sprite) {
        sprite_ = sprite;
        applyPhysics_ = false;
        solid_ = false;
        applyScrolling_ = false;
    }
    
    public String toString() {
        return "background";
    }
    
    public void draw(Graphics g, Rectangle rect) {
		for (float x = rect.getX() - offset_; x < rect.getMaxX(); x += sprite_.getWidth()) {
			sprite_.draw(x, rect.getY());
		}
    }
    
    public void update(int delta) {
		if (JumpAndRunGame.instance_.reverseScrolling_) {
			offset_ -= JumpAndRunGame.instance_.scrollPixelsPerSecond_ * (delta / 1000.f);
			if (offset_ <= sprite_.getWidth()) {
				offset_ += sprite_.getWidth();
			}
		} else {
			offset_ += JumpAndRunGame.instance_.scrollPixelsPerSecond_ * (delta / 1000.f);
			if (offset_ >= sprite_.getWidth()) {
				offset_ -= sprite_.getWidth();
			}
		}
	}
}
