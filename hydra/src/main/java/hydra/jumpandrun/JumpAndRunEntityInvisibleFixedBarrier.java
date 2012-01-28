
package hydra.jumpandrun;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class JumpAndRunEntityInvisibleFixedBarrier extends JumpAndRunEntity {
    public JumpAndRunEntityInvisibleFixedBarrier(Vector2f pos, Rectangle bounds) {
		applyPhysics_ = false;
		applyScrolling_ = false;
		pos_ = pos;
		collisionMask_ = bounds;
		deadly_ = false;
    }
    
    public String toString() {
        return "invisible, fixed barrier";
    }
    
    public void draw(Graphics g, Rectangle rect) {
		g.setColor(new Color(0, 255, 0, 100));
		
		g.fillRect(rect.getX() + pos_.x - getWidth()/2.0f, 
                    rect.getY() + pos_.y - getHeight(), 
                    collisionMask_.getWidth(), 
                    collisionMask_.getHeight());
    }
	
	public float getWidth() {
		return collisionMask_.getWidth();
	}
	
	public float getHeight() {
		return collisionMask_.getHeight();
	}
}	
