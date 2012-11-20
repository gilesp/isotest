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
	
	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
	
}
