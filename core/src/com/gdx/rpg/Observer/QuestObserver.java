package com.gdx.rpg.Observer;

import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Quests.Quest;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class QuestObserver implements Observer{

    public void onNotify(Quest quest, Event event){
        switch (event){
            case ACCEPT_QUEST:
                if(!MainGame.player.playerQuests.contains(quest))
                    MainGame.player.playerQuests.add(quest);
                System.out.println(quest.questDescription + " added");
                System.out.println(MainGame.player.playerQuests.size());
                break;
            case UPDATE_QUEST:

                break;
        }

    }

    @Override
    public void onNotify(Entity enemy, Event event) {

    }

    @Override
    public void onNotify(Player player, Event event) {

    }

    @Override
    public void onNotify(Player player, Entity enemy, Event event) {

    }
}
