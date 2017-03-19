package com.gdx.rpg;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public enum Item {

    STICK("stick", ItemType.JUNK),
    SLIME_GOO("slimegoo", ItemType.JUNK),
    BAT_WING("batwing", ItemType.JUNK),
    HEALTH_POTION("health_potion", ItemType.CONSUMABLE),

    SWORD("sword", WeaponType.SWORD, 3),
    CHEST("chest", ArmorType.CHEST, 3),
    RING("ring", ArmorType.RING, 1),
    HEAD("head", ArmorType.HEAD, 2);

    public enum ItemType{
        JUNK,
        EQUIP,
        CONSUMABLE
    }
    public enum WeaponType{
        SWORD,
        BOW
    }
    public enum ArmorType{
        CHEST,
        RING,
        HEAD
    }

    public String textureRegion;
    public ItemType type;
    public int modifier;
    public WeaponType weaponType;
    public ArmorType armorType;

    Item(String textureRegion, ItemType type){
        this.textureRegion = textureRegion;
        this.type = type;
    }

    Item(String textureRegion, ItemType type, int modifier){
        this.textureRegion = textureRegion;
        this.type = type;
        this.modifier = modifier;
    }

    Item(String textureRegion, WeaponType weaponType, int modifier){
        this.textureRegion = textureRegion;
        this.type = ItemType.EQUIP;
        this.weaponType = weaponType;
        this.modifier = modifier;
    }

    Item(String textureRegion, ArmorType armorType, int modifier){
        this.textureRegion = textureRegion;
        this.type = ItemType.EQUIP;
        this.armorType = armorType;
        this.modifier = modifier;
    }



    public String getTextureRegion(){
        return textureRegion;
    }

    public ItemType getItemType(){
        return type;
    }

}
