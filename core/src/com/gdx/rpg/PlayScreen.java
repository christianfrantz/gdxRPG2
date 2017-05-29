package com.gdx.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.HUD.HUD;
import com.sun.javaws.Main;

/**
 * Created by imont_000 on 2/26/2017.
 */
public class PlayScreen implements Screen{

    ShapeRenderer shapeRenderer;

    private MainGame game;
    Player player;

    OrthographicCamera camera;
    Box2DDebugRenderer debugRenderer;

    Viewport viewport;

    BitmapFont font;

    public PlayScreen(MainGame game){
        this.game = game;
        MainGame.player = new Player(game.currentMap.playerSpawns.get("main_start"));
        player = MainGame.player;
        player.attackTime = 0.2f;

        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.vWidth / MainGame.PPM, MainGame.vHeight / MainGame.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

        createContactListener();

        game.hud = new HUD(game.batch);
        game.hud.health = player.health;
        game.hud.playerHealthLabel.setText(Statics.HUD_HEALTH + player.health);

        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawContacts(true);
        debugRenderer.setDrawInactiveBodies(false);
        debugRenderer.SHAPE_NOT_ACTIVE.set(Color.WHITE);
        debugRenderer.SHAPE_STATIC.set(Color.RED);

        font = new BitmapFont();
        font.setColor(Color.RED);

        MainGame.dayNightCycle = new DayNightCycle();
        MainGame.lightHandler = new LightHandler(MainGame.dayNightCycle);
        MainGame.lightHandler.setLights();

        shapeRenderer = new ShapeRenderer();
    }

    private void update(float delta){
        game.world.step(1/60f, 16, 12);

        player.updatePlayer(delta, camera, MainGame.lightHandler);

        for(Projectile p : MainGame.projectilesOnScreen){
            if(p.isActive)
                p.update(delta, MainGame.projectilesOnScreen);
        }
        for(int i = MainGame.currentMap.mapEntities.size() - 1; i >= 0; i--){
            if(!MainGame.currentMap.mapEntities.get(i).flaggedForDelete)
                 MainGame.currentMap.mapEntities.get(i).entityUpdateComponent.Update();
        }
        camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
        camera.update();

        float x = camera.position.x - camera.viewportWidth * camera.zoom;
        float y = camera.position.y - camera.viewportHeight * camera.zoom;
        float w = camera.viewportWidth * camera.zoom * 8;
        float h = camera.viewportHeight * camera.zoom * 8;

        game.renderer.setView(camera.combined, x, y, w, h);

        MainGame.dayNightCycle.updateTime();
    }

    @Override
    public void render(float delta) {
        game.batch.setColor(1, 1, 1, 0.1f);
        if(MainGame.gameState == MainGame.GameState.PLAYING) {
            update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.renderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.enableBlending();
        game.batch.begin();

        player.graphicsComponent.DrawPlayer(game.batch);
        for(Projectile p : MainGame.projectilesOnScreen){
            if(p.isActive)
            p.sprite.draw(game.batch);
        }

        for(Entity entity : MainGame.currentMap.mapEntities){
            entity.sprite.draw(game.batch);
        }

       //MainGame.particleEffect.update(Gdx.graphics.getDeltaTime());
        //MainGame.particleEffect.draw(game.batch);
        for(ParticleEffectPool.PooledEffect effect : MainGame.effects){
            effect.draw(game.batch, delta);
        }
        game.batch.end();

        if(MainGame.currentMap.foregroundLayer[0] != 0)
            game.renderer.render(MainGame.currentMap.foregroundLayer);


        MainGame.lightHandler.updateLight( camera);

        debugRenderer.render(game.world, camera.combined);

        game.batch.setProjectionMatrix(game.hud.stage.getCamera().combined);
        game.hud.stage.act(Gdx.graphics.getDeltaTime());
        if(player.showInventory){
            game.hud.inventoryActor.setVisible(true);
        }
        else if(player.showInventory == false) {
            game.hud.inventoryActor.setVisible(false);
        }
        game.hud.stage.draw();


        game.batch.begin();
        font.draw(game.batch, 0 + "Defense: " + player.defense, 10, 800);
        font.draw(game.batch, 1 + "Attack " + player.attack, 10, 780);
        font.draw(game.batch, player.playerState.toString(), 10, 760);
        font.draw(game.batch, "Foreground layer " + MainGame.gameState, 10, 740);
        font.draw(game.batch, "Player spawns " + MainGame.currentMap.playerSpawns.size(), 10, 720);
        font.draw(game.batch, "Stamina " + player.stamina, 10, 300);
        font.draw(game.batch, "Day: " + MainGame.dayNightCycle.getDay(), 10, 500);
        font.draw(game.batch, String.format("%1$02d", MainGame.dayNightCycle.getHours()) + " : " +
                String.format("%1$02d", MainGame.dayNightCycle.getMinutes()) + " : " +
                String.format("%1$02d", MainGame.dayNightCycle.getSeconds()), 10, 520);

        if(game.playerQuests.size() > 0){
            for(int i = 0; i < game.playerQuests.size(); i++){
                font.draw(game.batch, game.playerQuests.get(i).questDescription + " " + game.playerQuests.get(i).questCompleted, 10, 700 - i * 20);

            }
        }
        game.batch.end();

        if(MainGame.gameState == MainGame.GameState.CAMP_FADE){
            WorldEventController.drawEvent();
        }

    }

    private void createContactListener(){
        MainGame.world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
