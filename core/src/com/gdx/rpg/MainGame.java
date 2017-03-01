package com.gdx.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.rpg.Enemy.Enemy;
import com.gdx.rpg.Enemy.EnemyFactory;
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

    public static HUD hud;

    public static HashMap<String, Enemy> enemies = new HashMap<String, Enemy>();

    public MainGame(){

    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        setScreen(new PlayScreen(this));
    }
}
