package com.gdx.rpg.Equips;

import com.gdx.rpg.Inventory.InventorySlot;
import com.gdx.rpg.Item;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Equips {
    public EquipSlot[] equipSlots;

    public Equips(int num){
        equipSlots = new EquipSlot[num];
        for(int i = 0; i < equipSlots.length; i++){
            equipSlots[i] = (new EquipSlot());
        }

        equipSlots[0].slotName = "HEAD";
        equipSlots[1].slotName = "CHEST";
        equipSlots[2].slotName = "RING";
        equipSlots[3].slotName = "WEAPON";
    }

    public void AddToSlot(String name, Item item){
        EquipSlot slot;
        for(int i = 0; i < equipSlots.length; i++) {
            if (equipSlots[i].slotName == name) {
                slot = equipSlots[i];
                if(!slot.hasItem){
                    slot.itemInSlot = item;
                    slot.hasItem = true;
                    slot.notifyListeners();
                }
                else if(slot.hasItem){
                    slot.itemInSlot = item;
                }
            }
        }
    }
    public void AddItem(Item item){
        for(int i = 0; i < equipSlots.length; i++){
            if(equipSlots[i].itemInSlot == item){
                //equipSlots[i].itemCount++;
                equipSlots[i].notifyListeners();
                return;
            }
            else if(!equipSlots[i].hasItem) {
                equipSlots[i].itemInSlot = item;
                equipSlots[i].hasItem = true;
                //equipSlots[i].itemCount++;
                equipSlots[i].notifyListeners();
                return;
            }

        }
    }

    public void RemoveItem(Item item){
        for(int i = 0; i <equipSlots.length; i++){
            if(equipSlots[i].itemInSlot == item){
                //equipSlots[i].itemCount--;
                equipSlots[i].notifyListeners();
                //if(equipSlots[i].itemCount == 0)
                    //equipSlots[i].itemInSlot = null;
//                inventorySlots[i].notifyListeners();
                return;
            }
        }
    }
}
