package com.gdx.rpg;

import com.badlogic.gdx.graphics.Texture;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Assets {


    public static Texture GetTexture(String path){
        return new Texture(path + ".png");
    }

}
