package com.gdx.rpg.Observer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.NPC;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Projectile;
import com.gdx.rpg.Quests.Quest;

/**
 * Created by imont_000 on 3/1/2017.
 */
public class ClickObserver implements Observer{

    @Override
    public void onNotify(Entity entity, Event event) {
        switch (event){
            case CLICKED_ENTITY:
                if(entity instanceof NPC && ((NPC) entity).hasQuest) {
                    MainGame.hud.ShowDialogue((NPC) entity);
                    ((NPC) entity).isClicked = true;
                }
                //else
                    System.out.println(entity.id);
        }
    }

    @Override
    public void onNotify(Player player, Event event) {

    }

    @Override
    public void onNotify(Player player, Entity enemy, Event event) {

    }

    @Override
    public void onNotify(Projectile p, Entity enemy, Event event) {

    }

    @Override
    public void onNotify(Quest quest, Event event) {

    }

    @Override
    public void onNotify(Item item, Event event) {
    }
}
