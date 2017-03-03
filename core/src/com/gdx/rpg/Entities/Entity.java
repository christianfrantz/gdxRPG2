package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gdx.rpg.Components.EntityUpdateComponent;
import com.gdx.rpg.MainGame;

/**
 * if object is entity, set sprite, health
 */
public class Entity {
    public enum Direction{
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    public enum EnemyState{
        NULL,
        IDLE,
        ATTACKING
    }

    public EnemyState enemyState;
    public Direction direction;
    public Sprite sprite;
    public Body body;
    private float speed;

    public int health;
    public String id;

    public boolean flaggedForDelete = false;
    public EntityUpdateComponent entityUpdateComponent;

    public Entity( Vector2 position, String id){
        speed = 2;
        direction = Direction.DOWN;
        enemyState = EnemyState.NULL;
        this.id = id;

        entityUpdateComponent = new EntityUpdateComponent(this);
    }

    public void createBody(Vector2 position, Texture texture){
        sprite.setBounds(0, 0, texture.getWidth() / MainGame.PPM, texture.getHeight() / MainGame.PPM);
        sprite.setOrigin(32 / MainGame.PPM, 32 / MainGame.PPM);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation = true;
        body = MainGame.world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() / 2 / MainGame.PPM, texture.getHeight() / 2 / MainGame.PPM);

        body.setUserData(this);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0.001f;
        fixtureDef.density = 100f;

        body.createFixture(fixtureDef);
    }
}
