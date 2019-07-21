package dbb.gumes.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dbb.gumes.gameobject.animation.Animation;
import java.util.ArrayList;

public abstract class GameObject extends Actor {

    private Polygon bounds;
    private Sprite sprite;
    protected Animation animation;

    public GameObject(TextureRegion textureRegion, float x, float y, float width, float height) {
        this.sprite = new Sprite(textureRegion);
        this.sprite.setSize(width, height);
        this.sprite.setOrigin(width / 2f, height / 2f);
        this.sprite.setPosition(x, y);

        this.bounds = new Polygon(new float[] { 0f, 0f, width, 0f, width, height, 0f, height });
        this.bounds.setPosition(x, y);
        this.bounds.setOrigin(width / 2f, height / 2f);

        this.defaultActorCommands(new Vector2(width, height));
    }

    public GameObject(TextureRegion textureRegion, Vector2 point, Vector2 size) {
        this.sprite = new Sprite(textureRegion);
        this.sprite.setSize(size.x, size.y);
        this.sprite.setOrigin(size.x / 2f, size.y / 2f);
        this.sprite.setPosition(point.x, point.y);

        this.bounds = new Polygon(new float[] { 0f, 0f, size.x, 0f, size.x, size.y, 0f, size.y });
        this.bounds.setPosition(point.x, point.y);
        this.bounds.setOrigin(size.x / 2f, size.y / 2f);

        this.defaultActorCommands(size);
    }

    @Override
    protected void positionChanged() {
        this.sprite.setPosition(this.getX(), this.getY());
        super.positionChanged();
    }

    private void defaultActorCommands(Vector2 size) {
        this.setBounds(this.sprite.getX(), sprite.getY(), size.x, size.y);
        setTouchable(Touchable.enabled);


    }

//    public void draw(SpriteBatch batch, Float delta) {
//        if(this.animation != null) {
//            this.animation.update(delta);
//            this.sprite.setRegion(this.animation.getFrame());
//        }
//
////        this.sprite.setPosition(this.bounds.getX(), this.bounds.getY());
////        this.sprite.setRotation(this.bounds.getRotation());
//
//        this.sprite.draw(batch);
//
//        batch.setColor(this.getColor());
//
//        Gdx.app.log("log", "draw");
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(this.animation != null) {
            this.animation.update(Gdx.graphics.getDeltaTime());
            this.sprite.setRegion(this.animation.getFrame());
        }

//        super.draw(batch, parentAlpha);

        this.sprite.draw(batch);
        batch.setColor(this.getColor());
    }

    public abstract void initAnimations(ArrayList<TextureRegion> frames, float cycleTime, boolean isLooping);
}
