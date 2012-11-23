package uk.co.vurt.gdxtest.screens;

import uk.co.vurt.gdxtest.TestGame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class SplashScreen extends AbstractScreen {

	private Image splashImage;
	
	public SplashScreen(TestGame game){
		super(game);
	}

	@Override
	public void show() {
		super.show();
		
		AtlasRegion splashRegion = getAtlas().findRegion("splash-screen/splash-image");
		Drawable splashDrawable = new TextureRegionDrawable(splashRegion);
		
		splashImage = new Image(splashDrawable, Scaling.fit);
		splashImage.setFillParent(true);
		
		splashImage.getColor().a = 0f; //set the image completely transparent
		
		splashImage.addAction(Actions.sequence(Actions.fadeIn(0.75f), Actions.delay(1.75f), Actions.fadeOut(0.75f), new Action(){

			@Override
			public boolean act(float delta) {
				game.setScreen(new GameScreen(game));
				return true;
			}
			
		}));
		stage.addActor(splashImage);
	}
	
}
