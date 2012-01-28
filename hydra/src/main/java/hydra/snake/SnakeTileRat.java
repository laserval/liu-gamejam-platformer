package hydra.snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Color;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SnakeTileRat extends SnakeTile {
	//private int volume = 10;
	
	static Sound fx;
	Image[] ratImages = new Image[1];
	Animation ratAnim = new Animation(false);
	
	
	public SnakeTileRat(int x, int y, Rectangle rect) {
		super(x, y, rect);
		
		try {
			fx = new Sound("SnakeEat.ogg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}

		// Load apples
		try {
			ratImages[0] = new Image("mouse_tile.jpg");
		} catch(SlickException e) {
			System.out.println(e);
			return;
		}
		
		ratAnim.addFrame(ratImages[0], 1);
	}

	public void render(Graphics g) {
		ratAnim.draw(clipRect_.getX(), clipRect_.getY());
	}

	public int[] onHeadMovement(){
		// Collision with food
		playingSound();
		SnakeGame.instance_.growSnake(2, "rat");
		return new int[] {x_, y_};
	}

	public void playingSound(){
		if(fx.playing() == false){
			fx.play();
		}
	}
	
	public String toString() {
		return "food";
	}
}

