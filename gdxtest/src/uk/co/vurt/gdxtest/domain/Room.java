package uk.co.vurt.gdxtest.domain;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.OrderedMap;

public class Room implements Serializable {

	private int width;
	private int height;
	private int[][] map;

	public Room(int[][] map){
		this.map = map;
		this.width = map.length;
		this.height = map[0].length;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isValid(RoomPosition position) {
		return position.row < width && position.row > 0
				&& position.col < height && position.col > 0;
	}
	
	public int getPosition(RoomPosition position){
		return map[position.row][position.col];
	}
	
	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void read(Json json, OrderedMap<String, Object> jsonData) {
		// TODO Auto-generated method stub

	}
}
