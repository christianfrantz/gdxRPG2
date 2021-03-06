package com.gdx.rpg.Observer;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Item;
import com.gdx.rpg.Projectile;
import com.gdx.rpg.Quests.Quest;

/**
 * Created by imont_000 on 2/28/2017.
 */
public interface Observer {
    void onNotify(Entity enemy, Event event);

    void onNotify(Player player, Event event);

    void onNotify(Player player, Entity enemy, Event event);

    void onNotify(Projectile p, Entity enemy, Event event);

    void onNotify(Quest quest, Event event);

    void onNotify(Item item, Event event);

}
