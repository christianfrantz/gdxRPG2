package com.gdx.rpg;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class InventorySlot {
    public int itemCount;
    public Item itemInSlot;
    public boolean hasItem;

    public InventorySlot(){
        this.itemInSlot = new Item("empty");
        this.hasItem = false;
        this.itemCount = 0;
    }
}
