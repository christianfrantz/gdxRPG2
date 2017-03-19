package com.gdx.rpg.Inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.gdx.rpg.Equips.EquipSlot;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class SlotTooltip extends Window implements SlotObserver {

    private Skin skin;
    private InventorySlot slot;

    public SlotTooltip(InventorySlot slot, Skin skin) {
        super("Tooltip...", skin);
        this.slot = slot;
        this.skin = skin;
        hasChanged(slot);
        slot.addListener(this);
        setVisible(false);
    }


    public void hasChanged(InventorySlot slot){
        if(!slot.hasItem){
            setVisible(false);
            return;
        }

        clear();
        Label label = new Label(slot.itemInSlot.name() + " " + slot.itemInSlot.getItemType() + " " + slot.itemInSlot.modifier, skin);
        add(label);
        pack();
    }

    public void setVisible(boolean visible){
        super.setVisible(visible);
        if(slot.hasItem == false){
            super.setVisible(false);
        }
    }
}
