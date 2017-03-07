package com.gdx.rpg.Inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.gdx.rpg.HUD.HUD;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class InventoryActor extends Window {

    public InventoryActor(Inventory inventory, HUD hud, Skin skin) {
        super("Inventory", skin);

        TextButton closeButton = new TextButton("x", skin);
        closeButton.addListener(new ClickListener());
       // getButtonTable().add(closeButton).height(getPadTop());

        setPosition(400, 100);
        defaults().space(8);
        row().fill().expandX();

        int i = 0;
        for(InventorySlot slot : inventory.inventorySlots){
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
