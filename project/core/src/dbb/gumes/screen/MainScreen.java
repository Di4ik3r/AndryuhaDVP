package dbb.gumes.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.*;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.Player;
import dbb.gumes.gameobject.animation.CutScene;
import dbb.gumes.gameobject.animation.GifDecoder;
import dbb.gumes.gameobject.world.GameWorld;
import dbb.gumes.screen.ui.MainUI;

import java.awt.*;

public class MainScreen implements Screen {

    public static OrthographicCamera camera;
    private Viewport viewport;

    private CutScene cutScene;

    private Stage stage;
    private MainUI ui;

    private static float cameraSpeed = 4 / GumesGame.PPM;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private GameWorld gameWorld;

    private Player player;





    @Override
    public void show() {
        this.stage = new Stage();
//        MainScreen.camera = new OrthographicCamera();
        MainScreen.camera = (OrthographicCamera)this.stage.getCamera();
        this.viewport = new ExtendViewport(GumesGame.WORLD_WIDTH / GumesGame.PPM, GumesGame.WORLD_HEIGHT / GumesGame.PPM, MainScreen.camera);
//        this.viewport = new ScreenViewport(this.camera);
        this.viewport.apply();
        MainScreen.camera.position.set(GumesGame.WORLD_WIDTH / 2, GumesGame.WORLD_HEIGHT / 2, 0);

        this.cutScene = new CutScene(GifDecoder.generateFrames(Gdx.files.internal("gif/frozen.gif").read()), 3f, true);

        this.stage.setViewport(this.viewport);
//        this.stage.addActor(this.cutScene);
//        this.stage.setKeyboardFocus(this.cutScene);

        Gdx.input.setInputProcessor(this.stage);

//        this.ui = new MainUI();
        this.ui = new MainUI();
//        this.stage.addActor(ui);

        this.mapLoader = new TmxMapLoader();
        this.map = this.mapLoader.load("world/lvl.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map, 1 / GumesGame.PPM);

        this.world = new World(new Vector2(0, -10), true);
        this.b2dr = new Box2DDebugRenderer();

        gameWorld = new GameWorld(this.world, this.map);

        player = new Player(this.world,
                new TextureRegion(new Texture(Gdx.files.internal("img/badlogic.jpg"))),
                new Vector2(50, 50),
                new Vector2(8*2, 16*2)
        );

        this.stage.addActor(player);
    }

    @Override
    public void render(float delta) {
        this.updateWorld(delta);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MainScreen.camera.position.x = this.player.getX();

        MainScreen.camera.update();

        this.mapRenderer.render();

        this.stage.act(delta);
        this.stage.draw();

//        this.b2dr.render(this.world, MainScreen.camera.combined);

        this.ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        MainScreen.camera.viewportWidth = GumesGame.WORLD_WIDTH / GumesGame.PPM;
        MainScreen.camera.viewportHeight = GumesGame.WORLD_HEIGHT / GumesGame.PPM;
        this.viewport.update(width, height);
        this.viewport.update(GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT);
        MainScreen.camera.position.set(GumesGame.WORLD_WIDTH / 2f / GumesGame.PPM, GumesGame.WORLD_HEIGHT / 2f / GumesGame.PPM, 0);
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
        this.map.dispose();
        this.mapRenderer.dispose();
        this.world.dispose();
        this.b2dr.dispose();
        this.ui.dispose();
    }


    private void updateWorld(float delta) {
        this.handleInput();

        this.world.step(1/60f, 6, 2);

        MainScreen.camera.update();
        this.mapRenderer.setView(MainScreen.camera);
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

    private void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.player.moveLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.player.moveRight();
        }



        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            MainScreen.camera.translate(-MainScreen.cameraSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            MainScreen.camera.translate(MainScreen.cameraSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            MainScreen.camera.translate(0, MainScreen.cameraSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            MainScreen.camera.translate(0, -MainScreen.cameraSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            MainScreen.camera.zoom += 0.04;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            MainScreen.camera.zoom -= 0.04;
        }
    }
}
