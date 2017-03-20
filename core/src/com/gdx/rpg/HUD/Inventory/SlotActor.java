package com.gdx.rpg.HUD.Inventory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.rpg.HUD.HUD;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class SlotActor extends ImageButton implements SlotObserver {

    private InventorySlot slot;
    private Skin skin;

    public SlotActor(Skin skin, InventorySlot slot, HUD hud) {
        super(createStyle(skin, slot));
        this.slot = slot;
        this.skin = skin;

        slot.addListener(this);

        SlotTooltip tooltip = new SlotTooltip(slot, skin);
        hud.stage.addActor(tooltip);
        addListener(new TooltipListener(tooltip, true));
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                useItem(getSlot());
            }
        });
    }

    private void useItem(InventorySlot slot){
        if(!slot.hasItem)
            return;
        if(slot.itemInSlot.getItemType() == Item.ItemType.CONSUMABLE){
            slot.itemInSlot.onConsume(slot.itemInSlot.consumeType, slot.itemInSlot.modifier);
            MainGame.player.inventory.RemoveItem(slot.itemInSlot);
            return;
        }
        if(slot.itemInSlot.getItemType() == Item.ItemType.EQUIP) {
            if(slot.itemInSlot.armorType != null) {
                switch (slot.itemInSlot.armorType) {
                    case CHEST:
                        MainGame.player.equips.AddToSlot("CHEST", slot.itemInSlot);
                        MainGame.player.defense += slot.itemInSlot.modifier;
                        MainGame.player.inventory.RemoveItem(slot.itemInSlot);
                        break;
                    case RING:
                        MainGame.player.equips.AddToSlot("RING", slot.itemInSlot);
                        MainGame.player.defense += slot.itemInSlot.modifier;
                        MainGame.player.inventory.RemoveItem(slot.itemInSlot);
                        break;
                    case HEAD:
                        MainGame.player.equips.AddToSlot("HEAD", slot.itemInSlot);
                        MainGame.player.defense += slot.itemInSlot.modifier;
                        MainGame.player.inventory.RemoveItem(slot.itemInSlot);
                        break;
                }
                return;
            }
            else if(slot.itemInSlot.weaponType != null) {
                switch (slot.itemInSlot.weaponType) {
                    case SWORD:
                        MainGame.player.equips.AddToSlot("WEAPON", slot.itemInSlot);
                        MainGame.player.attack += slot.itemInSlot.modifier;
                        MainGame.player.inventory.RemoveItem(slot.itemInSlot);
                        break;
                }
                return;
            }
        }
    }

    private static ImageButtonStyle createStyle(Skin skin, InventorySlot slot){
        TextureAtlas icons = new TextureAtlas("ItemIcons.pack");
        TextureRegion image;
        if(slot.hasItem){
            image = icons.findRegion(slot.itemInSlot.getTextureRegion());
        }
        else{
            image = icons.findRegion("empty");
        }
        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);
        return style;
    }

    @Override
    public void hasChanged(InventorySlot slot){
        setStyle(createStyle(skin, slot));
    }

    public InventorySlot getSlot(){
        return slot;
    }
}
