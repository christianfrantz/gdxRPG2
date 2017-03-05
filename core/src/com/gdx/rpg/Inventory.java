package com.gdx.rpg;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Inventory {
    public InventorySlot[] inventorySlots;

    public Inventory(){
        inventorySlots = new InventorySlot[5];
        for(int i = 0; i < inventorySlots.length; i++){
            inventorySlots[i] = (new InventorySlot());
        }
    }

    public void AddItem(Item item){
        for(int i = 0; i < inventorySlots.length; i++){
            if(inventorySlots[i].itemInSlot.id == item.id){
                inventorySlots[i].itemCount++;
                return;
            }
            else if(!inventorySlots[i].hasItem) {
                inventorySlots[i].itemInSlot = item;
                inventorySlots[i].hasItem = true;
                inventorySlots[i].itemCount++;
                return;
            }

        }
    }

    public void RemoveItem(String item){
        for(int i = 0; i <inventorySlots.length; i++){
            if(inventorySlots[i].itemInSlot.id == item){
                inventorySlots[i].itemCount--;
                if(inventorySlots[i].itemCount == 0)
                    inventorySlots[i].itemInSlot = new Item("null");
                return;
            }
        }
    }
}
