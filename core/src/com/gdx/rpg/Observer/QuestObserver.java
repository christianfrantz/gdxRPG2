package com.gdx.rpg.Observer;

import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
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
                if (!MainGame.player.playerQuests.contains(quest))
                    MainGame.player.playerQuests.add(quest);
                System.out.println(quest.questDescription + " added " + quest.questRequirement.numberKilled);
                System.out.println(MainGame.player.playerQuests.size());
                break;

            case COMPLETE_QUEST:

                break;
        }

    }


    @Override
    public void onNotify(Entity enemy, Event event) {
        switch (event){
            case UPDATE_QUEST:
                Enemy e = (Enemy)enemy;
                Quest quest;

                for(int i = 0; i < MainGame.player.playerQuests.size(); i++){
                    if(MainGame.player.playerQuests.get(i).equals(MainGame.availableQuests.get(Statics.KILL_SLIMES)) &&
                            MainGame.availableQuests.get(Statics.KILL_SLIMES).questRequirement.enemyNeeded.equals(e.enemyType)){
                        quest = MainGame.player.playerQuests.get(i);
                        quest.questRequirement.numberKilled++;
                        System.out.println("UPDATE QUEST " + quest.questDescription + "Slime " + quest.questRequirement.numberKilled);
                        if(quest.questRequirement.numberKilled == quest.questRequirement.numberNeeded){
                            quest.questCompleted = true;
                        }
                    }
                }
        }
    }

    @Override
    public void onNotify(Player player, Event event) {

    }

    @Override
    public void onNotify(Player player, Entity enemy, Event event) {

    }

    /*private void checkQuest(Entity enemy){
        switch ((Enemy)enemy.enemyType){
            Quest quest;
            if(Enemy.EnemyType.SLIME == enemy.enemyType && MainGame.player.playerQuests.contains(Statics.KILL_SLIMES)){
                quest.questRequirement.numberKilled++;
                System.out.println("Slime " + quest.questRequirement.numberKilled);
                if(quest.questRequirement.numberKilled == quest.questRequirement.numberNeeded){
                    quest.questCompleted = true;
                }
            }
        }
    }*/
}
