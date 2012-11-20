package uk.co.vurt.gdxtest.input;

import uk.co.vurt.gdxtest.ScrollHandler;
import uk.co.vurt.gdxtest.TouchHandler;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class KeyAndMouseInputProcessor implements InputProcessor {

	private ScrollHandler scrollHandler;
	private TouchHandler touchHandler;
	
	public KeyAndMouseInputProcessor(ScrollHandler scrollHandler, TouchHandler touchHandler){
		this.scrollHandler = scrollHandler;
		this.touchHandler = touchHandler;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		boolean handled = true;
		switch(keycode){
			case Keys.LEFT:
				scrollHandler.scrollLeft();
				break;
			case Keys.RIGHT:
				scrollHandler.scrollRight();
				break;
			case Keys.UP:
				scrollHandler.scrollUp();
				break;
			case Keys.DOWN:
				scrollHandler.scrollDown();
				break;
			default:
				handled = false;
				break;
		}
		return handled;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchHandler.touch(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		scrollHandler.scroll(screenX, screenY);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
