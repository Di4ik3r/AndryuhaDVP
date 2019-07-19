package dbb.gumes.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.animation.CutScene;
import dbb.gumes.gameobject.animation.GifDecoder;


public class MainScreen implements Screen {

    private SpriteBatch batch;
    private static OrthographicCamera camera;
    private Viewport viewport;

    private CutScene cutScene;

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        MainScreen.camera = new OrthographicCamera();
        this.viewport = new FillViewport(GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT, MainScreen.camera);
        MainScreen.camera.position.set(GumesGame.WORLD_WIDTH / 2, GumesGame.WORLD_HEIGHT / 2, 0);
        this.viewport.apply();

        this.cutScene = new CutScene(GifDecoder.generateFrames(Gdx.files.internal("frozen.gif").read()), 3f, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MainScreen.camera.update();
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();

            this.cutScene.draw(batch, delta);

        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        MainScreen.camera.viewportWidth = GumesGame.WORLD_WIDTH;
        MainScreen.camera.viewportHeight = GumesGame.WORLD_HEIGHT;
        this.viewport.update(width, height);
        this.viewport.update(GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT);
        MainScreen.camera.position.set(GumesGame.WORLD_WIDTH / 2f, GumesGame.WORLD_HEIGHT / 2f, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
