package hydra;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class JumpAndRunPlayer implements JumpAndRunEntity {
    
    private Animation sprite_;
    
    private Vector2f pos_;

    private Vector2f speed_;
    
    public JumpAndRunPlayer(Animation sprite, Vector2f pos) {
        sprite_ = sprite;
        pos_ = pos;
        
    }
    
    
    public String toString() {
        return "player";
    }
}
