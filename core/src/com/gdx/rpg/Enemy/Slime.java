package com.gdx.rpg.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by imont_000 on 2/27/2017.
 */
public class Slime extends Enemy {

    public Slime(Vector2 position, Texture texture, String id) {
        super(position, texture);
        sprite = new Sprite(new Texture("slime.png"));
        this.id = id;

        createBody(position, sprite.getTexture());
        health = 10;
        enemyType = EnemyType.SLIME;
    }

    @Override
    public void UpdateEnemy(){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

        body.applyLinearImpulse(new Vector2(-0.01f, 0), body.getWorldCenter(), true);

        checkIfAlive();
    }
}
