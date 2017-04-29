package com.gdx.rpg;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;

import java.util.ArrayList;
import java.util.Iterator;

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
    public Sprite sprite;

    public enum ProjectileType{
        ARROW
    }

    public ProjectileType projectileType;

    public Projectile(Player player, ProjectileType projectileType){
        setupBody(player);
        activeTime = 1f;
        this.projectileType = projectileType;

        switch (projectileType){
            case ARROW:
                setUpProjectile("arrow", player);
                Ray ray = new Ray();
                ray.set(new Vector3(player.body.getPosition().x, player.body.getPosition().y, 0), new Vector3(player.mouseRelativePlayer.x, player.mouseRelativePlayer.y, 0));
                body.applyLinearImpulse(new Vector2(player.mouseRelativePlayer.x, player.mouseRelativePlayer.y), body.getWorldCenter(), true);
        }
    }

    private void setUpProjectile(String texture, Player player){
        sprite = new Sprite(new Texture(texture + ".png"));
        sprite.setBounds(0, 0, sprite.getTexture().getWidth() / MainGame.PPM, sprite.getTexture().getHeight() / MainGame.PPM);

        sprite.setOrigin(body.getWorldCenter().x / MainGame.PPM, body.getWorldCenter().y / MainGame.PPM);
        //sprite.setOrigin(sprite.getTexture().getWidth() / MainGame.PPM, sprite.getTexture().getHeight() / MainGame.PPM);
        sprite.rotate(player.mouseRelativePlayer.angle());
    }

    public void update(float dt, ArrayList<Projectile> projectiles){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        if(isActive){
            timer += dt;
        }
        if(timer >= activeTime){
            isActive = false;
            Destroy(this, projectiles);
        }
    }

    public void setupBody(Player player){
        BodyDef bodyDef = new BodyDef();
        float x = player.body.getPosition().x;
        float y = player.body.getPosition().y;
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

    private void Destroy(Projectile projectile, ArrayList<Projectile> projectiles){
        projectile.body.setActive(false);
        //projectiles.remove(projectile);
        /*for(Iterator<Projectile> it = projectiles.iterator(); it.hasNext();){
            Projectile e = it.next();
            if(e.isActive == false){
                it.remove();
            }
        }*/
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
