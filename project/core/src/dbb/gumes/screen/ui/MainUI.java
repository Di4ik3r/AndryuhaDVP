package dbb.gumes.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import dbb.gumes.game.GumesGame;
import dbb.gumes.screen.MainScreen;

public class MainUI extends Group {

    private Skin skin;
    private BitmapFont font;
    private Label labelMouse, labelFPS, labelLength;
    private Vector2 unprojectedCoordinates;

    public MainUI() {
        this.skin = new Skin(Gdx.files.internal("skin.json"));
        this.font = new BitmapFont(Gdx.files.internal("font.fnt"));

        unprojectedCoordinates = MainScreen.unproj(Gdx.input.getX(), Gdx.input.getY());

        this.labelMouse = createLabel("", 0.5f, new Vector2(0, 30));
            RunnableAction runnableActionX = new RunnableAction();
            runnableActionX.setRunnable(new Runnable() {
                @Override
                public void run() {
                    unprojectedCoordinates = MainScreen.unproj(Gdx.input.getX(), Gdx.input.getY());
                    labelMouse.setText(unprojectedCoordinates.x + " : " + unprojectedCoordinates.y);
                }
            });
        this.labelMouse.addAction(Actions.forever(runnableActionX));
        this.labelMouse.setName("labelMouse");

        this.labelFPS = createLabel("", 0.5f, new Vector2(1540, 890));
            RunnableAction runnableActionFPS = new RunnableAction();
            runnableActionFPS.setRunnable(new Runnable() {
                @Override
                public void run() {
                    labelFPS.setText(Gdx.graphics.getFramesPerSecond());
                }
            });
        this.labelFPS.addAction(Actions.forever(runnableActionFPS));


        RunnableAction runnableActionGroup = new RunnableAction();
        runnableActionGroup.setRunnable(new Runnable() {
            @Override
            public void run() {
                MainUI.this.setPosition(MainScreen.camera.position.x - GumesGame.WORLD_WIDTH/2, MainScreen.camera.position.y - GumesGame.WORLD_HEIGHT/2);
            }
        });
        this.addAction(Actions.forever(runnableActionGroup));
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
        this.addActor(label);

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
        this.addActor(table);

        return btn;
    }

    public void sendInfo(Vector3 vector, float length) {
        this.labelMouse.setText(Float.toString(vector.x));
//        this.labelY.setText(Float.toString(vector.y));
        this.labelLength.setText("Length: " + Float.toString(length));

    }
}
