package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Quests.Quest;
import com.gdx.rpg.Statics;

import java.util.ArrayList;

/**
 * For each entity create id first
 * entity = new entity(position, id)
 * create sprite
 * createBody()
 * set health
 * set type
 * set update component if needed
 * add to MainGame.entities
 */
public class NPCFactory {
    public int idNumber = 0;

    public NPC createNPC(NPC.NPCType type, Vector2 position, Quest quest){

        NPC npc = null;
        String id = null;

        switch (type){
            case NORMAL:
                 id = "normal" + idNumber;
                npc = new NPC(position, id);
                npc.sprite = new Sprite(new Texture("Sprites/NPC/ghost.png"));
                npc.createBody(position, npc.sprite.getTexture(), false);
                if(quest != null) {
                    npc.hasQuest = true;
                }
                else
                    npc.hasQuest = false;
                npc.currentQuest = quest;
                npc.setUpQuest();
                break;
            case GHOST:
                id = "ghost" + idNumber;
                npc = new NPC(position, id);
                npc.sprite = new Sprite(new Texture("Sprites/NPC/ghost.png"));
                npc.createBody(position, npc.sprite.getTexture(), true);
                npc.hasQuest = false;

        }

        idNumber++;
        return npc;
    }
}
