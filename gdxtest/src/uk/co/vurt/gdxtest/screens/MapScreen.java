package uk.co.vurt.gdxtest.screens;

import uk.co.vurt.gdxtest.TestGame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapScreen extends Stage implements Screen {

	public MapScreen() {
		this(TestGame.instance().getWidth(), TestGame.instance().getHeight(),
				TestGame.instance().getSpriteBatch());
	}

	public MapScreen(float width, float height, SpriteBatch spriteBatch) {
		super(width, height, false, spriteBatch);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
