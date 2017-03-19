package com.gdx.rpg.Equips;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.rpg.HUD.HUD;
import com.gdx.rpg.Inventory.InventorySlot;
import com.gdx.rpg.Inventory.SlotObserver;
import com.gdx.rpg.Inventory.SlotTooltip;
import com.gdx.rpg.Inventory.TooltipListener;
import com.gdx.rpg.Equips.EquipSlot;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class EquipSlotActor extends ImageButton implements EquipSlotObserver {

    private EquipSlot slot;
    private Skin skin;

    public EquipSlotActor(Skin skin, EquipSlot slot, HUD hud) {
        super(createStyle(skin, slot));
        this.slot = slot;
        this.skin = skin;

        slot.addListener(this);

        EquipSlotTooltip tooltip = new EquipSlotTooltip(slot, skin);
        hud.stage.addActor(tooltip);
        addListener(new TooltipListener(tooltip, true));
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                RemoveEquip(getSlot());
            }
        });

    }

    private EquipSlot getSlot(){
        return slot;
    }

    private void RemoveEquip(EquipSlot slot){
        if(slot.hasItem){
            if(slot.itemInSlot.armorType != null){
                MainGame.player.defense -= slot.itemInSlot.modifier;
            }
            else if(slot.itemInSlot.weaponType != null){
                MainGame.player.attack -= slot.itemInSlot.modifier;
            }
            MainGame.player.inventory.AddItem(slot.itemInSlot);
            slot.itemInSlot = null;
            slot.hasItem = false;
            slot.notifyListeners();
        }
    }
    private static ImageButton.ImageButtonStyle createStyle(Skin skin, EquipSlot slot){
        TextureAtlas icons = new TextureAtlas("ItemIcons.pack");
        TextureRegion image;
        if(slot.hasItem){
            image = icons.findRegion(slot.itemInSlot.getTextureRegion());
        }
        else if(!slot.hasItem && slot.slotName.equals("HEAD")){
            image = icons.findRegion("EMPTY_HEAD");
        }
        else if(!slot.hasItem && slot.slotName.equals("CHEST")){
            image = icons.findRegion("EMPTY_CHEST");
        }
        else if(!slot.hasItem && slot.slotName.equals("RING")){
            image = icons.findRegion("EMPTY_RING");
        }
        else if(!slot.hasItem && slot.slotName.equals("WEAPON")){
            image = icons.findRegion("EMPTY_WEAPON");
        }
        else{
            image = icons.findRegion("empty");
        }

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(skin.get(Button.ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);
        return style;
    }

    @Override
    public void hasChanged(EquipSlot slot){
        setStyle(createStyle(skin, slot));
    }
}
