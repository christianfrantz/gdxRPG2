package com.gdx.rpg.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.Enemy.Enemy.EnemyType;
import com.gdx.rpg.MainGame;


/**
 * Created by imont_000 on 2/27/2017.
 */
public  class EnemyFactory {

    public int idNumber = 0;

    public Enemy createEnemy(EnemyType type, Vector2 position){

        Enemy enemy = null;

        switch (type){
            case SLIME:
                String id = "slime" + idNumber;
                enemy = new Slime(position, new Texture("slime.png"), id);
                MainGame.enemies.put(id, enemy);
                break;
        }

        idNumber++;
        return enemy;
    }
}
