package com.gdx.rpg.Observer;


import com.gdx.rpg.Player;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class PlayerSubject extends Subject {

    public void notify(Player player, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(player, event);
        }
    }
}
