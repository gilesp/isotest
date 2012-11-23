package uk.co.vurt.gdxtest.screens;

import uk.co.vurt.gdxtest.TestGame;
import uk.co.vurt.gdxtest.domain.Room;
import uk.co.vurt.gdxtest.scene.actors.PuckActor;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GameScreen extends AbstractScreen {

	int[][] map = {{0,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1},
				   {0,0,0,1,1,1,1,1,1,1},
				   {1,1,0,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1}};
	
	private Room room = new Room(map);
	private PuckActor puck;
	
	public GameScreen(TestGame game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		puck = PuckActor.create(getAtlas());
		stage.addActor(puck);
		stage.getRoot().getColor().a = 0f;
		stage.getRoot().addAction(Actions.fadeIn(0.5f));
	}

}
