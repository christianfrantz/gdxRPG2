package com.gdx.rpg.HUD.Equips;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class EquipSlotTooltip extends Window implements EquipSlotObserver {
    private Skin skin;
    private EquipSlot eSlot;

    public EquipSlotTooltip(EquipSlot slot, Skin skin){
        super("Tooltip...", skin);
        this.eSlot = slot;
        this.skin = skin;
        hasChanged(slot);
        slot.addListener(this);
        eSlotVisible(false);
    }

    @Override
    public void hasChanged(EquipSlot eSlot) {
        if(!eSlot.hasItem){
            eSlotVisible(false);
            return;
        }

        clear();
        Label label = new Label(eSlot.itemInSlot.name(), skin);
        System.out.println(eSlot.itemInSlot.name());
        add(label);
        pack();
    }

    public void eSlotVisible(boolean visible){
        super.setVisible(visible);
        if(eSlot.hasItem == false){
            super.setVisible(false);
        }
    }

}
