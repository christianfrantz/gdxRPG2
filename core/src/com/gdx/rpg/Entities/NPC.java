package com.gdx.rpg.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.Components.NPCUpdateComponent;

/**
 * if object is entity, set sprite, health, call
 createBody(position, sprite.texture), set type
 */
public class NPC extends Entity {
    public enum NPCType{
        NORMAL
    }

    public NPCType npcType;
    public String dialogue;

    public NPC( Vector2 position, String id) {
        super(position, id);
        sprite = new Sprite(new Texture("player.png"));
        createBody(position, sprite.getTexture());
        dialogue = "HELLO";
        entityUpdateComponent = new NPCUpdateComponent(this);
        health = 10;
        npcType = NPCType.NORMAL;
    }


}
