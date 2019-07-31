package dbb.gumes.gameobject.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import dbb.gumes.game.GumesGame;
import dbb.gumes.gameobject.Player;
import dbb.gumes.screen.MainScreen;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected MainScreen screen;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(MainScreen screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / GumesGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / GumesGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / GumesGame.PPM, bounds.getHeight() / 2 / GumesGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public abstract void onSensorHit(Player player);
}
