package dbb.gumes.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import dbb.gumes.gameobject.animation.Animation;

import java.util.ArrayList;

public abstract class GameObject {

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
    }

    public GameObject(TextureRegion textureRegion, Vector2 point, Vector2 size) {
        this.sprite = new Sprite(textureRegion);
        this.sprite.setSize(size.x, size.y);
        this.sprite.setOrigin(size.x / 2f, size.y / 2f);
        this.sprite.setPosition(point.x, point.y);

        this.bounds = new Polygon(new float[] { 0f, 0f, size.x, 0f, size.x, size.y, 0f, size.y });
        this.bounds.setPosition(point.x, point.y);
        this.bounds.setOrigin(size.x / 2f, size.y / 2f);
    }

    public void draw(SpriteBatch batch, Float delta) {
        if(this.animation != null) {
            this.animation.update(delta);
            this.sprite.setRegion(this.animation.getFrame());
        }

        this.sprite.setPosition(this.bounds.getX(), this.bounds.getY());
        this.sprite.setRotation(this.bounds.getRotation());
        this.sprite.draw(batch);
    }

    public abstract void initAnimations(ArrayList<TextureRegion> frames, float cycleTime, boolean isLooping);
}
