package com.gdx.rpg.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.rpg.Components.PlayerInputComponent;
import com.gdx.rpg.Entities.NPC;

import com.gdx.rpg.HUD.Inventory.InventoryActor;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.Event;
import com.gdx.rpg.Observer.QuestObserver;
import com.gdx.rpg.Observer.Subject;
import com.gdx.rpg.Statics;
import com.gdx.rpg.WorldEventController;

/**
 * Created by imont_000 on 3/1/2017.
 */
public class HUD implements Disposable {
    public Stage stage;
    private Viewport viewport;
    public int health;

    public Label playerHealthLabel;

    private BitmapFont font;

    private SpriteBatch batch;

    public Dialog dialog;
    private Skin skin;
    private Subject subject;

    private Table table;
    private boolean dialogShown = false;

    public InventoryActor inventoryActor;

    public HUD(SpriteBatch batch){
        subject = new Subject();
        subject.AddObserver(new QuestObserver());

        this.batch = batch;
        viewport = new FitViewport(MainGame.vWidth, MainGame.vHeight, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        playerHealthLabel = new Label(Statics.HUD_HEALTH + health, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(playerHealthLabel).expandX().padTop(10);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("timesbd.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 20;
        font = generator.generateFont(parameter);
        font.getData().setScale(1);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        inventoryActor = new InventoryActor(MainGame.player.inventory, this, skin);
        stage.addActor(inventoryActor);

        stage.addActor(table);

    }
    public void ShowCampMenu(){
        if(!dialogShown) {
            dialogShown = true;
            dialog = new Dialog("Camp", skin, "dialog") {
                public void result(Object obj) {
                    if(obj.equals(false)){
                        dialogShown = false;
                        MainGame.gameState = MainGame.GameState.PLAYING;
                        Gdx.input.setInputProcessor(MainGame.inputMultiplexer);
                    }
                    if(obj.equals(true)){
                        dialogShown = false;
                        MainGame.gameState = MainGame.GameState.CAMP_FADE;
                        Gdx.input.setInputProcessor(MainGame.inputMultiplexer);
                    }
                }
            };

            dialog.text("Rest for the night?");
            dialog.setSize(400, 100);
            dialog.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            dialog.setMovable(false);

            dialog.button("Yes", true);
            dialog.button("No", false);
            stage.addActor(dialog);
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void ShowDialogue(final NPC entity){
        if(!dialogShown) {
            dialogShown = true;
            entity.isClicked = true;
            dialog = new Dialog(entity.id, skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("Result " + obj);
                    if (obj.equals(false)) {
                        dialogShown = false;
                        entity.isClicked = false;
                        System.out.println("Quest declined");
                    } else if (obj.equals(true)) {
                        subject.notify(entity.currentQuest, Event.ACCEPT_QUEST);
                        entity.currentDialogue = entity.currentQuest.duringQuest;
                        dialogShown = false;
                        entity.isClicked = false;
                        System.out.println("Quest accepted");
                    }
                }
            };
            dialog.text(entity.currentDialogue);
            dialog.setSize(400, 100);
            dialog.setPosition(100, 100);
            dialog.setMovable(false);

            if (entity.hasQuest && MainGame.playerQuests.contains(entity.currentQuest)) {
                dialog.button("Bye", false);
            } else if (entity.hasQuest) {
                dialog.button("Yes", true);
                dialog.button("No", false);
            } else if (!entity.hasQuest) {
                dialog.button("Bye", false);
            }
            dialog.key(Input.Keys.ENTER, true);
            stage.addActor(dialog);
            Gdx.input.setInputProcessor(stage);
        }
    }


    @Override
    public void dispose() {

    }
}
