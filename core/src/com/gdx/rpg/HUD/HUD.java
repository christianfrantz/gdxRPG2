package com.gdx.rpg.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.rpg.Entity;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Statics;

/**
 * Created by imont_000 on 3/1/2017.
 */
public class HUD implements Disposable {

    public Stage stage;
    private Viewport viewport;
    public int health;

    public Label playerHealthLabel;

    private BitmapFont font;

    private float damageTextTime = 1;
    private float damageTextCounter = 0;
    public boolean damageDrawn = false;
    private Entity entity;
    private String damage;

    private SpriteBatch batch;

    public HUD(SpriteBatch batch){
        this.batch = batch;
        viewport = new FitViewport(MainGame.vWidth, MainGame.vHeight, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        playerHealthLabel = new Label(Statics.HUD_HEALTH + health, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(playerHealthLabel).expandX().padTop(10);

        stage.addActor(table);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("times.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 4;
        font = generator.generateFont(parameter);

    }

    public void drawDamage(Entity entity, String damage){
        this.entity = entity;
        this.damage = damage;
        damageDrawn = true;
    }

    public void updateHud(){
        damageTextCounter += Gdx.graphics.getDeltaTime();

        if(damageDrawn){
            font.draw(batch, damage, entity.sprite.getX(), entity.sprite.getY() - 10 / MainGame.PPM);
        }

        if(damageTextCounter >= damageTextTime){
            damageDrawn = false;
            damageTextCounter = 0;
        }
    }

    @Override
    public void dispose() {

    }
}
