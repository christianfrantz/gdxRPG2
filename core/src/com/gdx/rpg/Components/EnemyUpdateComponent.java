package com.gdx.rpg.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.MainGame;

import java.util.Random;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class EnemyUpdateComponent extends EntityUpdateComponent{

    Enemy entity;

    float moveTimer;
    float moveCount;

    Random random;
    int moveDirection;

    public EnemyUpdateComponent(Enemy entity){
        super(entity);
        this.entity = entity;

        moveTimer = 1;
        moveCount = 0;

        random = new Random();
        moveDirection = random.nextInt(4);
    }

    @Override
    public void Update(){
        entity.sprite.setPosition(entity.body.getPosition().x - entity.sprite.getWidth() / 2, entity.body.getPosition().y - entity.sprite.getHeight() / 2);

        switch (entity.enemyType){
            case SLIME:
                updateSlime();
                break;
            case BAT:
                updateBat();
        }

        checkIfAlive();
    }

    private void updateBat() {
        entity.chaseBody.setTransform(entity.body.getWorldCenter(), 0);

        if(entity.enemyState == Enemy.EnemyState.IDLE) {
            moveCount += Gdx.graphics.getDeltaTime();
            if (moveCount >= moveTimer) {
                moveDirection = random.nextInt(4);
                moveCount = 0;
            }
            switch (moveDirection) {
                case 0:
                    entity.body.applyLinearImpulse(new Vector2(-0.01f, 0), entity.body.getWorldCenter(), true);
                    break;
                case 1:
                    entity.body.applyLinearImpulse(new Vector2(0.01f, 0), entity.body.getWorldCenter(), true);
                    break;
                case 2:
                    entity.body.applyLinearImpulse(new Vector2(0, -0.01f), entity.body.getWorldCenter(), true);
                    break;
                case 3:
                    entity.body.applyLinearImpulse(new Vector2(0, 0.01f), entity.body.getWorldCenter(), true);
                    break;
            }
        }
        else if(entity.enemyState == Entity.EnemyState.ATTACKING){
            //entity.body.applyLinearImpulse(MainGame.player.body.getWorldCenter(), entity.body.getWorldCenter(), true);
        }
    }

    private void updateSlime(){
        entity.chaseBody.setTransform(entity.body.getWorldCenter(), 0);

        if(entity.enemyState == Enemy.EnemyState.IDLE) {
            entity.body.applyLinearImpulse(new Vector2(-0.01f, 0), entity.body.getWorldCenter(), true);
        }
        else if(entity.enemyState == Entity.EnemyState.ATTACKING){
            //entity.body.applyLinearImpulse(new Vector2(MainGame.player.body.getWorldCenter().x / MainGame.PPM, MainGame.player.body.getWorldCenter().y / MainGame.PPM), entity.body.getWorldCenter(), true);
        }
    }
}
