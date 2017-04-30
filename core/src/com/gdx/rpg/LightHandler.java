package com.gdx.rpg;


import box2dLight.RayHandler;
import box2dLight.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class LightHandler {

    private RayHandler rayHandler;
    private PointLight playerLight;
    private float ambientR, ambientG, ambientB, ambientA;

    public LightHandler(){
        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(MainGame.world);
        rayHandler.setCulling(true);

        playerLight = new PointLight(rayHandler, 50);
        playerLight.setStaticLight(false);
        playerLight.setActive(true);
        playerLight.setDistance(3);
        playerLight.attachToBody(MainGame.player.body);
        playerLight.setIgnoreAttachedBody(true);
    }

    public void updateLight(DayNightCycle dayNightCycle, Camera camera){
        if(dayNightCycle.getHours() == 6){
            ambientB = 0.3f;
            ambientR = 0.8f;
            ambientG = 0.35f;
        }
        if(dayNightCycle.getHours() == 7){
            ambientG = 0.65f;
            ambientR = 0.9f;
            ambientB = 0.01f;
        }

        if(dayNightCycle.getHours() == 8){
            ambientB = 1;
            ambientG = 1;
            ambientR = 1;
        }
        if(dayNightCycle.getHours() == 17){
            ambientR = 1;
            ambientG = 0.8f;
            ambientB = 0;
        }
        if(dayNightCycle.getHours() == 19){
            ambientR = 1;
            ambientG = 0.5f;
            ambientB = 0;
        }
        if(dayNightCycle.getHours() == 20){
            ambientR = 1;
            ambientB = 0.3f;
            ambientG = 0.3f;
        }
        if(dayNightCycle.getHours() == 21){
            ambientG = 0.2f;
            ambientB = 0.7f;
            ambientR = 0.3f;
        }
        if(dayNightCycle.getHours() == 22){
            ambientR = 0.2f;
            ambientG = 0.15f;
            ambientB = 0.4f;
            ambientA = 0.3f;
        }
        rayHandler.setAmbientLight(ambientR, ambientG, ambientB, ambientA);
        playerLight.setPosition(MainGame.player.sprite.getX(), MainGame.player.sprite.getY());
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
    }
}
