package com.gdx.rpg;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Entities.Player;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Projectile implements ContactListener{
    public Vector2 position;
    public boolean isActive;
    public Body body;
    public float timer;
    public float activeTime;

    public Projectile(Player player){
        setupBody(player);
        activeTime = 1f;
    }

    public void update(float dt, Player player){
        if(isActive){
            Ray ray = new Ray();
            ray.set(new Vector3(player.body.getLinearVelocity().x, player.body.getPosition().y, 0), new Vector3(player.mouseRelativePlayer.x, player.mouseRelativePlayer.y, 0));
            body.applyLinearImpulse(new Vector2(ray.direction.x, ray.direction.y), body.getWorldCenter(), true);
            timer += dt;
        }
        if(timer >= activeTime){
            isActive = false;
            Destroy(this);
        }
    }

    public void setupBody(Player player){
        BodyDef bodyDef = new BodyDef();
        float x = player.body.getPosition().x + player.mouseRelativePlayer.x;
        float y = player.body.getPosition().y + player.mouseRelativePlayer.y;
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = MainGame.world.createBody(bodyDef);
        body.setUserData(this);
        FixtureDef def = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MainGame.PPM);
        def.shape = shape;
        body.createFixture(def);
    }

    private void Destroy(Projectile projectile){
        projectile.body.setActive(false);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.println("SDF");
        MainGame.world.destroyBody(body);
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
