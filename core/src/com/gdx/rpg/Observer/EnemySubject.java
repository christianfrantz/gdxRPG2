package com.gdx.rpg.Observer;

import com.gdx.rpg.Enemy.Enemy;
import com.gdx.rpg.Player;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class EnemySubject extends Subject{

    public void notify(Enemy enemy, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(enemy, event);
            System.out.println(i);
        }
    }

    public void notify(Player player, Enemy enemy, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(player, enemy, event);
        }
    }
}
