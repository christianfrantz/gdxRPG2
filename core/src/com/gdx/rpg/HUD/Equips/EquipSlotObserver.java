package com.gdx.rpg.HUD.Equips;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public interface EquipSlotObserver {
    void hasChanged(EquipSlot slot);
}
