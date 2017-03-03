package com.gdx.rpg.Quests;

import com.gdx.rpg.Entities.Entity;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Quest {
    public enum QuestType {
        FETCH,
        KILL
    }
    public String questDescription;
    public boolean questCompleted;
    public QuestType questType;
    public QuestRequirement questRequirement;
    public Entity questGiver;

    public String beforeQuest;
    public String duringQuest;
    public String afterQuest;

    public Quest(String questDescription, QuestType questType){
        this.questDescription = questDescription;
        questCompleted = false;
        this.questType = questType;
        questRequirement = new QuestRequirement(this, questType);
    }

    public boolean isQuestCompleted(){
        return questCompleted;
    }
}