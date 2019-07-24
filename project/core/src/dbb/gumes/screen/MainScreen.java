package dbb.gumes.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.*;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.animation.CutScene;
import dbb.gumes.gameobject.animation.GifDecoder;
import dbb.gumes.screen.ui.MainUI;
import sun.applet.Main;

public class MainScreen implements Screen, InputProcessor {

    public static OrthographicCamera camera;
    private Viewport viewport;

    private CutScene cutScene;

    private Stage stage;
    private MainUI ui;

    private static float cameraSpeed = 40;
    private float fontScale =  1;

    @Override
    public void show() {
        this.stage = new Stage();
//        MainScreen.camera = new OrthographicCamera();
        MainScreen.camera = (OrthographicCamera)this.stage.getCamera();
        this.viewport = new ExtendViewport(GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT, MainScreen.camera);
//        this.viewport = new ScreenViewport(this.camera);
        this.viewport.apply();
        MainScreen.camera.position.set(GumesGame.WORLD_WIDTH / 2, GumesGame.WORLD_HEIGHT / 2, 0);

        this.cutScene = new CutScene(GifDecoder.generateFrames(Gdx.files.internal("gif/frozen.gif").read()), 3f, true);

        this.stage.setViewport(this.viewport);
        this.stage.addActor(this.cutScene);
//        this.stage.setKeyboardFocus(this.cutScene);

//        Gdx.input.setInputProcessor(this.stage);
        Gdx.input.setInputProcessor(this);

        this.ui = new MainUI();
        this.stage.addActor(ui);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MainScreen.camera.update();

        this.stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        MainScreen.camera.viewportWidth = GumesGame.WORLD_WIDTH;
        MainScreen.camera.viewportHeight = GumesGame.WORLD_HEIGHT;
        this.viewport.update(width, height);
        this.viewport.update(GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT);
        MainScreen.camera.position.set(GumesGame.WORLD_WIDTH / 2f, GumesGame.WORLD_HEIGHT / 2f, 0);
//        MainScreen.camera.position.set(0, 0, 0);
        MainScreen.camera.update();
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


    public static Vector2 unproj(float x, float y) {
        Vector3 buff = MainScreen.camera.unproject(new Vector3(x, y, 0));
        return new Vector2(buff.x, buff.y);
    }

    public static Vector2 unproj(Vector2 point) {
        Vector3 buff = MainScreen.camera.unproject(new Vector3(point.x, point.y, 0));
        return new Vector2(buff.x, buff.y);
    }

    public static Vector2 proj(float x, float y) {
        Vector3 buff = MainScreen.camera.project(new Vector3(x, y, 0));
        return new Vector2(buff.x, buff.y);
    }

    public static Vector2 proj(Vector2 point) {
        Vector3 buff = MainScreen.camera.project(new Vector3(point.x, point.y, 0));
        return new Vector2(buff.x, buff.y);
    }


    // *********************************************************************************************



    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                MainScreen.camera.translate(-MainScreen.cameraSpeed, 0);
                break;
            case Input.Keys.D:
                MainScreen.camera.translate(MainScreen.cameraSpeed, 0);
                break;
            case Input.Keys.W:
                MainScreen.camera.translate(0, MainScreen.cameraSpeed);
                break;
            case Input.Keys.S:
                MainScreen.camera.translate(0, -MainScreen.cameraSpeed);
                break;
            case Input.Keys.Q:
                MainScreen.camera.zoom += 0.1;
                break;
            case Input.Keys.E:
                MainScreen.camera.zoom -= 0.1;
                break;
            case Input.Keys.NUM_1:
                this.fontScale -= 0.1f;
                ((Label)this.stage.getRoot().findActor("labelMouse")).setFontScale(this.fontScale);
                Gdx.app.log("log", this.fontScale + "");
                break;
            case Input.Keys.NUM_2:
                this.fontScale += 0.1f;
                ((Label)this.stage.getRoot().findActor("labelMouse")).setFontScale(this.fontScale);
                Gdx.app.log("log", this.fontScale + "");
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("mousePressed", Gdx.input.getX() + " : " + Gdx.input.getY());
        Vector2 b = MainScreen.unproj(Gdx.input.getX(), Gdx.input.getY());
        Gdx.app.log("mousePressed", b.x + " : " + b.y);

        Vector2 v = MainScreen.unproj(Gdx.input.getX(), Gdx.input.getY());
        ((Label)this.stage.getRoot().findActor("labelMouse")).setPosition(v.x, v.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
