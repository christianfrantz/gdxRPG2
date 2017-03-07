package com.gdx.rpg.Inventory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.rpg.HUD.HUD;

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
        System.out.println("SDF");
    }

    public InventorySlot getSlot(){
        return slot;
    }
}
