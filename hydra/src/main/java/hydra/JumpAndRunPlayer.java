package hydra;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class JumpAndRunPlayer extends JumpAndRunEntity {
    
    private Vector2f jumpImpulse_;
    private boolean jumping_;
    private Vector2f runLeftImpulse_;
    private boolean runningLeft_;
    private Vector2f runRightImpulse_;
    private boolean runningRight_;
    
    public JumpAndRunPlayer(Animation sprite, Vector2f pos) {
        sprite_ = sprite;
        pos_ = pos;
        speed_ = new Vector2f(0.0f, 0.0f);
        acc_ = new Vector2f(0.0f, 0.0f);
        
        jumpImpulse_ = new Vector2f(0.0f, -10.0f);
        runLeftImpulse_ = new Vector2f(-5.0f, 0.0f);
        runRightImpulse_ = new Vector2f(5.0f, 0.0f);
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
        if (!inAir && jumping_) this.impulse(jumpImpulse_);
        if (!inAir && runningLeft_) this.impulse(runLeftImpulse_);
        if (!inAir && runningRight_) this.impulse(runRightImpulse_);
        if (inAir && runningLeft_) this.impulse(runLeftImpulse_.scale(0.2f));
        if (inAir && runningRight_) this.impulse(runRightImpulse_.scale(0.2f));
        super.move(inAir, delta);
    }
    
    public String toString() {
        return "player";
    }
}
