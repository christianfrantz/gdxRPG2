package com.gdx.rpg.HUD.Inventory;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public interface SlotObserver {
    void hasChanged(InventorySlot slot);
}
