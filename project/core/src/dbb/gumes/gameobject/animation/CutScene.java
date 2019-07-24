package dbb.gumes.gameobject.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.VisualObject;

import java.awt.Dimension;
import java.util.ArrayList;

public class CutScene extends VisualObject {

    public CutScene(ArrayList<TextureRegion> frames, float time, boolean isLooping) {
//        super(frames.get(0), 0, 0, GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT);
        super(frames.get(0), new Vector2(0, 0), new Dimension(GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT));
        initAnimations(frames, time, isLooping);

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.A:
                        Gdx.app.log("log", "a pressed");
                        ColorAction ca = new ColorAction();
                        ca.setEndColor(Color.RED);
                        ca.setDuration(2f);
                        CutScene.this.addAction(ca);
                        break;

                    case Input.Keys.B:
                        Gdx.app.log("log", "b pressed");
                        MoveByAction ma = new MoveByAction();
                        ma.setAmount(1, 1);
                        ma.setDuration(2f);
                        CutScene.this.addAction(ma);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initAnimations(ArrayList<TextureRegion> frames, float time, boolean isLooping) {
        this.animation = new Animation(frames, time, isLooping);
    }


}
