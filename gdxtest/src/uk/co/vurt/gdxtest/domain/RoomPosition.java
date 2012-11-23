package uk.co.vurt.gdxtest.domain;

public class RoomPosition {

	//made public for convenience's sake
	public int row = 0;
	public int col = 0;
	
	public RoomPosition(int x, int y){
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
