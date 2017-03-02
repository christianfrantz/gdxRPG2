package com.gdx.rpg.Observer;


import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;

/**
 * Created by imont_000 on 2/28/2017.
 */
public interface Observer {
    public void onNotify(Entity enemy, Event event);

    public void onNotify(Player player, Event event);

    public void onNotify(Player player, Entity enemy, Event event);
}
