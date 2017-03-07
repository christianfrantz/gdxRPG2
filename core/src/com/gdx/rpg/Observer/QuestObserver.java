package com.gdx.rpg.Observer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.NPC;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Quests.Quest;
import com.gdx.rpg.Statics;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class QuestObserver implements Observer{

    public void onNotify(Quest quest, Event event) {
        switch (event) {
            case ACCEPT_QUEST:
                if (!MainGame.playerQuests.contains(quest))
                    MainGame.playerQuests.add(quest);
                System.out.println(quest.questDescription + " added " + quest.questRequirement.numberKilled);
                System.out.println(MainGame.playerQuests.size());
                break;

            case COMPLETE_QUEST:
                NPC npc = (NPC)quest.questGiver;
                npc.currentDialogue = quest.afterQuest;
                System.out.println("QUEST COMPLETED " + quest.questDescription);
                if(quest.questType == Quest.QuestType.FETCH && ((NPC) quest.questGiver).isClicked){
                    for(int i = 0; i < quest.questRequirement.numberNeeded; i++){
                        MainGame.player.inventory.RemoveItem(quest.questRequirement.itemNeeded);
                        System.out.println("SDFSDF");
                    }
                }
                break;
        }

    }

    @Override
    public void onNotify(Item item, Event event) {
        Quest quest;
        switch (event){
            case UPDATE_FETCH_QUEST:

                for(int i = 0; i < MainGame.playerQuests.size(); i++){
                    if(MainGame.playerQuests.get(i).questType == Quest.QuestType.FETCH
                            && MainGame.playerQuests.get(i).questRequirement.itemNeeded == item){
                        quest = MainGame.playerQuests.get(i);
                        for(int x = 0; x < MainGame.player.inventory.inventorySlots.length; x++){
                            if(quest.questRequirement.itemNeeded == MainGame.player.inventory.inventorySlots[x].itemInSlot
                                    && quest.questRequirement.numberNeeded == MainGame.player.inventory.inventorySlots[x].itemCount){
                                quest.questCompleted = true;
                                onNotify(quest, Event.COMPLETE_QUEST);
                            }
                        }
                    }
                }
                break;
        }
    }


    @Override
    public void onNotify(Entity enemy, Event event) {
        switch (event){
            case UPDATE_KILL_QUEST:
                Enemy e = (Enemy)enemy;
                Quest quest;

                for(int i = 0; i < MainGame.playerQuests.size(); i++) {
                    if (MainGame.playerQuests.get(i).questType == Quest.QuestType.KILL &&
                            MainGame.playerQuests.get(i).questRequirement.enemyNeeded == ((Enemy) enemy).enemyType) {
                        quest = MainGame.playerQuests.get(i);
                        quest.questRequirement.numberKilled++;
                        System.out.println("UPDATE QUEST " + quest.questDescription + " " + quest.questRequirement.numberKilled);
                        if (quest.questRequirement.numberKilled == quest.questRequirement.numberNeeded) {
                            quest.questCompleted = true;
                            onNotify(quest, Event.COMPLETE_QUEST);
                        }
                    }
                    break;
                }
        }
    }

    @Override
    public void onNotify(Player player, Event event) {

    }

    @Override
    public void onNotify(Player player, Entity enemy, Event event) {

    }
}
