package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gdx.rpg.Components.EnemyUpdateComponent;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.DamageObserver;
import com.gdx.rpg.Observer.EnemySubject;
import com.gdx.rpg.Statics;


/**
 * Created by imont_000 on 2/27/2017.
 */
public class Enemy extends Entity {

    public enum EnemyType{
        SLIME,
        BAT,
        SKELETON
    };


    public EnemyType enemyType;

    public EnemySubject enemySubject;
    public Body chaseBody;
    public float attackForce = 4f;
    public float attackCount;
    public float attackTimer;

    public Enemy(Vector2 position, String id){
        super( position, id);

        enemyState = EnemyState.IDLE;
        enemySubject = new EnemySubject();
        enemySubject.AddObserver(new DamageObserver());
        entityUpdateComponent = new EnemyUpdateComponent(this);
    }

    @Override
    public void createBody(Vector2 position, Texture texture, boolean isSensor){
        sprite.setBounds(0, 0, texture.getWidth() / MainGame.PPM, texture.getHeight() / MainGame.PPM);
        sprite.setOrigin(32 / MainGame.PPM, 32 / MainGame.PPM);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = MainGame.world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() / 2 / MainGame.PPM, texture.getHeight() / 2 / MainGame.PPM);

        body.setUserData(this);
        fixtureDef.shape = shape;
        //fixtureDef.restitution = 3;
        fixtureDef.filter.groupIndex = Statics.ENEMY_FILTER;
        body.createFixture(fixtureDef);

        BodyDef chaseDef = new BodyDef();
        chaseDef.position.set(position.x, position.y);
        chaseDef.type = BodyDef.BodyType.DynamicBody;

        chaseBody = MainGame.world.createBody(chaseDef);

        FixtureDef chaseFixture = new FixtureDef();
        PolygonShape chaseShape = new PolygonShape();
        chaseShape.setAsBox(texture.getWidth() * 3 / MainGame.PPM, texture.getWidth() * 3 / MainGame.PPM);
        chaseBody.setUserData("CHASE_BODY");
        chaseFixture.shape = chaseShape;
        chaseFixture.isSensor = true;
        chaseBody.createFixture(chaseFixture).setUserData(this);

        body.setLinearDamping(5f);
        chaseBody.setLinearDamping(5);

    }
}
