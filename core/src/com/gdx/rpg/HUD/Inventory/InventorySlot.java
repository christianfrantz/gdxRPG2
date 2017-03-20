package com.gdx.rpg.HUD.Inventory;

import com.badlogic.gdx.utils.Array;
import com.gdx.rpg.Item;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class InventorySlot {
    public int itemCount;
    public Item itemInSlot;
    public boolean hasItem;

    private Array<SlotObserver> slotListener = new Array<SlotObserver>();

    public InventorySlot(){
        this.itemInSlot = null;
        this.hasItem = false;
        this.itemCount = 0;
    }

    public void addListener(SlotObserver listener){
        slotListener.add(listener);
    }

    public void notifyListeners(){
        System.out.println("SLOT NOTIFIED");
        for(SlotObserver listener : slotListener) {
            listener.hasChanged(this);
        }
    }
}
