package com.gdx.rpg.Equips;

import com.badlogic.gdx.utils.Array;
import com.gdx.rpg.Inventory.SlotObserver;
import com.gdx.rpg.Item;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class EquipSlot {
    public String slotName;
    public Item itemInSlot;
    public boolean hasItem;

    private Array<EquipSlotObserver> slotListener = new Array<EquipSlotObserver>();

    public EquipSlot(){
        this.itemInSlot = null;
        this.hasItem = false;
        this.slotName = null;
    }

    public void addListener(EquipSlotObserver listener){
        slotListener.add(listener);
    }

    public void notifyListeners(){
        for(EquipSlotObserver listener : slotListener) {
            listener.hasChanged(this);
        }
    }
}
