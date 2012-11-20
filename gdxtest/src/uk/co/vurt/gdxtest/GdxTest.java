package uk.co.vurt.gdxtest;

import uk.co.vurt.gdxtest.input.KeyAndMouseInputProcessor;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GdxTest implements ApplicationListener, ScrollHandler, TouchHandler {
	private final static int SCREEN_WIDTH = 800;
	private final static int SCREEN_HEIGHT = 480;
	private final static int TILE_WIDTH = 100;
	private final static int TILE_HEIGHT = 50;
	private OrthographicCamera camera;
	
	private Texture whiteTileImage;
	private Texture blackTileImage;
	private Texture puckImage;
//	private Sound dropSound;
	
	private SpriteBatch batch;

	
	private int[][] map =  {{0,1,1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1},
							{0,0,0,1,1,1,1,1,1,1},
							{1,1,0,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1,1,1}};
	
//	private int startX, startY;
//	private float centreX, centreY;
	private Vector3 touchPosition;
	private MapPosition puckCell;
	
	private Vector2 scroll;
	private float zoomLevel= 1.0f;
	private float tileWidth;
	private float tileHeight;
	
	InputMultiplexer inputMultiplexer;
	
	
	@Override
	public void create() {
		whiteTileImage = new Texture(Gdx.files.internal("tile_white.png"));
		blackTileImage = new Texture(Gdx.files.internal("tile_black.png"));
		puckImage = new Texture(Gdx.files.internal("puck.png"));
		puckCell = new MapPosition(0, 0);
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new KeyAndMouseInputProcessor(this, this));
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		scroll = new Vector2(0, 0);
		touchPosition = new Vector3();
		tileWidth = TILE_WIDTH * zoomLevel;
		tileHeight = TILE_HEIGHT * zoomLevel;
		
		
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT); //coordinate arranged from bottom left
//		camera.setToOrtho(true, WINDOW_WIDTH, WINDOW_HEIGHT); //coordinate arranged from top left
		
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		blackTileImage.dispose();
		whiteTileImage.dispose();
		batch.dispose();
	}

	@Override
	public void render() {
		//clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//draw everything on the screen
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//draw map
		Vector2 cellPosition;
		for(int row = 0; row < map[0].length; row++){
			for(int col = map.length-1; col >= 0; col--){
				cellPosition = mapToScreen(new MapPosition(row, col));
				if(cellPosition.x > -tileWidth && cellPosition.x < SCREEN_WIDTH+tileWidth
						&& cellPosition.y > -tileHeight && cellPosition.y < SCREEN_HEIGHT+tileHeight){
					if(map[col][row] == 0){
						batch.draw(blackTileImage, cellPosition.x, cellPosition.y, tileWidth, tileHeight);
					} else {
						batch.draw(whiteTileImage, cellPosition.x, cellPosition.y, tileWidth, tileHeight);
					}
				}
			}
		}
		//draw puck
		Vector2  puckPosition = mapToScreen(puckCell);
		batch.draw(puckImage, puckPosition.x, puckPosition.y, tileWidth, tileHeight);
		batch.end();
		
		


	}

 	private Vector2 mapToScreen(MapPosition mapPos){
 		return new Vector2(Math.round(((mapPos.getCol() + mapPos.getRow()) * tileWidth/2) + scroll.x),
 				Math.round(((mapPos.getRow() - mapPos.getCol()) * tileHeight/2) + scroll.y));
 	}
 	
 	//TODO: Finish the linear equation integration to make this more efficient
 	// and to fix the off-by-one issue with the column
 	private MapPosition screenToMap(float screenX, float screenY){
 		float offsetX = screenX - scroll.x/* - startX*/;
 		float offsetY = screenY - scroll.y/* - startY*/;
 		
 		int col = Math.round((offsetY/(tileHeight/2) + offsetX/(tileWidth/2))/2);
		int row = Math.round(offsetX/(tileWidth/2) - col);

		col = col -1;
		
		return new MapPosition(row, col);
 	}
 	
	private void handleTouch(int screenX, int screenY){
		System.out.println("Raw pos: " + screenX + "," + screenY);
		touchPosition.set(screenX, screenY, 0);
		camera.unproject(touchPosition);
		System.out.println("Unprojected: " + touchPosition.x + "," + touchPosition.y);

		MapPosition mapPos = screenToMap(touchPosition.x, touchPosition.y);
		
	    //check the click isn't outside the map boundaries
  		if(mapPos.getRow() >= 0 && mapPos.getCol() >= 0 && 
  		   mapPos.getRow() < map.length && mapPos.getCol() < map[0].length){
  			map[mapPos.getRow()][mapPos.getCol()] = map[mapPos.getRow()][mapPos.getCol()] == 0 ? 1 : 0;
  			System.out.println("Clicked on map[" + mapPos.getRow() + "][" + mapPos.getCol() + "]");
  		}else {
  			System.out.println("Clicked outside of map");
  		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void scrollUp() {
		scroll.y += 400 * Gdx.graphics.getDeltaTime();
		System.out.println("Scroll Y: " + scroll.y);
	}

	@Override
	public void scrollDown() {
		scroll.y -= 400 * Gdx.graphics.getDeltaTime();
		System.out.println("Scroll Y: " + scroll.y);
	}

	@Override
	public void scrollLeft() {
		scroll.x -= 400 * Gdx.graphics.getDeltaTime();
		System.out.println("Scroll X: " + scroll.x);
	}

	@Override
	public void scrollRight() {
		scroll.x += 400 * Gdx.graphics.getDeltaTime();
		System.out.println("Scroll X: " + scroll.x);
	}
	
	private int lastDragX = 0, lastDragY = 0;
	
	@Override
	public void dragReset(){
		lastDragX = 0;
		lastDragY = 0;
	}
	
	@Override
	public void scroll(int x, int y){
		if(lastDragX != 0 && lastDragY != 0){
			int xDiff = lastDragX - x;
			int yDiff = lastDragY - y;
			scroll.x -= xDiff;
			scroll.y += yDiff;
		}
		lastDragX = x;
		lastDragY = y;
				
	}

	@Override
	public void touch(int x, int y) {
		handleTouch(x, y);
	}
}
