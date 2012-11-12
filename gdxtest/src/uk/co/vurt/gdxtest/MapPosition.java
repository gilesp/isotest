package uk.co.vurt.gdxtest;

public class MapPosition {

	int row = 0;
	int col = 0;
	
	public MapPosition(int x, int y){
		this.row = x;
		this.col = y;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
