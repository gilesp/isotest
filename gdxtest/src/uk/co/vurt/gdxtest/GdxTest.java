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
	
	private SpriteBatch batch;
	
	private int[][] map =  {{0,1,1,1,1},
							{1,1,1,1,1},
							{0,0,0,1,1},
							{1,1,0,1,1},
							{1,1,1,1,1}};
	
//	private int startX, startY;
	private int zoomLevel = 1;
//	private float centreX, centreY;
	private Vector3 touchPosition = new Vector3();
	private Vector2 scrollPosition = new Vector2(0, 0);
	private int tileWidth = TILE_WIDTH * zoomLevel;
	private int tileHeight = TILE_HEIGHT * zoomLevel;
	
	@Override
	public void create() {
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
		
		//load the tile images
		whiteTileImage = new Texture(Gdx.files.internal("tile_white.png"));
		blackTileImage = new Texture(Gdx.files.internal("tile_black.png"));
		
		//initialise the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT); //coordinate arranged from bottom left
//		camera.setToOrtho(true, SCREEN_WIDTH, SCREEN_HEIGHT); //coordinate arranged from top left
		
		//initially centre the starting point horizontally and vertically
		scrollPosition.x = map.length * zoomLevel * scrollPosition.x;
		scrollPosition.y = map[0].length * zoomLevel * scrollPosition.y;
		
		System.out.println("scroll position: " + scrollPosition.x + "," + scrollPosition.y);
		
		//initialise the batch for drawing stuff on screen
		batch = new SpriteBatch();
		
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

 	@Override
	public void render() {
		//clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		MapPosition topLeft = screenToMap(1,1);
		MapPosition topRight = screenToMap(1, SCREEN_WIDTH);
		MapPosition bottomLeft = screenToMap(SCREEN_WIDTH, 1);
		MapPosition bottomRight = screenToMap(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		int startRow = topLeft.row;
		int startCol = topLeft.col;
		int rowCount = bottomRight.row + 1;
		int colCount = bottomRight.col + 1;

		
		
		startRow = startRow < 0 ? 0 : startRow;
		startCol = startCol < 0 ? 0 : startCol;
		
		rowCount = rowCount > map.length ? map.length : rowCount;
		colCount = colCount > map[0].length ? map[0].length : colCount;
		
		System.out.println("start: " + startRow + "," + startCol);
		System.out.println("count: " + rowCount + ":" + colCount);
		
		//draw everything on the screen
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
//		batch.draw(blackTileImage, startX, startY, 1, 1);
	
		float xPos, yPos;
		for(int row = startRow; row < rowCount; row++){
			for(int col = startCol; col < colCount; col++){
				xPos = (row - col) * tileHeight + (map.length * zoomLevel);
				xPos += (SCREEN_WIDTH/2) - ((tileWidth / 2) * zoomLevel) + scrollPosition.x;
				yPos = (row + col) * (tileHeight/2) + (map[0].length * zoomLevel) + scrollPosition.y;
				
				if(map[col][row] == 0){
					batch.draw(blackTileImage, xPos, yPos);
				} else {
					batch.draw(whiteTileImage, xPos, yPos);
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

// 	private Vector2 mapToScreen(MapPosition mapPos){
// 		int offsetX = Math.round((mapPos.getCol() + mapPos.getRow()) * TILE_WIDTH/2);
//		int offsetY = Math.round((mapPos.getRow() - mapPos.getCol()) * TILE_HEIGHT/2);
//		
//		return new Vector2(startX + offsetX, startY + offsetY);
// 	}
 	
 	private final static int HALF_TILE_HEIGHT = TILE_HEIGHT / 2;
 	private final static int HALF_TILE_WIDTH = TILE_WIDTH / 2;
 	
 	//TODO: Finish the linear equation integration to make this more efficient
 	// and to fix the off-by-one issue with the column
 	private MapPosition screenToMap(float screenX, float screenY){
 		Vector2 offset = new Vector2();
 		offset.y = (map.length * zoomLevel) + scrollPosition.y;
 		offset.x = (map[0].length * zoomLevel);
 		offset.x += (SCREEN_WIDTH / 2) - ((tileWidth/2) * zoomLevel) + scrollPosition.x;
 		
 		int col = Math.round(((2 * (screenY - offset.y) - screenX + offset.x)/ 2) / tileHeight);
 		int row = Math.round((screenX + col - offset.y - tileHeight) / tileHeight);
		
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
