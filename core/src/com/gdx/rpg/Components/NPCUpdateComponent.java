package com.gdx.rpg.Components;

import com.badlogic.gdx.math.Vector2;
import com.gdx.rpg.Entities.Entity;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class NPCUpdateComponent extends EntityUpdateComponent {

    public NPCUpdateComponent(Entity entity) {
        super(entity);
    }

    @Override
    public void Update(){
        entity.sprite.setPosition(entity.body.getPosition().x - entity.sprite.getWidth() / 2, entity.body.getPosition().y - entity.sprite.getHeight() / 2);

        entity.body.applyLinearImpulse(new Vector2(-0.01f, 0), entity.body.getWorldCenter(), true);
    }
}
