package com.gdx.rpg.Observer;


import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Item;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class PlayerSubject extends Subject {

    public void notify(Player player, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(player, event);
        }
    }

    public void notify(Entity entity, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(entity, event);
        }
    }

    public void notify(Item item, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(item, event);
        }
    }

}
