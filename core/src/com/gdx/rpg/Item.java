package com.gdx.rpg;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Item {

    public enum ItemType{
        JUNK
    }

    public String id;
    public ItemType itemType;

    public Item(String name){
        this.id = name;
    }
}
