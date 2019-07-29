package dbb.gumes.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dbb.gumes.misc.Assets;
import dbb.gumes.screen.MainScreen;

public class GumesGame extends Game {

	public static final int WORLD_WIDTH = 426;
	public static final int WORLD_HEIGHT = 240;
	public static final float PPM = 1;

	private MainScreen mainScreen;

	private static Assets assets;
	public static TextureAtlas atlas;

	@Override
	public void create() {
//		GumesGame.assets = new Assets("assets.atlas");
//		GumesGame.atlas = GumesGame.assets.getManager().get("assets.atlas", TextureAtlas.class);

		this.mainScreen = new MainScreen();

		this.setScreen(mainScreen);
	}

	@Override
	public void dispose() {

	}
}
