package com.gdx.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IdentityMap;
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

    public PlayScreen(MainGame game){
        this.game = game;
        MainGame.player = new Player(game.currentMap.playerSpawn);
        player = MainGame.player;

        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.vWidth / MainGame.PPM, MainGame.vHeight / MainGame.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

        createContactListener();

        MainGame.npcFactory.createNPC(NPC.NPCType.NORMAL, MainGame.currentMap.mapEntities, new Vector2(6, 4), null);
        MainGame.npcFactory.createNPC(NPC.NPCType.NORMAL, MainGame.currentMap.mapEntities,new Vector2(4, 4), MainGame.availableQuests.get(Statics.KILL_SLIMES));
        MainGame.npcFactory.createNPC(NPC.NPCType.NORMAL, MainGame.currentMap.mapEntities,new Vector2(2, 2), MainGame.availableQuests.get(Statics.KILL_BATS));
        //MainGame.enemyFactory.createEnemy(Enemy.EnemyType.SLIME, MainGame.currentMap.mapEntities, new Vector2(3, 3));

        game.hud = new HUD(game.batch);
        game.hud.health = player.health;
        game.hud.playerHealthLabel.setText(Statics.HUD_HEALTH + player.health);

        debugRenderer = new Box2DDebugRenderer();
    }

    private void update(float delta){
        game.currentWorld.step(1/60f, 6, 2);

        if(player.needToMove){
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

        debugRenderer.render(game.currentWorld, camera.combined);

        game.batch.setProjectionMatrix(game.hud.stage.getCamera().combined);
        game.hud.stage.act(Gdx.graphics.getDeltaTime());
        game.hud.stage.draw();

    }

    private void createContactListener(){
        MainGame.currentWorld.setContactListener(new WorldContactListener());
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
