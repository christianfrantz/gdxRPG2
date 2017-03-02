package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.MainGame;

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

    public NPC createNPC(NPC.NPCType type, Vector2 position){

        NPC npc = null;

        switch (type){
            case NORMAL:
                String id = "normal" + idNumber;
                npc = new NPC(position, id);
                MainGame.entities.put(id, npc);
                break;
        }

        idNumber++;
        return npc;
    }
}
