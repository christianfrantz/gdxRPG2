package com.gdx.rpg;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;

import java.util.ArrayList;

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
    public Vector2 target;

    private float speed;

    private ParticleEffectPool.PooledEffect particleEffect;

    public enum ProjectileType{
        FIREBALL,
        SLIME_SHOT
    }

    public ProjectileType projectileType;

    public Projectile(Entity entity, ProjectileType projectileType, Vector2 target){
        activeTime = 3f;
        this.projectileType = projectileType;
        this.target = target;

        switch (projectileType) {
            case SLIME_SHOT:
                speed = 3;
                setUpProjectile("slimeshot", entity);
                break;
            case FIREBALL:
                speed = 4;
                setUpProjectile("fireball", entity);
                particleEffect = MainGame.fireballPool.obtain();
                particleEffect.setPosition(body.getWorldCenter().x, body.getWorldCenter().y);
                particleEffect.start();
                MainGame.effects.add(particleEffect);
                break;
        }
    }

    private void setUpProjectile(String texture, Entity entity){
        setupBody(entity);
        sprite = new Sprite(Assets.GetTexture(texture));
        sprite.setBounds(0, 0, sprite.getTexture().getWidth() / MainGame.PPM, sprite.getTexture().getHeight() / MainGame.PPM);
        sprite.setOrigin(body.getWorldCenter().x / MainGame.PPM, body.getWorldCenter().y / MainGame.PPM);

        isActive = true;
        MainGame.projectilesOnScreen.add(this);
        Vector2 shotDirection = new Vector2(0, 0);
        shotDirection.x = target.x - entity.sprite.getX();
        shotDirection.y = target.y - entity.sprite.getY();
        shotDirection.nor();
        body.applyLinearImpulse(new Vector2(shotDirection.x * speed, shotDirection.y * speed), body.getWorldCenter(), true);
    }

    public void update(float dt, ArrayList<Projectile> projectiles){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        if(particleEffect != null){
            particleEffect.setPosition(body.getPosition().x, body.getPosition().y);
        }
        if(isActive){
            timer += dt;
        }
        if(timer >= activeTime){
            isActive = false;
            Destroy(this, projectiles);
            if(particleEffect != null){
                particleEffect.dispose();
            }
        }
    }

    public void setupBody(Entity entity){
        BodyDef bodyDef = new BodyDef();
        float x = entity.body.getPosition().x;
        float y = entity.body.getPosition().y;
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = MainGame.world.createBody(bodyDef);
        FixtureDef def = new FixtureDef();
        if(entity instanceof Enemy){
            def.filter.groupIndex = Statics.ENEMY_PROJECTILE_FILTER;
            body.setUserData(Statics.ENEMY_PROJECTILE);
        }
        else{
            def.filter.groupIndex = Statics.PLAYER_PROJECTILE_FILTER;
            body.setUserData(Statics.PLAYER_PROJECTILE);
        }
       // def.isSensor = true;
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MainGame.PPM);
        def.shape = shape;
        body.createFixture(def);
    }

    public void Destroy(Projectile projectile, ArrayList<Projectile> projectiles){
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
