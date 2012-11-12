package uk.co.vurt.gdxtest.client;

import uk.co.vurt.gdxtest.GdxTest;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(800, 480);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new GdxTest();
	}
}