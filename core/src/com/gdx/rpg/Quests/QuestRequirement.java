package com.gdx.rpg.Quests;

import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Statics;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class QuestRequirement {
    public int numberKilled;
    public int numberNeeded;

    public Enemy.EnemyType enemyNeeded;

    public QuestRequirement(Quest quest, Quest.QuestType questType){
        init(quest, questType);
    }

    private void init(Quest quest, Quest.QuestType questType){
        switch (questType){
            case KILL:
                if(quest.questDescription == Statics.KILL_SLIMES) {
                    numberNeeded = 3;
                    enemyNeeded = Enemy.EnemyType.SLIME;
                }
                break;
            case FETCH:

                break;
        }
    }
}
