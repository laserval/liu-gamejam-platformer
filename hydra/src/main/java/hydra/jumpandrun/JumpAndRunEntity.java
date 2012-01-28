package hydra.jumpandrun;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class JumpAndRunEntity {

    protected Animation sprite_;
    
    protected int inAirFrame_;
    
    protected Rectangle collisionMask_;
    
    /**
     * Position is the bottom center of the entity
     * */
    protected Vector2f pos_;

    protected Vector2f speed_;
    
    protected Vector2f acc_;
    
    protected boolean applyPhysics_ = true;
    
    protected boolean solid_ = true;
    
    
    public boolean collidesWith(JumpAndRunEntity other) {
        if (!solid_) return false;
        
        collisionMask_.setLocation(other.pos_.x, other.pos_.y);
        
        return collisionMask_.intersects(other.collisionMask_);
    }
    
    public boolean isSolid() {
        return solid_;
    }
    
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
    
    public Vector2f getSpeed() {
        return speed_;
    }
    
    public void multiplySpeed(float x, float y) {
        speed_.set(speed_.x * x, speed_.y * y);
    }
    
    public Vector2f getAcc() {
        return acc_;
    }
    
    public boolean applyPhysics() {
		return applyPhysics_;
	}
    
    public void friction(float f, int delta) {
        if (speed_.length() < 0.001f) {
            speed_.set(0.0f, 0.0f);
        }
        else {
            this.multiplySpeed(1.0f - (f * (float)delta), 1.0f);
        }
    }
    
    public void impulse(Vector2f v, int delta) {
        System.out.println(v + " " + delta);
        acc_.add( new Vector2f(v).scale((float)delta*0.001f));
    }
    
    public void move(boolean inAir, int delta) {
        speed_.add(acc_);
        pos_.add(new Vector2f(speed_).scale((float)delta*0.001f));
        acc_.set(0.0f, 0.0f);
    }
    
    public void draw(Rectangle rect) {
        // Draw sprite with input coords as offset
        sprite_.draw(rect.getX() + pos_.x - sprite_.getWidth()/2.0f, 
                    rect.getY() + pos_.y - sprite_.getHeight());
    }

    public void updateSprite(boolean inAir, int delta) {
        if (!inAir) {
            sprite_.update((int)(speed_.length() * 0.1f * (float)delta));
        }
        else {
            sprite_.setCurrentFrame(inAirFrame_);
        }
    }
}
