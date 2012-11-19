package uk.co.vurt.gdxtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
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
	
	private Vector2 scroll;
	private float zoomLevel= 1.0f;
	private float tileWidth;
	private float tileHeight;
	
	
	
	@Override
	public void create() {
		scroll = new Vector2(0, 0);
		touchPosition = new Vector3();
		tileWidth = TILE_WIDTH * zoomLevel;
		tileHeight = TILE_HEIGHT * zoomLevel;
		
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
		
		whiteTileImage = new Texture(Gdx.files.internal("tile_white.png"));
		blackTileImage = new Texture(Gdx.files.internal("tile_black.png"));
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT); //coordinate arranged from bottom left
//		camera.setToOrtho(true, WINDOW_WIDTH, WINDOW_HEIGHT); //coordinate arranged from top left
		
		batch = new SpriteBatch();
//		
//		centreX = SCREEN_WIDTH/2;
//		centreY = SCREEN_HEIGHT/2;
//		
//		System.out.println("map.length: " + map.length);
//		System.out.println("map.length/2: " + (map.length/2f));
//		
//		startX = Math.round(centreX - ((map.length/2f)*(TILE_WIDTH)));
//		startY = Math.round(centreY - (TILE_HEIGHT/2f));
//		
//		System.out.println("Centre: " + centreX + ", " + centreY);
//		System.out.println("Start: " + startX + ", " + startY);
	}

	@Override
	public void dispose() {
		blackTileImage.dispose();
		whiteTileImage.dispose();
		batch.dispose();
	}

	private MapPosition topLeft,topRight, bottomLeft, bottomRight;
	private int minRow, minCol, maxRow, maxCol; 
	
 	@Override
	public void render() {
		//clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		
		topLeft = screenToMap(0, 0);
		topRight = screenToMap(SCREEN_WIDTH, 0);
		bottomLeft = screenToMap(0, SCREEN_HEIGHT);
		bottomRight = screenToMap(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		System.out.println("topLeft: " + topLeft);
		System.out.println("topRight: " + topRight);
		System.out.println("bottomLeft: " + bottomLeft);
		System.out.println("bottomRight: " + bottomRight);
		
		minRow = bottomLeft.row;
		minCol = topLeft.col;
		maxRow = topRight.row;
		maxCol = bottomRight.col;
		
		minRow = minRow < 0 ? 0 : minRow;
		minCol = minCol < 0 ? 0 : minCol;

		maxRow = maxRow > map[0].length ? map[0].length-1 : maxRow;
		maxCol = maxCol > map.length ? map.length-1 : maxCol;
		
		System.out.println("row: " + minRow + "->" + maxRow);
		System.out.println("col: " + minCol + "->" + maxCol);
		
		//draw everything on the screen
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int row = minRow; row < maxRow; row++){
			for(int col = maxCol; col >= minCol; col--){
//			for(int col = startCol; col < colCount; col++){
				Vector2 position = mapToScreen(new MapPosition(row, col));
				if(map[row][col] == 0){
					batch.draw(blackTileImage, position.x, position.y, tileWidth, tileHeight);
				} else {
					batch.draw(whiteTileImage, position.x, position.y, tileWidth, tileHeight);
				}
			}
		}
//		for(int row = 0; row < map.length; row++){
//			for(int col = map[0].length-1; col >= 0; col--){
//				Vector2 position = mapToScreen(new MapPosition(row, col));
//				if(map[col][row] == 0){
//					batch.draw(blackTileImage, position.x, position.y, tileWidth, tileHeight);
//				} else {
//					batch.draw(whiteTileImage, position.x, position.y, tileWidth, tileHeight);
//				}
//			}
//		}
		batch.end();
		
		//handle touch input
		if(Gdx.input.justTouched()){
			handleTouch(Gdx.input);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			scroll.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			scroll.x += 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			scroll.y += 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			scroll.y -= 200 * Gdx.graphics.getDeltaTime();
		}

	}

 	private Vector2 mapToScreen(MapPosition mapPos){
 		int offsetX = Math.round(((mapPos.getCol() + mapPos.getRow()) * tileWidth/2) + scroll.x) ;
		int offsetY = Math.round(((mapPos.getRow() - mapPos.getCol()) * tileHeight/2) + scroll.y);
		
//		return new Vector2(startX + offsetX, startY + offsetY);
		return new Vector2(offsetX, offsetY);
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
 	
	private void handleTouch(Input input){
		System.out.println("Raw pos: " + Gdx.input.getX() + "," + Gdx.input.getY());
		touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
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
}
