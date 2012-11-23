package uk.co.vurt.gdxtest.scene.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PuckActor extends Image {

	private final Vector2 position;

	TextureRegion texture; //should eventually be replaced with a textureregion? different views of the character depending on direction faced.

	private PuckActor(TextureRegion texture){
		super(texture);
		this.texture = texture;
		this.position = new Vector2();
	}

	public static PuckActor create(TextureAtlas atlas){
		return new PuckActor(atlas.findRegion("level-screen/puck"));
	}
	
	public void setInitialPosition(float x, float y){
		position.set(x, y);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}
}
