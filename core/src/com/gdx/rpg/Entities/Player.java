package com.gdx.rpg.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Components.PlayerInputComponent;
import com.gdx.rpg.Components.PlayerPhysicsComponent;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.ClickObserver;
import com.gdx.rpg.Observer.DamageObserver;
import com.gdx.rpg.Observer.PlayerSubject;
import com.gdx.rpg.Observer.QuestObserver;
import com.gdx.rpg.Quests.Quest;

import java.util.ArrayList;


/**
 * Created by imont_000 on 2/23/2017.
 */
public class Player extends Entity {
    public int health = 100;
    public int damage = 5;

    public enum PlayerState{
        IDLE,
        MOVING,
        ATTACKING
    }

    private PlayerPhysicsComponent physicsComponent;
    private PlayerInputComponent inputComponent;

    public Body body;
    public Body attackBody;

    private float speed = 0.5f;
    public PlayerState playerState;

    private float attackTime = 0.5f;
    public float attackCounter = 0;

    public PlayerSubject playerSubject;
    public ArrayList<Quest> playerQuests;

    public Sprite cursor;
    public float angle;

    public boolean needToMove = false;

    public Player( Vector2 position){
        super( position, "PLAYER");

        sprite = new Sprite(new Texture("player.png"));
        sprite.setBounds(0, 0, 64 / MainGame.PPM, 64 / MainGame.PPM);
        sprite.setOrigin(32 / MainGame.PPM, 32 / MainGame.PPM);

        direction = Direction.DOWN;
        playerState = PlayerState.IDLE;

        playerSubject = new PlayerSubject();
        playerSubject.AddObserver(new DamageObserver());
        playerSubject.AddObserver(new ClickObserver());
        playerSubject.AddObserver(new QuestObserver());

        inputComponent = new PlayerInputComponent(this);
        physicsComponent = new PlayerPhysicsComponent(this, position);

        cursor = new Sprite(new Texture("cursor.png"));
        cursor.setBounds(0, 0, 16 / MainGame.PPM, 16 / MainGame.PPM);

        playerQuests = new ArrayList<Quest>();
    }

    public void updatePlayer(float delta, Camera cam){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

        Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(pos);
        cursor.setPosition(pos.x - (8 /MainGame.PPM), pos.y - (8 / MainGame.PPM));

        Vector2 mouseRelativePlayer = new Vector2(cursor.getX() - sprite.getX(), cursor.getY() - sprite.getY());
        angle = mouseRelativePlayer.angle();

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
