package com.gdx.rpg;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.NPC;
import com.gdx.rpg.Quests.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Map {
    public String mapName;
    public ArrayList<Entity> mapEntities = new ArrayList<Entity>();
    public HashMap<String, Vector2> playerSpawns;
    public TiledMap tiledMap;
    public int[] foregroundLayer;


    public Map( String name, TmxMapLoader loader){
        this.mapName = name;
        tiledMap = loader.load(name + ".tmx");

        foregroundLayer = new int[1];
        if(tiledMap.getLayers().get("Foreground") != null){
            foregroundLayer[0] = tiledMap.getLayers().getIndex("Foreground");
        }else
            foregroundLayer[0] = 2;
    }


    public void loadMap(){

        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        Array<Body> bodies = new Array<Body>();
        playerSpawns = new HashMap<String, Vector2>();

        MainGame.world.getBodies(bodies);
        for(Body b : bodies){
            if(!b.getUserData().equals(Statics.PLAYER_BODY) && !b.getUserData().equals(Statics.PLAYER_ATTACK_BODY) && !b.getUserData().equals(Statics.PLAYER_DIALOG_BODY)){
                MainGame.world.destroyBody(b);
            }
        }

        mapEntities.clear();
        playerSpawns.clear();

        for(MapObject object : tiledMap.getLayers().get("CollisionLayer").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            def.type = BodyDef.BodyType.StaticBody;
            def.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PPM , (rect.getY() + rect.getHeight() /2) / MainGame.PPM  );
            body = MainGame.world.createBody(def);

            shape.setAsBox(rect.getWidth() / 2 / MainGame.PPM , rect.getHeight() / 2 / MainGame.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
            body.setUserData("wall");
        }

        for(MapObject object : tiledMap.getLayers().get("SpawnLayer").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            if(object.getProperties().containsKey("enemySpawn")){
                setupEnemyGroup(object.getProperties().get("enemySpawn").toString(), rect);
            }

            if(object.getProperties().containsKey("player_start")){
                playerSpawns.put(object.getProperties().get("player_start").toString(), new Vector2(rect.getX() / MainGame.PPM, rect.getY() / MainGame.PPM));
            }
            if(object.getProperties().containsKey("npc")){
                mapEntities.add(MainGame.npcFactory.createNPC(NPC.NPCType.NORMAL, new Vector2(rect.getX() / MainGame.PPM, rect.getY() / MainGame.PPM), MainGame.availableQuests.get(object.getProperties().get("npc"))));
            }
            if(object.getProperties().containsKey("ghost")){
                mapEntities.add(MainGame.npcFactory.createNPC(NPC.NPCType.GHOST, new Vector2(rect.getX() / MainGame.PPM, rect.getY() / MainGame.PPM), null));
            }
        }

        for(MapObject object : tiledMap.getLayers().get("TeleportLayer").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            def.type = BodyDef.BodyType.StaticBody;
            def.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PPM , (rect.getY() + rect.getHeight() /2) / MainGame.PPM  );
            body = MainGame.world.createBody(def);

            shape.setAsBox(rect.getWidth() / 2 / MainGame.PPM , rect.getHeight() / 2 / MainGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.isSensor = true;
            body.createFixture(fixtureDef).setUserData(object);
            body.setUserData("teleport");
        }
    }

    private void setupEnemyGroup(String enemyType, Rectangle bounds){
        float x = bounds.getX() / MainGame.PPM;
        float y = bounds.getY() / MainGame.PPM;
        float width = bounds.getWidth() / MainGame.PPM;
        float height = bounds.getHeight() / MainGame.PPM;

        Random random = new Random();
        int enemyCount = 4 + random.nextInt(4);

        if(enemyType.equals("slime")){
            for(int i = 0; i < enemyCount; i++){
                float xPos = x + (float)random.nextInt((int)width);
                float yPos = y + (float)random.nextInt((int)height);
                mapEntities.add(MainGame.enemyFactory.createEnemy(Enemy.EnemyType.SLIME, new Vector2(xPos, yPos)));
            }
        }
        else if(enemyType.equals("bat")) {
            for (int i = 0; i < enemyCount; i++) {
                float xPos = x + (float) random.nextInt((int) width);
                float yPos = y + (float) random.nextInt((int) height);
                mapEntities.add(MainGame.enemyFactory.createEnemy(Enemy.EnemyType.BAT, new Vector2(xPos, yPos)));
            }
        }
    }
}
