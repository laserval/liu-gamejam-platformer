
package hydra.jumpandrun;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

public class JumpAndRunObstacle extends JumpAndRunEntity {
    public JumpAndRunObstacle(Vector2f pos, String type) {
		applyPhysics_ = false;
		applyScrolling_ = true;
		
		pos_ = pos;
		
		initType(type);
    }
    
    public void initType(String type) {
		if (type.equals("apple")) {
			Image[] obstacleImages = new Image[1];
			try {
				obstacleImages[0] = new Image("obstacle_apple.png");
			} catch(SlickException e) {
				System.out.println(e);
				return;
			}
			
			Animation obstacleAnim = new Animation(false);
			obstacleAnim.addFrame(obstacleImages[0], 1);
			
			sprite_ = obstacleAnim;
			collisionMask_ = new Rectangle(0, 0, 130, 120);
		} else if (type.equals("rat")) {
			Image[] obstacleImages = new Image[1];
			try {
				obstacleImages[0] = new Image("obstacle_mouse.png");
			} catch(SlickException e) {
				System.out.println(e);
				return;
			}
			
			Animation obstacleAnim = new Animation(false);
			obstacleAnim.addFrame(obstacleImages[0], 1);
			
			sprite_ = obstacleAnim;
			collisionMask_ = new Rectangle(0, 0, 145, 130);
		}
	}
    
    public String toString() {
        return "deadly obstacle";
    }
}	
