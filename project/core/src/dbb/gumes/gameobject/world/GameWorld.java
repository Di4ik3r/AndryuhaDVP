package dbb.gumes.gameobject.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import dbb.gumes.game.GumesGame;

public class GameWorld {


    private World world;
    private TiledMap map;

    public GameWorld(World world, TiledMap map) {
        this.world = world;
        this.map = map;

        generateFromLayer(2);
        generateFromLayer(3);
        generateFromLayer(4);
        generateFromLayer(5);
    }

    private void generateFromLayer(int i) {
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        for(MapObject object : this.map.getLayers().get(i).getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / GumesGame.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / GumesGame.PPM);

            body = this.world.createBody(bDef);

            shape.setAsBox(rectangle.getWidth() / 2 / GumesGame.PPM, rectangle.getHeight() / 2 / GumesGame.PPM);
            fDef.shape = shape;

            body.createFixture(fDef);
        }
    }
}
