package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.Components.EnemyUpdateComponent;
import com.gdx.rpg.Entities.Enemy.EnemyType;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;

import java.util.ArrayList;


/**
 * For each entity create id first
 * entity = new entity(position, id)
 * create sprite
 * createBody()
 * set health
 * set type
 * set update component if needed
 * add to MainGame.entities
 */
public  class EnemyFactory {

    public int idNumber = 0;

    public Enemy createEnemy(EnemyType type, Vector2 position){

        Enemy enemy = null;
        String id;
        switch (type){
            case SLIME:
                id = "slime" + idNumber;
                enemy = new Enemy(position, id);
                enemy.isEnemy = true;
                enemy.sprite = new Sprite(new Texture("slime.png"));
                enemy.createBody(position, enemy.sprite.getTexture());
                enemy.health = 10;
                enemy.enemyType = EnemyType.SLIME;
                enemy.itemToDrop = new Item("slimeGoo");
                break;
            case BAT:
                id = "bat" + idNumber;
                enemy = new Enemy(position, id);
                enemy.isEnemy = true;
                enemy.sprite = new Sprite(new Texture("bat.png"));
                enemy.createBody(position, enemy.sprite.getTexture());
                enemy.health = 10;
                enemy.enemyType = EnemyType.BAT;
                enemy.itemToDrop = new Item("batWing");
                break;
        }

        idNumber++;
        return enemy;
    }
}
