package dbb.gumes.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        Gdx.app.log("contact", "begin");

        if (a.getUserData() == "sensor" || b.getUserData() == "sensor") {
            Fixture sensor = a.getUserData() == "sensor" ? a : b;
            Fixture object = a.getUserData() == sensor ? b : a;

            Gdx.app.log("contact", sensor.toString());
            Gdx.app.log("contact", object.toString());
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
