package uk.co.vurt.gdxtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GdxTest implements ApplicationListener {
	private final static int SCREEN_WIDTH = 800;
	private final static int SCREEN_HEIGHT = 480;
	private final static int TILE_WIDTH = 100;
	private final static int TILE_HEIGHT = 50;
	private OrthographicCamera camera;
	
	private Texture whiteTileImage;
	private Texture blackTileImage;
//	private Sound dropSound;
	
	private SpriteBatch batch;

	
	private int[][] map =  {{0,1,1,1,1},
							{1,1,1,1,1},
							{0,0,0,1,1},
							{1,1,0,1,1},
							{1,1,1,1,1}};
	
	private int startX, startY;
	private float centreX, centreY;
	private Vector3 touchPosition;
	
	@Override
	public void create() {
		touchPosition = new Vector3();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		whiteTileImage = new Texture(Gdx.files.internal("tile_white.png"));
		blackTileImage = new Texture(Gdx.files.internal("tile_black.png"));
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT); //coordinate arranged from bottom left
//		camera.setToOrtho(true, WINDOW_WIDTH, WINDOW_HEIGHT); //coordinate arranged from top left
		
		batch = new SpriteBatch();
		
		centreX = SCREEN_WIDTH/2;
		centreY = SCREEN_HEIGHT/2;
		
		System.out.println("map.length: " + map.length);
		System.out.println("map.length/2: " + (map.length/2f));
		
		startX = Math.round(centreX - ((map.length/2f)*(TILE_WIDTH)));
		startY = Math.round(centreY - (TILE_HEIGHT/2f));
		
		System.out.println("Centre: " + centreX + ", " + centreY);
		System.out.println("Start: " + startX + ", " + startY);
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
//		batch.draw(blackTileImage, startX, startY, 1, 1);
	
		for(int row = 0; row < map.length; row++){
			for(int col = map[0].length-1; col >= 0; col--){
				Vector2 position = getScreenPositionForMapPosition(new MapPosition(row, col));
				if(map[col][row] == 0){
					batch.draw(blackTileImage, position.x, position.y);
				} else {
					batch.draw(whiteTileImage, position.x, position.y);
				}
			}
		}
		
		//draw centre point
//		batch.draw(blackTileImage, centreX, centreY, 1, 1);
		batch.end();
		
		//handle touch input
		if(Gdx.input.justTouched()){
			handleTouch(Gdx.input);
		}
	}

 	private Vector2 getScreenPositionForMapPosition(MapPosition mapPos){
 		int offsetX = Math.round((mapPos.getCol() + mapPos.getRow()) * TILE_WIDTH/2);
		int offsetY = Math.round((mapPos.getRow() - mapPos.getCol()) * TILE_HEIGHT/2);
		
		return new Vector2(startX + offsetX, startY + offsetY);
 	}
 	
 	//TODO: Finish the linear equation integration to make this more efficient
 	// and to fix the off-by-one issue with the column
 	private MapPosition getMapPositionFromScreenCoordinates(float screenX, float screenY){
 		float offsetY = screenY - startY;
 		float offsetX = screenX - startX;
 		
 		int col = Math.round((((2 * offsetY)/TILE_HEIGHT) + ((2 * offsetX)/TILE_WIDTH))/2);
		int row = Math.round(((2 * offsetX)/TILE_WIDTH) - col);
		col = col -1;
		
		return new MapPosition(row, col);
 	}
 	
	private void handleTouch(Input input){
		System.out.println("Raw pos: " + Gdx.input.getX() + "," + Gdx.input.getY());
		touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPosition);
		System.out.println("Unprojected: " + touchPosition.x + "," + touchPosition.y);

		MapPosition mapPos = getMapPositionFromScreenCoordinates(touchPosition.x, touchPosition.y);
		
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
}
