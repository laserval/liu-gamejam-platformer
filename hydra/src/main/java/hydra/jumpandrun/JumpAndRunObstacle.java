
package hydra.jumpandrun;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

public class JumpAndRunObstacle extends JumpAndRunEntity {
	private static Animation appleAnim;
	private static Animation ratAnim;
	private static Animation chiliAnim;
	
	static {
		Image[] appleImg = new Image[1];
		try {
			appleImg[0] = new Image("obstacle_apple.png");
		} catch(SlickException e) {
			System.out.println(e);
		}
		
		appleAnim = new Animation(false);
		appleAnim.addFrame(appleImg[0], 1);
		
		
		Image[] ratImg = new Image[1];
		try {
			ratImg[0] = new Image("obstacle_mouse.png");
		} catch(SlickException e) {
			System.out.println(e);
		}
		
		ratAnim = new Animation(false);
		ratAnim.addFrame(ratImg[0], 1);
		
		Image[] chiliImg = new Image[1];
		try {
			chiliImg[0] = new Image("obstacle_cili.png");
		} catch(SlickException e) {
			System.out.println(e);
		}
		
		chiliAnim = new Animation(false);
		chiliAnim.addFrame(chiliImg[0], 1);
	}
	
    public JumpAndRunObstacle(Vector2f pos, String type) {
		applyPhysics_ = false;
		applyScrolling_ = true;
		
		pos_ = pos;
		
		initType(type);
    }
    
    public void initType(String type) {
		if (type.equals("apple")) {
			sprite_ = appleAnim;
			collisionMask_ = new Rectangle(0, 0, 130, 120);
		} else if (type.equals("rat")) {
			sprite_ = ratAnim;
			collisionMask_ = new Rectangle(0, 0, 145, 130);
		} else if (type.equals("chili")) {
			sprite_ = chiliAnim;
			collisionMask_ = new Rectangle(0, 0, sprite_.getWidth(), sprite_.getHeight());
		}
	}
    
    public String toString() {
        return "deadly obstacle";
    }
}	
