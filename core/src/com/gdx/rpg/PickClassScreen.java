package com.gdx.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.rpg.Entities.Player;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class PickClassScreen implements Screen {

    private MainGame game;

    private Stage stage;
    private Viewport viewport;
    private Button warriorButton;
    private Button mageButton;
    private Button rangerButton;

    public PickClassScreen(MainGame game){
        this.game = game;

        viewport = new FitViewport(MainGame.vWidth, MainGame.vHeight, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        Texture button = new Texture("warriorButton.png");
        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = new TextureRegionDrawable(new TextureRegion(button));
        warriorButton = new Button(bs);

        Texture button1 = new Texture("mageButton.png");
        Button.ButtonStyle bs1 = new Button.ButtonStyle();
        bs1.up = new TextureRegionDrawable(new TextureRegion(button1));
        mageButton = new Button(bs1);

        Texture button2 = new Texture("rangerButton.png");
        Button.ButtonStyle bs2 = new Button.ButtonStyle();
        bs2.up = new TextureRegionDrawable(new TextureRegion(button2));
        rangerButton = new Button(bs2);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(warriorButton).expandX().padTop(10);
        table.add(mageButton).expandX().padTop(10);
        table.add(rangerButton).expandX().padTop(10);

        warriorButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MainGame.playerClass = Player.PlayerClass.WARRIOR;
                MainGame.classChosen = true;
        }
        });

        mageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MainGame.playerClass = Player.PlayerClass.MAGE;
                MainGame.classChosen = true;
            }
        });

        rangerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MainGame.playerClass = Player.PlayerClass.RANGER;
                MainGame.classChosen = true;
            }
        });

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if(MainGame.classChosen) {
            game.SetPlayScreen();
            this.dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
