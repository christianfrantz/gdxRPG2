package com.gdx.rpg.Observer;


import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Quests.Quest;

/**
 * Created by imont_000 on 2/28/2017.
 */
public interface Observer {
    void onNotify(Entity enemy, Event event);

    void onNotify(Player player, Event event);

    void onNotify(Player player, Entity enemy, Event event);

    void onNotify(Quest quest, Event event);
}
