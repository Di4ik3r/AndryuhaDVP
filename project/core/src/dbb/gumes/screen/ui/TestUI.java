package dbb.gumes.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import dbb.gumes.game.GumesGame;
import dbb.gumes.screen.MainScreen;

public class TestUI {

    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Label labelMouse, labelFPS, labelLength;
    private Vector2 mouseCoord;

    public TestUI() {
        this.stage = new Stage();

//        ((OrthographicCamera)this.stage.getCamera()).position.setZero();
        ((OrthographicCamera)this.stage.getCamera()).position.set(GumesGame.WORLD_WIDTH/2, GumesGame.WORLD_HEIGHT/2, 0);
        this.stage.setViewport(new StretchViewport(GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT, this.stage.getCamera()));

        this.skin = new Skin(Gdx.files.internal("skin.json"));
        this.font = new BitmapFont(Gdx.files.internal("font.fnt"));

        mouseCoord = MainScreen.unproj(Gdx.input.getX(), Gdx.input.getY());

        this.labelMouse = createLabel("", 0.5f,  new Vector2(0, 30));
        RunnableAction runnableActionX = new RunnableAction();
        runnableActionX.setRunnable(new Runnable() {
            @Override
            public void run() {
                mouseCoord = MainScreen.unproj(Gdx.input.getX(), Gdx.input.getY());
                labelMouse.setText(mouseCoord.x + " : " + mouseCoord.y);
            }
        });
        this.labelMouse.addAction(Actions.forever(runnableActionX));
        this.labelMouse.setName("labelMouse");

//        this.labelFPS = createLabel("", 0.5f, new Vector2(1540, 890));
        this.labelFPS = createLabel("", 0.5f, new Vector2(1540, 890));
        RunnableAction runnableActionFPS = new RunnableAction();
        runnableActionFPS.setRunnable(new Runnable() {
            @Override
            public void run() {
                labelFPS.setText(Gdx.graphics.getFramesPerSecond());
            }
        });
        this.labelFPS.addAction(Actions.forever(runnableActionFPS));
    }

    public void draw() {
        this.stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.stage.act();
        this.stage.draw();
    }

    // ************************************************** OWN FUCNTIONS
    // **************************************************


    private Label createLabel(String text, float fontScale, Vector2 point) {
        Label label = new Label(text, this.skin.get("default", LabelStyle.class));
        label.setPosition(point.x, point.y);
//        label.setPosition(point.x - GumesGame.WORLD_WIDTH, point.y - GumesGame.WORLD_HEIGHT);
        label.setFontScale(fontScale);

//        float yyy = 30 * label.getFontScaleY() * ((float)GumesGame.WORLD_HEIGHT/(float)GumesGame.WORLD_WIDTH);
//        label.setPosition(point.x, point.y + yyy );

//        this.stage.addActor(label);
        this.stage.addActor(label);

        return label;
    }

    private TextButton createButton(String text, float x, float y) {
        int lr = 30;
        int ud = 0;
        float fontScale = 0.45f;

        TextButtonStyle btnStyle = new TextButtonStyle();
        btnStyle.font = font;
        btnStyle.up = skin.getDrawable("cell");
        btnStyle.down = skin.getDrawable("cellMouseOnBlocked");
        btnStyle.over = skin.getDrawable("cellMouseOn");
        btnStyle.pressedOffsetX = 1;
        btnStyle.pressedOffsetY = -1;

        TextButton btn = new TextButton(text, btnStyle);
        btn.pad(ud, lr, ud, lr);
        btn.padBottom(ud + 7);
        btn.getLabel().setFontScale(fontScale);

        Table table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setPosition(x, y);
        table.add(btn).height(60);
        btn.setWidth(300);
//        this.stage.addActor(table);
        this.stage.addActor(table);

        return btn;
    }
}

