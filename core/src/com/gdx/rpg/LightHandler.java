package com.gdx.rpg;


import box2dLight.RayHandler;
import box2dLight.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class LightHandler {

    private RayHandler rayHandler;
    private PointLight playerLight;
    private float ambientR, ambientG, ambientB, ambientA;
    public ArrayList<PointLight> lightsOnScreen = new ArrayList<PointLight>();
    private DayNightCycle dayNightCycle;

    public LightHandler(DayNightCycle dayNightCycle){
        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(MainGame.world);
        rayHandler.setCulling(true);

        playerLight = new PointLight(rayHandler, 50);
        playerLight.setStaticLight(false);
        playerLight.setActive(true);
        playerLight.setDistance(3);
        playerLight.attachToBody(MainGame.player.body);
        playerLight.setIgnoreAttachedBody(true);
        this.dayNightCycle = dayNightCycle;
    }

    public void setLights(){

        for(int i = 0; i < lightsOnScreen.size(); i++){
            lightsOnScreen.get(i).setActive(false);
        }
        lightsOnScreen.clear();

        if(MainGame.currentMap.tiledMap.getLayers().get("Lights") != null)
        for(MapObject object : MainGame.currentMap.tiledMap.getLayers().get("Lights").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            PointLight light = new PointLight(rayHandler, 50);
            light.setStaticLight(true);
            light.setActive(true);
            light.setDistance(3);
            light.setPosition(rect.x / MainGame.PPM, rect.y / MainGame.PPM);

            lightsOnScreen.add(light);
        }

        setRGBA();
    }

    public void updateLight( Camera camera){
        setRGBA();

        rayHandler.setAmbientLight(ambientR, ambientG, ambientB, ambientA);
        playerLight.setPosition(MainGame.player.sprite.getX(), MainGame.player.sprite.getY());
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
    }

    private void setRGBA(){
        if(MainGame.player.isOutside) {
            if (dayNightCycle.getHours() == 6) {
                ambientB = 0.3f;
                ambientR = 0.8f;
                ambientG = 0.35f;
            }
            if (dayNightCycle.getHours() == 7) {
                ambientG = 0.65f;
                ambientR = 0.9f;
                ambientB = 0.01f;
            }

            if (dayNightCycle.getHours() == 8) {
                ambientB = 1;
                ambientG = 1;
                ambientR = 1;
            }
            if (dayNightCycle.getHours() == 17) {
                ambientR = 1;
                ambientG = 0.8f;
                ambientB = 0;
            }
            if (dayNightCycle.getHours() == 19) {
                ambientR = 1;
                ambientG = 0.5f;
                ambientB = 0;
            }
            if (dayNightCycle.getHours() == 20) {
                ambientR = 1;
                ambientB = 0.3f;
                ambientG = 0.3f;
            }
            if (dayNightCycle.getHours() == 21) {
                ambientG = 0.2f;
                ambientB = 0.7f;
                ambientR = 0.3f;
            }
            if (dayNightCycle.getHours() >= 22 && dayNightCycle.getHours() <= 5) {
                ambientR = 0.2f;
                ambientG = 0.15f;
                ambientB = 0.4f;
                ambientA = 0.3f;
            }
        }
        else {
            ambientA = 1;
            ambientB = 1;
            ambientG = 1;
            ambientR = 1;
        }
    }
}
