package hydra.jumpandrun;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class JumpAndRunEntity {

    protected Animation sprite_;
    
    protected int inAirFrame_;
    
    protected Shape collisionMask_;
    
    /**
     * Position is the bottom center of the entity
     * */
    protected Vector2f pos_;

    protected Vector2f speed_;
    
    protected Vector2f acc_;
    
    protected boolean applyPhysics_ = true;
    protected boolean applyScrolling_ = true;
    
    protected boolean solid_ = true;
    
    public boolean deadly_ = true;
    
    public boolean collidesWith(JumpAndRunEntity other) {
        if (!solid_ || !other.solid_) return false;
        
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
    
    public void setPosition(Vector2f v) {
        pos_.set(v);
    }
    
    public void setSpeed(float x, float y) {
        speed_.set(x, y);
    }
    
    public void setSpeed(Vector2f v) {
        speed_.set(v);
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
	
	public boolean applyScrolling() {
		return applyScrolling_;
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
        //System.out.println(v + " " + delta);
        acc_.add( new Vector2f(v).scale((float)delta*0.001f));
    }
    
    public void move(boolean inAir, int delta) {
        speed_.add(acc_);
        pos_.add(new Vector2f(speed_).scale((float)delta*0.001f));
        acc_.set(0.0f, 0.0f);
    }
    
    public void draw(Graphics g, Rectangle rect) {
		// collision area
		g.setColor(new Color(0, 255, 0));
		g.fillRect(rect.getX() + collisionMask_.getX(), rect.getY() + collisionMask_.getY(), collisionMask_.getWidth(), collisionMask_.getHeight());
		
		// Draw sprite with input coords as offset
        sprite_.draw(rect.getX() + pos_.x - getWidth()/2.0f, 
                    rect.getY() + pos_.y - getHeight());
		
		// center
		//g.setColor(new Color(0, 255, 0));
		// g.fillRect(rect.getX() + pos_.x - 10, rect.getY() + pos_.y - 10, 20, 20);
		
        
    }

    public void updateSprite(boolean inAir, int delta) {
        if (!inAir) {
            sprite_.update((int)(speed_.length() * 0.05f * (float)delta));
        }
        else {
            sprite_.setCurrentFrame(inAirFrame_);
        }
    }
    
    public void update(int delta) {
		collisionMask_.setLocation(pos_.x - collisionMask_.getWidth() / 2, pos_.y - collisionMask_.getHeight());
		//System.out.println("pos=" + pos_ + " mask=(" + collisionMask_.getX() + "/" + collisionMask_.getY() + ") " + collisionMask_);
	}
}
