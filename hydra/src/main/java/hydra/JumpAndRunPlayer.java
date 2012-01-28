package hydra;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class JumpAndRunPlayer extends JumpAndRunEntity {
    
    private Vector2f jumpImpulse_;
    private boolean jumping_;
    private Vector2f runImpulse_;
    private boolean runningRight_;
    private boolean runningLeft_;
    
    public JumpAndRunPlayer(Animation sprite, Vector2f pos) {
        sprite_ = sprite;
        pos_ = pos;
        speed_ = new Vector2f(0.0f, 0.0f);
        acc_ = new Vector2f(0.0f, 0.0f);
        
        jumpImpulse_ = new Vector2f(0.0f, -2.0f);
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
    
    public void move() {
        if (jumping_) this.impulse(jumpImpulse_);
        if (runningLeft_) this.impulse(runningLeft_);
        if (runningRight_) this.impulse(runningRight_);
        super.move(delta);
    }
    
    public String toString() {
        return "player";
    }
}
