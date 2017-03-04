package com.gdx.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.rpg.Entities.EnemyFactory;
import com.gdx.rpg.Entities.NPCFactory;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.HUD.HUD;
import com.gdx.rpg.Quests.Quest;


import java.util.HashMap;

/**
 * Created by imont_000 on 2/26/2017.
 */
public class MainGame extends Game {
    public SpriteBatch batch;

    public static float vWidth = 800;
    public static float vHeight = 800;
    public static float PPM = 100;

    public static EnemyFactory enemyFactory = new EnemyFactory();
    public static NPCFactory npcFactory = new NPCFactory();

    public static HUD hud;

    public static Player player;

    public static HashMap<String, Quest> availableQuests = new HashMap<String, Quest>();

    public static HashMap<String, Map> gameMaps = new HashMap<String, Map>();
    public static Map currentMap;
    public static Map previousMap;
    public static World currentWorld;
    public static TiledMapRenderer renderer;

    private TmxMapLoader mapLoader;

    public MainGame(){
        availableQuests.put(Statics.KILL_SLIMES, new Quest(Statics.KILL_SLIMES, Quest.QuestType.KILL));
        availableQuests.put(Statics.KILL_BATS, new Quest(Statics.KILL_BATS, Quest.QuestType.KILL));
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        mapLoader = new TmxMapLoader();

        gameMaps.put(Statics.M_MAIN_MAP, new Map(Statics.M_MAIN_MAP, mapLoader));
        gameMaps.put(Statics.M_HOUSE, new Map(Statics.M_HOUSE, mapLoader));

        currentMap = gameMaps.get(Statics.M_MAIN_MAP);
        previousMap = currentMap;
        currentWorld = currentMap.world;

        gameMaps.get(Statics.M_HOUSE).loadMap();
        gameMaps.get(Statics.M_MAIN_MAP).loadMap();

        renderer = new OrthogonalTiledMapRenderer(MainGame.currentMap.tiledMap, 1 / MainGame.PPM);
        System.out.println(currentMap.playerSpawn);
        setScreen(new PlayScreen(this));
    }

    public static void ChangeMap(String name){
        currentMap = gameMaps.get(name);
        currentWorld = currentMap.world;
        renderer = new OrthogonalTiledMapRenderer(currentMap.tiledMap, 1 / MainGame.PPM);
        player.needToMove = true;
    }
}
