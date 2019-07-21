package dbb.gumes.gameobject.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Animation {

    private ArrayList<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    private boolean looping;

    private Vector2 size;
    private Vector2 position;

    public Animation(ArrayList<TextureRegion> frames, float cycleTime, boolean looping) {
        this.frames = frames;
        this.frameCount = frames.size();
        maxFrameTime = cycleTime / frameCount;
        frame = 0;

        this.looping = looping;
    }


    public void setDeltaPosition(float x, float y) {
        this.position = new Vector2(x, y);
    }

    public Vector2 getDeltaPosition() {
        return this.position;
    }

    public void setSize(int width, int height) {
        this.size = new Vector2(width, height);
    }

    public Vector2 getSize() {
        return this.size;
    }

    public void refresh() {
        this.currentFrameTime = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            frame = 0;
        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame) ;
    }
}
