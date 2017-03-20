package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gdx.rpg.Components.EntityUpdateComponent;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.Event;

import java.util.Iterator;

/**
 * if object is entity, set sprite, health
 */
public class Entity {
    public float attackForce = 4;
    public enum Direction{
        LEFT,
        RIGHT,
        DOWN,
        UP,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
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

    public boolean isEnemy = false;
    public boolean flaggedForDelete = false;
    public EntityUpdateComponent entityUpdateComponent;

    public Item itemToDrop;

    public Entity( Vector2 position, String id){
        speed = 2;
        direction = Direction.DOWN;
        enemyState = EnemyState.NULL;
        this.id = id;
        itemToDrop = null;

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

    public void Destroy(){
        for(int i = 0; i < body.getFixtureList().size; i++){
            body.destroyFixture(body.getFixtureList().get(i));
        }
        for(Iterator<Entity> it = MainGame.currentMap.mapEntities.iterator(); it.hasNext();){
            Entity e = it.next();
            if(e.flaggedForDelete){
                it.remove();
            }
        }
    }

    public void IsKilled(){
        for(int i = 0; i < body.getFixtureList().size; i++){
            body.destroyFixture(body.getFixtureList().get(i));
        }
        for(Iterator<Entity> it = MainGame.currentMap.mapEntities.iterator(); it.hasNext();){
            Entity e = it.next();
            if(e.flaggedForDelete){
                it.remove();
            }
        }

        MainGame.player.playerSubject.notify(this, Event.UPDATE_KILL_QUEST);
        MainGame.player.inventory.AddItem(itemToDrop);
        MainGame.player.playerSubject.notify(itemToDrop, Event.UPDATE_FETCH_QUEST);
    }
}
