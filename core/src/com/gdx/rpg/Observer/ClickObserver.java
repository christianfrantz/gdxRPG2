package com.gdx.rpg.Observer;

import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.NPC;
import com.gdx.rpg.Entities.Player;

/**
 * Created by imont_000 on 3/1/2017.
 */
public class ClickObserver implements Observer{

    @Override
    public void onNotify(Entity entity, Event event) {
        switch (event){
            case CLICKED_ENTITY:
                if(entity instanceof NPC)
                System.out.println(entity.id + " " + ((NPC)entity).dialogue);
                else
                    System.out.println(entity.id);
        }
    }

    @Override
    public void onNotify(Player player, Event event) {

    }

    @Override
    public void onNotify(Player player, Entity enemy, Event event) {

    }
}
