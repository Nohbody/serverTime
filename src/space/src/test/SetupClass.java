package space.src.test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SetupClass extends BasicGame {
	
	private Image ship = null;
	private Image enemySet = null;
	private SpriteSheet enemies = null;
	
	
	public SetupClass(String title) {
		super(title);
	}
	
	public void init(GameContainer container) throws SlickException {
		ship = new Image("data/ship-animated.gif");
		enemySet = new Image("data/enemyships.jpg");
		enemies = new SpriteSheet(enemySet, 130, 120);
		
	}	
	
	public void update(GameContainer container, int delta) throws SlickException {
		
	}
	
	public void render(GameContainer container, Graphics arg1) throws SlickException {
		for(int x = 150; x < 1000; x+= 150) {
			enemies.getSubImage(1, 0).draw(x, 80);
		}
		ship.startUse();
		ship.draw(800, 800);
		ship.endUse();
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SetupClass("Basic Game"));
		
		app.setDisplayMode(1200, 800, false);
		app.start();
	}
}
