package com.gdx.rpg;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;

import java.util.ArrayList;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class Map {
    public String mapName;
    public ArrayList<Entity> mapEntities = new ArrayList<Entity>();
    private ArrayList<Entity> e;
    public TiledMap tiledMap;
    public Vector2 playerSpawn;

    public Map( String name, TmxMapLoader loader){
        this.mapName = name;
        tiledMap = loader.load(name + ".tmx");
        e = new ArrayList<Entity>();
    }


    public void loadMap(){
        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

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
            if(object.getProperties().containsKey("slime")){
                mapEntities.add(MainGame.enemyFactory.createEnemy(Enemy.EnemyType.SLIME, new Vector2(rect.getX() / MainGame.PPM, rect.getY() / MainGame.PPM)));
            }
            if(object.getProperties().containsKey("bat")){
               mapEntities.add( MainGame.enemyFactory.createEnemy(Enemy.EnemyType.BAT, new Vector2(rect.getX() / MainGame.PPM, rect.getY() / MainGame.PPM)));
            }
            if(object.getProperties().containsKey("player")){
                playerSpawn = new Vector2(rect.getX() / MainGame.PPM, rect.getY() / MainGame.PPM);
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
            body.createFixture(fixtureDef).setUserData(object.getName());
            body.setUserData("teleport");
        }
        System.out.println(mapEntities.size());
    }
}
