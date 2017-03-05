package com.gdx.rpg.Components;

import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class EntityUpdateComponent {

    public Entity entity;

    public EntityUpdateComponent(Entity entity){
        this.entity = entity;
    }

    public void Update(){
        entity.sprite.setPosition(entity.body.getPosition().x - entity.sprite.getWidth() / 2, entity.body.getPosition().y - entity.sprite.getHeight() / 2);

        checkIfAlive();
    }


    public void checkIfAlive(){
        if(entity.health <= 0) {
            entity.flaggedForDelete = true;
            entity.IsKilled();
        }

    }
}
