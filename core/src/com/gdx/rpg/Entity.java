package com.gdx.rpg;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class Entity {
    public enum Direction{
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    public Direction direction;
    public Sprite sprite;
    public Body body;
    private float speed;

    public int health;
    public String id;

    public Entity(Texture texture, Vector2 position){
        sprite = new Sprite(texture);
        speed = 2;

        //createBody(position, texture);
        direction = Direction.DOWN;
    }

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
}
