package com.gdx.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public abstract class WorldEventController {
    static float timeToFade = 1f;
    static float fadeTimer = 0f;
    static float alpha = 0f;
    static ShapeRenderer shapeRenderer;
    static Color color;

    public static void FadeToCamp(){
        shapeRenderer = new ShapeRenderer();
        color = Color.BLACK;

        fadeTimer += Gdx.graphics.getDeltaTime();
        alpha = 1 - (fadeTimer / timeToFade);

        color.a = -alpha;

        System.out.println(alpha);
        if(alpha <= 0){
            fadeTimer = 0;
            MainGame.gameState = MainGame.GameState.CAMPING;
            MainGame.dayNightCycle.setCampTime();
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(0, 0, 1920, 1080);
        shapeRenderer.end();
    }

    public static void drawEvent(){
        FadeToCamp();
    }

    public static void updateEvents(){

    }
}
