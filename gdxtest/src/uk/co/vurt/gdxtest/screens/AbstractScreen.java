package uk.co.vurt.gdxtest.screens;

import uk.co.vurt.gdxtest.TestGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractScreen implements Screen {

	protected final TestGame game;
	protected final Stage stage;

	public static final int VIEWPORT_WIDTH = 800;
	public static final int VIEWPORT_HEIGHT = 480;
	
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;
	
	public AbstractScreen(TestGame game){
		this.game = game;
		this.stage = new Stage(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, true);
	}
	@Override
	public void render(float delta) {
		//call the stage to process any game logic
		stage.act(delta);
		//draw the result on screen
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		//Draw table debug lines
		//Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		dispose();
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
		if(font != null){
			font.dispose();
		}
		if(spriteBatch != null){
			spriteBatch.dispose();
		}
		if(skin != null){
			skin.dispose();
		}
		if(atlas != null){
			atlas.dispose();
		}
	}

	public BitmapFont getFont(){
		if(font == null){
			font = new BitmapFont();
		}
		return font;
	}
	
	public SpriteBatch getSpriteBatch(){
		if(spriteBatch == null){
			spriteBatch = new SpriteBatch();
		}
		return spriteBatch;
	}
	
	protected Skin getSkin(){
		if(skin == null){
			FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
			skin = new Skin(skinFile);
		}
		return skin;
	}
	
	protected TextureAtlas getAtlas(){
		if(atlas == null){
			atlas = new TextureAtlas(Gdx.files.internal("image-atlases/pages.atlas"));
		}
		return atlas;
	}
	
	protected Table getTable(){
		if(table == null){
			table = new Table(getSkin());
			table.setFillParent(true);
			stage.addActor(table);
		}
		return table;
	}
}
