package com.gdx.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.rpg.Entities.EnemyFactory;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.NPCFactory;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.HUD.HUD;


import java.util.HashMap;

/**
 * Created by imont_000 on 2/26/2017.
 */
public class MainGame extends Game {
    public SpriteBatch batch;

    public static float vWidth = 800;
    public static float vHeight = 800;
    public static float PPM = 100;

    public static World world = new World(new Vector2(0, 0), true);

    public static EnemyFactory enemyFactory = new EnemyFactory();
    public static NPCFactory npcFactory = new NPCFactory();

    public static HUD hud;

    public static HashMap<String, Entity> entities = new HashMap<String, Entity>();
    public static Player player;

    public MainGame(){

    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        setScreen(new PlayScreen(this));
    }
}
