
package hydra;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Color;

import hydra.BaseSubGame;
import hydra.App;
import hydra.jumpandrun.JumpAndRunGame;

public class StartScreen {
	public void render(GameContainer gc, Graphics g) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(200, 200, 200, 200);
	}
	
	public void update(GameContainer gc, int delta) {
		if (gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			App.instance_.showStartScreenNext_ = false;
		}
	}
	
	public void init(GameContainer gc) {
	}
	
}
