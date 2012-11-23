package uk.co.vurt.gdxtest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
// based on https://code.google.com/p/steigert-libgdx/source/browse/tags/post-20120709/#post-20120709%2Ftyrian-game%2Fsrc%2Fcom%2Fblogspot%2Fsteigert%2Ftyrian%2Fscreens%253Fstate%253Dclosed

public class TestGame extends Game {

	private static TestGame instance = null;
	
	private int width;
	private int height;
	private Screen screen;
	private SpriteBatch spriteBatch;
	private Vector2 scaleFactor;
	
	public synchronized static TestGame instance(){
		if(instance == null){
			instance = new TestGame();
			instance.create();
		}
		return instance;
	}
	
	@Override
	public void create() {
		screen = null;

		spriteBatch = new SpriteBatch();
		//scale factors used for adjusting touch events to the actual size of the view port
		scaleFactor.x = 1;
		scaleFactor.y = 1;
	}

	public void setScreen(Screen screen) {
		if(this.screen != null){
			this.screen.pause();
			//should we dispose of the screen?
			//need some set of rules to determine which we keep and which we don't.
			this.screen.dispose();
		}
		
		this.screen = screen;
		
		if(this.screen != null){
			this.screen.resume();
		}
	}

	public Screen getScreen() {
		return screen;
	}

	private void resizeScreen(){
		if(screen != null){
			screen.resize(width, height);
		}
	}
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		resizeScreen();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		resizeScreen();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		resizeScreen();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		if(screen != null){
			screen.pause();
			screen.dispose();
		}
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
}
