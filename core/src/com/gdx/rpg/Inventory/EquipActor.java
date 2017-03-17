package com.gdx.rpg.Inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.rpg.HUD.HUD;


/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class EquipActor extends Window {
    public EquipActor(Inventory equips, HUD hud, Skin skin) {
        super("Equips", skin);

        TextButton closeButton = new TextButton("x", skin);
        closeButton.addListener(new ClickListener());
        // getButtonTable().add(closeButton).height(getPadTop());

        setPosition(400, 270);
        defaults().space(8);
        row().fill().expandX();

        int i = 0;
        for(InventorySlot slot : equips.inventorySlots){
            SlotActor actor = new SlotActor(skin, slot, hud);
            add(actor);

            i++;
            if(i % 5 == 0) {
                row();
            }
        }

        pack();
        setVisible(false);
    }
}
