package dbb.gumes.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.corba.se.spi.orbutil.fsm.GuardBase;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.animation.GifDecoder;

import java.awt.Dimension;
import java.util.ArrayList;


public class Player extends VisualObject {

    private static float SPEED = 10 / GumesGame.PPM;
    private static float VELOCITY_LIMIT = 100 / GumesGame.PPM;

    private World world;
    private Body body;

    public Player(World world, TextureRegion texture, Vector2 point, Vector2 size) {
        super(texture, point, size);
        this.world = world;

        definePlayer(point, size);

        Gdx.app.log("log", point.toString());
        Gdx.app.log("log", size.toString());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.setPosition(body.getPosition().x - this.getWidth() / 2, body.getPosition().y - this.getHeight() / 2);
    }

    private void definePlayer(Vector2 point, Vector2 size) {
        point.set(point.x / GumesGame.PPM, point.y / GumesGame.PPM);
        size.set(size.x / GumesGame.PPM, size.y / GumesGame.PPM);

        BodyDef bDef = new BodyDef();
        bDef.position.set(point);
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.gravityScale = 150;

        body = this.world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 2, size.y / 2);
        fDef.shape = shape;

        body.createFixture(fDef);

        Gdx.app.log("log", Float.toString(body.getGravityScale()));
    }

    @Override
    public void initAnimations(ArrayList<TextureRegion> frames, float cycleTime, boolean isLooping) {

    }

    public void moveLeft() {
        if(this.velocityCheck())
            this.body.applyLinearImpulse(new Vector2(-Player.SPEED, 0), this.body.getWorldCenter(), true);
    }
    public void moveRight() {
        if(this.velocityCheck())
            this.body.applyLinearImpulse(new Vector2(Player.SPEED, 0), this.body.getWorldCenter(), true);
    }

    private boolean velocityCheck() {
        return Math.abs(this.body.getLinearVelocity().x) < Player.VELOCITY_LIMIT;
    }
}
