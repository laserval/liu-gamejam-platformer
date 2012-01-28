package hydra.jumpandrun;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

public abstract class JumpAndRunEntity {

    protected Animation sprite_;
    
    protected Vector2f pos_;

    protected Vector2f speed_;
    
    protected Vector2f acc_;
    
    protected boolean applyPhysics_ = true;
    
    
    public float getWidth() {
        return sprite_.getWidth();
    }
    
    public float getHeight() {
        return sprite_.getHeight();
    }
    
    public Vector2f getPosition() {
        return pos_;
    }
    
    public void setPosition(float x, float y) {
        pos_.set(x, y);
    }
    
    public void setSpeed(float x, float y) {
        speed_.set(x, y);
    }
    
    public boolean applyPhysics() {
		return applyPhysics_;
	}
    
    public void friction(float f) {
        if (speed_.length() < 0.05f) {
            speed_.set(0.0f, 0.0f);
        }
        else {
            speed_.scale(1.0f - f);
        }
    }
    
    public void impulse(Vector2f v) {
        acc_.add(v);
    }
    
    public void move(boolean inAir, int delta) {
        speed_.add(acc_);
        pos_.add(speed_.scale(delta * 0.01f));
        acc_.set(0.0f, 0.0f);
    }
    
    public void draw(Rectangle rect) {
        // Draw sprite with input coords as offset
        sprite_.draw(rect.getX() + pos_.x, rect.getY() + pos_.y);
    }

}
