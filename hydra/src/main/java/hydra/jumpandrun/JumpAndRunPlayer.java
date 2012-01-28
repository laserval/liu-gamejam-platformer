package hydra.jumpandrun;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

public class JumpAndRunPlayer extends JumpAndRunEntity {
    
    private Vector2f jumpImpulse_;
    private boolean jumping_;
    private Vector2f runLeftImpulse_;
    private boolean runningLeft_;
    private Vector2f runRightImpulse_;
    private boolean runningRight_;
    
    private float airSpeedMultiplier_ = 0.1f;
    
    public JumpAndRunPlayer(Animation sprite, Vector2f pos) {
        sprite_ = sprite;
        sprite_.setAutoUpdate(false);
        inAirFrame_ = 2;
        
        pos_ = pos;
        speed_ = new Vector2f(0.0f, 0.0f);
        acc_ = new Vector2f(0.0f, 0.0f);
        
        jumpImpulse_ = new Vector2f(0.0f, -600.0f);
        runLeftImpulse_ = new Vector2f(-2500.0f, 0.0f);
        runRightImpulse_ = new Vector2f(2500.0f, 0.0f);
        
        collisionMask_ = new Rectangle(0.0f, 0.f, sprite_.getWidth(), sprite_.getHeight() - 10);
    }
    
    public void resetMoves() {
        jumping_ = runningLeft_ = runningRight_ = false;
    }
    
    public void jump() {
        jumping_ = true;
    }
    
    public void right() {
        runningRight_ = true;
    }
    
    public void left() {
        runningLeft_ = true;
    }
    
    public void move(boolean inAir, int delta) {
        // On the ground, can jump!
        if (!inAir && jumping_) acc_.add(jumpImpulse_);
        // On the ground, can run at full speed
        if (!inAir && runningLeft_) this.impulse(runLeftImpulse_, delta);
        if (!inAir && runningRight_) this.impulse(runRightImpulse_, delta);
        // In the air, can control a bit
        if (inAir && runningLeft_) this.impulse(new Vector2f(runLeftImpulse_).scale(airSpeedMultiplier_), delta);
        if (inAir && runningRight_) this.impulse(new Vector2f(runRightImpulse_).scale(airSpeedMultiplier_), delta);
        super.move(inAir, delta);
    }
    
    public String toString() {
        return "player";
    }
    
    public void update(int delta) {
		super.update(delta);
		collisionMask_.setY(collisionMask_.getY() - 10);
	}
}
