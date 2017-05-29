package com.gdx.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdx.rpg.Entities.EnemyFactory;
import com.gdx.rpg.Entities.NPCFactory;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.HUD.HUD;
import com.gdx.rpg.Quests.Quest;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by imont_000 on 2/26/2017.
 */
public class MainGame extends Game {

    public static InputMultiplexer inputMultiplexer;

    public static DayNightCycle dayNightCycle;
    public static LightHandler lightHandler;

    public static enum GameState{
        PLAYING,
        PAUSE_MENU,
        PAUSE_CAMP,
        CAMP_FADE,
        CAMPING
    };

    public static GameState gameState;

    public static SpriteBatch batch;

    public static float vWidth = 1920;
    public static float vHeight = 1080;
    public static float PPM = 100;

    public static EnemyFactory enemyFactory = new EnemyFactory();
    public static NPCFactory npcFactory = new NPCFactory();

    public static HUD hud;

    public static Player player;

    public static ArrayList<Quest> playerQuests;
    public static HashMap<String, Quest> availableQuests = new HashMap<String, Quest>();

    public static HashMap<String, Map> gameMaps = new HashMap<String, Map>();
    public static ParticleEffectPool fireballPool;
    public static Map currentMap;
    public static Map mapToLoad;
    public static World world;
    public static TiledMapRenderer renderer;

    public static TmxMapLoader mapLoader;
    public static String currentPlayerSpawn;

    public static Array<ParticleEffectPool.PooledEffect> effects;
    public static ParticleEffect fireball;

    public static ArrayList<Projectile> projectilesOnScreen = new ArrayList<Projectile>();

    public MainGame(){
        availableQuests.put(Statics.KILL_SLIMES, new Quest(Statics.KILL_SLIMES, Quest.QuestType.KILL));
        availableQuests.put(Statics.KILL_BATS, new Quest(Statics.KILL_BATS, Quest.QuestType.KILL));
        availableQuests.put(Statics.FETCH_SLIMES, new Quest(Statics.FETCH_SLIMES, Quest.QuestType.FETCH));
    }

    @Override
    public void create() {
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        batch = new SpriteBatch();
        Pixmap pm = new Pixmap(Gdx.files.internal("cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));


        fireball = new ParticleEffect();
        fireball.load(Gdx.files.internal("fire.particle"), Gdx.files.internal(""));
        fireball.scaleEffect(0.005f);
        fireballPool = new ParticleEffectPool(fireball, 0, 100);
        effects = new Array<ParticleEffectPool.PooledEffect>();

       
        playerQuests = new ArrayList<Quest>();
        mapLoader = new TmxMapLoader();

        gameMaps.put(Statics.M_MAIN_MAP, new Map(Statics.M_MAIN_MAP, mapLoader));
        gameMaps.put(Statics.M_HOUSE, new Map(Statics.M_HOUSE, mapLoader));
        gameMaps.put(Statics.M_HOUSE_2, new Map(Statics.M_HOUSE_2, mapLoader));
        gameMaps.put(Statics.M_PURGATORY, new Map(Statics.M_PURGATORY, mapLoader));
        gameMaps.put(Statics.M_TOWER_FLOOR_1, new Map(Statics.M_TOWER_FLOOR_1, mapLoader));

        currentMap = gameMaps.get(Statics.M_MAIN_MAP);

        world = new World(new Vector2(0, 0), true);

        gameMaps.get(Statics.M_MAIN_MAP).loadMap();

        renderer = new OrthogonalTiledMapRenderer(MainGame.currentMap.tiledMap, 1 / MainGame.PPM);

        setScreen(new PlayScreen(this));
        gameState = GameState.PLAYING;
    }

    public void SetPlayScreen(){
        setScreen(new PlayScreen(this));
    }

    public static void ChangeMap(String name){
        player.setNeedToMove(true);
        mapToLoad = gameMaps.get(name);
    }

    public static void PurgatoryLoad(){
        player.setNeedToMove(true);
        mapToLoad = gameMaps.get(Statics.M_PURGATORY);
        player.health = 5;
        MainGame.hud.health = 5;
        MainGame.hud.playerHealthLabel.setText("Health: " + player.health);
    }

    public static String getCurrentPlayerSpawn(){
        return currentPlayerSpawn;
    }

    public static void setCurrentPlayerSpawn(String s){
        currentPlayerSpawn = s;
    }
}
