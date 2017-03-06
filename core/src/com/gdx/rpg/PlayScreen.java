package com.gdx.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.NPC;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.HUD.HUD;

/**
 * Created by imont_000 on 2/26/2017.
 */
public class PlayScreen implements Screen{

    private MainGame game;
    Player player;

    OrthographicCamera camera;
    Box2DDebugRenderer debugRenderer;

    Viewport viewport;

    BitmapFont font;

    public PlayScreen(MainGame game){
        this.game = game;
        MainGame.player = new Player(game.currentMap.playerSpawn);
        player = MainGame.player;

        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.vWidth / MainGame.PPM, MainGame.vHeight / MainGame.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

        createContactListener();

        game.hud = new HUD(game.batch);
        game.hud.health = player.health;
        game.hud.playerHealthLabel.setText(Statics.HUD_HEALTH + player.health);

        debugRenderer = new Box2DDebugRenderer();

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    private void update(float delta){
        game.world.step(1/60f, 6, 2);

        if(player.needToMove){
            game.mapToLoad.loadMap();
            game.currentMap = game.mapToLoad;

            MainGame.renderer = new OrthogonalTiledMapRenderer(game.currentMap.tiledMap, 1 / MainGame.PPM);

            player.body.setTransform(game.currentMap.playerSpawn.x, game.currentMap.playerSpawn.y, 0);
            player.needToMove = false;
            player.body.setAwake(true);
        }

        player.updatePlayer(delta, camera);

        for(int i = MainGame.currentMap.mapEntities.size() - 1; i >= 0; i--){
            if(!MainGame.currentMap.mapEntities.get(i).flaggedForDelete)
                 MainGame.currentMap.mapEntities.get(i).entityUpdateComponent.Update();
        }
        camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
        camera.update();

        game.renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.renderer.render();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.enableBlending();
        game.batch.begin();
        player.sprite.draw(game.batch);

        for(Entity entity : MainGame.currentMap.mapEntities){
            entity.sprite.draw(game.batch);
        }
        player.cursor.draw(game.batch);
        game.batch.end();

        debugRenderer.render(game.world, camera.combined);

        game.batch.setProjectionMatrix(game.hud.stage.getCamera().combined);
        game.hud.stage.act(Gdx.graphics.getDeltaTime());
        game.hud.stage.draw();


        game.batch.begin();
        font.draw(game.batch, 0 + " " + player.inventory.inventorySlots[0].itemInSlot.id + " " + player.inventory.inventorySlots[0].itemCount, 10, 800);
        font.draw(game.batch, 1 + " " + player.inventory.inventorySlots[1].itemInSlot.id + " " + player.inventory.inventorySlots[1].itemCount, 10, 780);
        font.draw(game.batch, 2 + " " + player.inventory.inventorySlots[2].itemInSlot.id + " " + player.inventory.inventorySlots[2].itemCount, 10, 760);
        font.draw(game.batch, 3 + " " + player.inventory.inventorySlots[3].itemInSlot.id + " " + player.inventory.inventorySlots[3].itemCount, 10, 740);
        font.draw(game.batch, 4 + " " + player.inventory.inventorySlots[4].itemInSlot.id + " " + player.inventory.inventorySlots[4].itemCount, 10, 720);

        if(game.playerQuests.size() > 0){
            for(int i = 0; i < game.playerQuests.size(); i++){
                font.draw(game.batch, game.playerQuests.get(i).questDescription + " " + game.playerQuests.get(i).questCompleted, 10, 700 - i * 20);

            }
        }
        game.batch.end();

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
