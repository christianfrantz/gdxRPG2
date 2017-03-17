package com.gdx.rpg;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public enum Item {

    STICK("stick", ItemType.JUNK),
    SLIME_GOO("slimegoo", ItemType.JUNK),
    BAT_WING("batwing", ItemType.JUNK),

    SWORD("sword", ItemType.GEAR),
    SHIRT("shirt", ItemType.GEAR),
    PANTS("pants", ItemType.GEAR),
    BOOTS("boots", ItemType.GEAR);

    public enum ItemType{
        JUNK,
        GEAR
    }

    public String textureRegion;
    public ItemType type;

    private Item(String textureRegion, ItemType type){
        this.textureRegion = textureRegion;
        this.type = type;
    }

    public String getTextureRegion(){
        return textureRegion;
    }

    public ItemType getItemType(){
        return type;
    }

}
