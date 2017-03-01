package com.gdx.rpg.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Entity;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.DamageObserver;
import com.gdx.rpg.Observer.EnemySubject;


/**
 * Created by imont_000 on 2/27/2017.
 */
public class Enemy extends Entity {

    public enum EnemyType{
        SLIME
    };


    public boolean flaggedForDelete = false;

    public EnemyType enemyType;

    public EnemySubject enemySubject;

    public Enemy(Vector2 position, Texture texture){
        super(texture, position);
        sprite = new Sprite(texture);

        enemySubject = new EnemySubject();
        enemySubject.AddObserver(new DamageObserver());
    }

    @Override
    public void createBody(Vector2 position, Texture texture){
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
        body.createFixture(fixtureDef);
    }

    public void UpdateEnemy(){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

        checkIfAlive();
    }

    public void checkIfAlive(){
        if(health <= 0) {
            MainGame.enemies.values().remove(this);
            flaggedForDelete = true;
        }

    }
}
