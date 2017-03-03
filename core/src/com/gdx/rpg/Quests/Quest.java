package com.gdx.rpg.Quests;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Quest {
    public String questDescription;
    public boolean questCompleted;

    public Quest(String questDescription){
        this.questDescription = questDescription;
        questCompleted = false;
    }

    public boolean isQuestCompleted(){
        return questCompleted;
    }
}