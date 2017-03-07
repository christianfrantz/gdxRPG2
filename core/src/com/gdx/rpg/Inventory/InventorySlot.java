package com.gdx.rpg.Inventory;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
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

    private Array<SlotListener> slotListener = new Array<SlotListener>();

    public InventorySlot(){
        this.itemInSlot = null;
        this.hasItem = false;
        this.itemCount = 0;
    }

    public void addListener(SlotListener listener){
        slotListener.add(listener);
    }

    public void notifyListeners(){
        System.out.println("SLOT NOTIFIED");
        for(SlotListener listener : slotListener) {
            listener.hasChanged(this);
        }
    }
}
