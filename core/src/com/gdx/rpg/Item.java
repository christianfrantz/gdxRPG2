package com.gdx.rpg;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public enum Item {

    STICK("stick", ItemType.JUNK),
    SLIME_GOO("slimegoo", ItemType.JUNK),
    BAT_WING("batwing", ItemType.JUNK),

    HEALTH_POTION("health_potion", ConsumeType.HEALTH, 25);

    public enum ItemType{
        JUNK,
        EQUIP,
        CONSUMABLE
    }

    public enum ConsumeType{
        MANA,
        HEALTH
    }

    public String textureRegion;
    public ItemType type;
    public int modifier;
    public ConsumeType consumeType;

    Item(String textureRegion, ItemType type){
        this.textureRegion = textureRegion;
        this.type = type;
    }

    Item(String textureRegion, ItemType type, int modifier){
        this.textureRegion = textureRegion;
        this.type = type;
        this.modifier = modifier;
    }


    Item(String textureRegion, ConsumeType consumeType, int modifier){
        this.textureRegion = textureRegion;
        this.type = ItemType.CONSUMABLE;
        this.consumeType = consumeType;
        this.modifier = modifier;
    }

    public void onConsume(ConsumeType consumeType, int modifier){
        if(consumeType == ConsumeType.HEALTH){
            MainGame.player.health += modifier;
            MainGame.hud.health = MainGame.player.health;
            MainGame.hud.playerHealthLabel.setText(Statics.HUD_HEALTH + MainGame.player.health);
        }
        else if(consumeType == ConsumeType.MANA){
            MainGame.player.mana += modifier;
        }
    }

    public String getTextureRegion(){
        return textureRegion;
    }

    public ItemType getItemType(){
        return type;
    }

}
