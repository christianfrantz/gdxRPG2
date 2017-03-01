package com.gdx.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Components.PlayerInputComponent;
import com.gdx.rpg.Components.PlayerPhysicsComponent;
import com.gdx.rpg.Observer.DamageObserver;
import com.gdx.rpg.Observer.PlayerSubject;


/**
 * Created by imont_000 on 2/23/2017.
 */
public class Player extends Entity{
    public int health = 100;
    public int damage = 5;

    public enum PlayerState{
        IDLE,
        MOVING,
        ATTACKING
    }

    private PlayerPhysicsComponent physicsComponent;
    private PlayerInputComponent inputComponent;

    private World world;
    public Body body;
    public Body attackBody;

    private float speed = 0.5f;
    public PlayerState playerState;

    private float attackTime = 0.5f;
    public float attackCounter = 0;

    public PlayerSubject playerSubject;

    public Player(Texture texture, Vector2 position){
        super(texture, position);
        this.world = MainGame.world;

        sprite = new Sprite(texture);
        sprite.setBounds(0, 0, 64 / MainGame.PPM, 64 / MainGame.PPM);
        sprite.setOrigin(32 / MainGame.PPM, 32 / MainGame.PPM);

        direction = Direction.DOWN;
        playerState = PlayerState.IDLE;

        playerSubject = new PlayerSubject();
        playerSubject.AddObserver(new DamageObserver());

        inputComponent = new PlayerInputComponent(this);
        physicsComponent = new PlayerPhysicsComponent(this, position);
    }

    public void updatePlayer(float delta){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

        inputComponent.updateInput(this);
        physicsComponent.updatePhysics(this, speed);

        switch (playerState){
            case IDLE:
                attackBody.setActive(false);
                break;
            case ATTACKING:
            {
                attackCounter += Gdx.graphics.getDeltaTime();
                attackBody.setActive(true);
                if(attackCounter >= attackTime){
                    playerState = PlayerState.IDLE;
                    attackCounter = 0;
                }
            }
        }

    }
}
