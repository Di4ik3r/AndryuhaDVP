package dbb.gumes.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.animation.Animation;

import java.awt.*;
import java.util.ArrayList;

public abstract class VisualObject extends Image {

//    private Polygon bounds;
//    private Sprite sprite;
    protected Animation animation;

    public VisualObject(TextureRegion texture, Vector2 point, Vector2 size) {
        super(texture);
        this.setPosition(point.x / GumesGame.PPM, point.y / GumesGame.PPM);
        this.setSize(size.x / GumesGame.PPM, size.y / GumesGame.PPM);
        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.setTouchable(Touchable.enabled);
    }

    @Override
    protected void positionChanged() {
//        this.sprite.setPosition(this.getX(), this.getY());
        super.positionChanged();
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(this.animation != null) {
            this.animation.update(Gdx.graphics.getDeltaTime());
//            this.sprite.setRegion(this.animation.getFrame());
            this.setDrawable(new TextureRegionDrawable(this.animation.getFrame()));
        }

//        super.draw(batch, parentAlpha);
//        this.sprite.draw(batch);

        batch.setColor(this.getColor());
        ((TextureRegionDrawable)this.getDrawable()).draw(batch,
                this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation()
        );
    }

    public abstract void initAnimations(ArrayList<TextureRegion> frames, float cycleTime, boolean isLooping);
}
