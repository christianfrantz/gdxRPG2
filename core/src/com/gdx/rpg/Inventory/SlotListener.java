package com.gdx.rpg.Inventory;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public interface SlotListener {
    void hasChanged(InventorySlot slot);
}
