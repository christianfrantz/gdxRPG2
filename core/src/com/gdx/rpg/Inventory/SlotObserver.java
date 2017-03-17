package com.gdx.rpg.Inventory;

import com.gdx.rpg.Equips.EquipSlot;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public interface SlotObserver {
    void hasChanged(InventorySlot slot);
}
