package com.gdx.rpg.Quests;

import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Item;
import com.gdx.rpg.Statics;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class QuestRequirement {
    public int numberKilled;
    public int numberNeeded;

    public Enemy.EnemyType enemyNeeded;
    public String itemNeeded;

    public QuestRequirement(Quest quest, Quest.QuestType questType){
        init(quest, questType);
    }

    private void init(Quest quest, Quest.QuestType questType){
        switch (questType){
            case KILL:
                if(quest.questDescription == Statics.KILL_SLIMES) {
                    numberNeeded = 3;
                    enemyNeeded = Enemy.EnemyType.SLIME;
                    quest.beforeQuest = "Kill 3 slimes";
                    quest.duringQuest = "Come back when you kill 3 slimes";
                    quest.afterQuest = "Thank you";
                }
                if(quest.questDescription == Statics.KILL_BATS){
                    numberNeeded = 5;
                    enemyNeeded = Enemy.EnemyType.BAT;
                    quest.beforeQuest = "Kill 5 bats";
                    quest.duringQuest = "Come back when you kill 5 bats";
                    quest.afterQuest = "Thank you";
                }
                break;
            case FETCH:
                if(quest.questDescription == Statics.FETCH_SLIMES){
                    numberNeeded = 2;
                    itemNeeded = "slimeGoo";
                    quest.beforeQuest = "Collect 2 slime goo";
                    quest.duringQuest = "Come back when you get 2 slime goos";
                    quest.afterQuest = "Thank you";
                }
                break;
        }
    }
}
