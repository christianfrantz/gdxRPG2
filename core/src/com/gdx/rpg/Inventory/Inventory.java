package com.gdx.rpg.Inventory;

import com.gdx.rpg.Item;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Inventory {
    public InventorySlot[] inventorySlots;

    public Inventory(int num){
        inventorySlots = new InventorySlot[num];
        for(int i = 0; i < inventorySlots.length; i++){
            inventorySlots[i] = (new InventorySlot());
        }
    }

    public void AddItem(Item item){
        for(int i = 0; i < inventorySlots.length; i++){
            if(inventorySlots[i].itemInSlot == item){
                inventorySlots[i].itemCount++;
                inventorySlots[i].notifyListeners();
                return;
            }
            else if(!inventorySlots[i].hasItem) {
                inventorySlots[i].itemInSlot = item;
                inventorySlots[i].hasItem = true;
                inventorySlots[i].itemCount++;
                inventorySlots[i].notifyListeners();
                return;
            }

        }
    }

    public void RemoveItem(Item item){
        for(int i = 0; i <inventorySlots.length; i++){
            if(inventorySlots[i].itemInSlot == item){
                inventorySlots[i].itemCount--;
                inventorySlots[i].notifyListeners();
                if(inventorySlots[i].itemCount == 0)
                    inventorySlots[i].itemInSlot = null;
//                inventorySlots[i].notifyListeners();
                return;
            }
        }
    }

    public void EquipItem(Item item){

    }
}
