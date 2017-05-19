package com.gdx.rpg.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Projectile;

import java.rmi.activation.ActivationGroup_Stub;
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
    float movementSpeed = 0.1f;

    public EnemyUpdateComponent(Enemy entity){
        super(entity);
        this.entity = entity;

        moveTimer = 2;
        moveCount = 0;

        random = new Random();
        moveDirection = random.nextInt(4);
    }

    private void randomMove(){
        moveCount += Gdx.graphics.getDeltaTime();
        if (moveCount >= moveTimer) {
            moveDirection = random.nextInt(4);
            moveCount = 0;
        }
        switch (moveDirection) {
            case 0:
                entity.body.applyLinearImpulse(new Vector2(-movementSpeed, 0), entity.body.getWorldCenter(), true);
                break;
            case 1:
                entity.body.applyLinearImpulse(new Vector2(movementSpeed, 0), entity.body.getWorldCenter(), true);
                break;
            case 2:
                entity.body.applyLinearImpulse(new Vector2(0, -movementSpeed), entity.body.getWorldCenter(), true);
                break;
            case 3:
                entity.body.applyLinearImpulse(new Vector2(0, movementSpeed), entity.body.getWorldCenter(), true);
                break;
        }
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
                break;
            case SKELETON:
                updateSkeleton();
                break;
        }

        checkIfAlive();
    }

    private void updateBat() {
        entity.chaseBody.setTransform(entity.body.getWorldCenter(), 0);

        if(entity.enemyState == Enemy.EnemyState.IDLE) {
            randomMove();
        }
        else if(entity.enemyState == Entity.EnemyState.ATTACKING){
            float speed = 10f;
            Vector2 force = new Vector2();
            force.set(MainGame.player.body.getPosition()).sub(entity.body.getPosition()).nor().scl(speed);
            entity.body.applyForceToCenter(force, true);
        }
    }

    private void updateSkeleton(){
        entity.chaseBody.setTransform(entity.body.getWorldCenter(), 0);

        if(entity.enemyState == Enemy.EnemyState.IDLE) {
            randomMove();
        }
        else if(entity.enemyState == Entity.EnemyState.ATTACKING){
            float speed = 10f;
            Vector2 force = new Vector2();
            force.set(MainGame.player.body.getPosition()).sub(entity.body.getPosition()).nor().scl(speed);
            entity.body.applyForceToCenter(force, true);
        }
    }

    private void updateSlime(){
        entity.chaseBody.setTransform(entity.body.getWorldCenter(), 0);

        if(entity.enemyState == Enemy.EnemyState.IDLE) {
            randomMove();
        }
        else if(entity.enemyState == Entity.EnemyState.ATTACKING){
            randomMove();
            entity.attackCount += Gdx.graphics.getDeltaTime();
            if(entity.attackCount >= entity.attackTimer){
                Projectile p = new Projectile(entity, Projectile.ProjectileType.SLIME_SHOT, MainGame.player.body.getWorldCenter());
                //MainGame.projectilesOnScreen.add(p);
                entity.attackCount = 0;
            }
            float speed = 10f;
            /*Vector2 force = new Vector2();
            force.set(MainGame.player.body.getPosition()).sub(entity.body.getPosition()).nor().scl(speed);
            entity.body.applyForceToCenter(force, true);*/
        }

    }
}
