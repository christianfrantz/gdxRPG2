package com.gdx.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdx.rpg.Entities.EnemyFactory;
import com.gdx.rpg.Entities.Entity;
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
    public static Map mapToLoad;
    public static World world;
    public static TiledMapRenderer renderer;

    public static TmxMapLoader mapLoader;

    public MainGame(){
        availableQuests.put(Statics.KILL_SLIMES, new Quest(Statics.KILL_SLIMES, Quest.QuestType.KILL));
        availableQuests.put(Statics.KILL_BATS, new Quest(Statics.KILL_BATS, Quest.QuestType.KILL));
        availableQuests.put(Statics.FETCH_SLIMES, new Quest(Statics.FETCH_SLIMES, Quest.QuestType.FETCH));
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        mapLoader = new TmxMapLoader();

        gameMaps.put(Statics.M_MAIN_MAP, new Map(Statics.M_MAIN_MAP, mapLoader));
        gameMaps.put(Statics.M_HOUSE, new Map(Statics.M_HOUSE, mapLoader));

        currentMap = gameMaps.get(Statics.M_MAIN_MAP);

        world = new World(new Vector2(0, 0), true);

        gameMaps.get(Statics.M_MAIN_MAP).loadMap();

        renderer = new OrthogonalTiledMapRenderer(MainGame.currentMap.tiledMap, 1 / MainGame.PPM);

        setScreen(new PlayScreen(this));
    }

    public static void ChangeMap(String name){
        player.needToMove = true;
        mapToLoad = gameMaps.get(name);
    }
}
