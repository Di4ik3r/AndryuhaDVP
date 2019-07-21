package dbb.gumes.gameobject.animation;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.GameObject;

import java.util.ArrayList;

public class CutScene extends GameObject {

    public CutScene(ArrayList<TextureRegion> frames, float time, boolean isLooping) {
        super(frames.get(0), 0, 0, GumesGame.WORLD_WIDTH, GumesGame.WORLD_HEIGHT);
        initAnimations(frames, time, isLooping);


    }

    @Override
    public void initAnimations(ArrayList<TextureRegion> frames, float time, boolean isLooping) {
        this.animation = new Animation(frames, time, isLooping);
    }


}
