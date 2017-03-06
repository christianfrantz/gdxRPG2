package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.Components.NPCUpdateComponent;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Quests.Quest;
import com.gdx.rpg.Statics;

/**
 * if object is entity, set sprite, health, call
 createBody(position, sprite.texture), set type
 */
public class NPC extends Entity {
    public enum NPCType{
        NORMAL
    }

    public NPCType npcType;

    public String currentDialogue;

    public String dialogue = "Hello";


    public boolean hasQuest;
    public Quest currentQuest;
    public boolean isClicked;

    public NPC( Vector2 position, String id) {
        super(position, id);
        sprite = new Sprite(new Texture("player.png"));
        createBody(position, sprite.getTexture());

        entityUpdateComponent = new NPCUpdateComponent(this);
        health = 10;
        npcType = NPCType.NORMAL;
        isClicked = false;
    }

    public void setUpQuest(){
        if(hasQuest){
            currentQuest.questGiver = this;
            currentDialogue = currentQuest.beforeQuest;

            for(int i = 0; i < MainGame.playerQuests.size(); i++){
                if(MainGame.playerQuests.contains(currentQuest) &&
                        MainGame.playerQuests.get(i).isQuestCompleted()){
                    currentDialogue = currentQuest.afterQuest;

                }
                else if(MainGame.playerQuests.contains(currentQuest) &&
                        !MainGame.playerQuests.get(i).isQuestCompleted()){
                    currentDialogue = currentQuest.duringQuest;

                }
                else if(!MainGame.playerQuests.contains(i))
                {
                    currentDialogue = currentQuest.beforeQuest;

                }
            }
        }
        else
            currentDialogue = dialogue;

    }


}
