package com.gdx.rpg.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gdx.rpg.Components.PlayerGraphicsComponent;
import com.gdx.rpg.Components.PlayerInputComponent;
import com.gdx.rpg.Components.PlayerPhysicsComponent;
import com.gdx.rpg.HUD.Equips.Equips;
import com.gdx.rpg.HUD.Inventory.Inventory;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.ClickObserver;
import com.gdx.rpg.Observer.DamageObserver;
import com.gdx.rpg.Observer.PlayerSubject;
import com.gdx.rpg.Observer.QuestObserver;
import com.gdx.rpg.Projectile;
import com.gdx.rpg.Statics;

import java.util.ArrayList;


/**
 * Created by imont_000 on 2/23/2017.
 */
public class Player extends Entity {
    public int health = 100;
    public int stamina = 100;
    public int mana = 0;
    public int gold = 10;
    public int attack = 3;
    public int defense = 0;

    public enum PlayerClass{
        WARRIOR,
        MAGE,
        RANGER
    }

    public PlayerClass playerClass;

    public enum PlayerState{
        IDLE,
        MOVING,
        ATTACKING,
        DODGING
    }

    private PlayerPhysicsComponent physicsComponent;
    private PlayerInputComponent inputComponent;
    public PlayerGraphicsComponent graphicsComponent;

    public Body body;
    public Body attackBody;
    public Body dialogBody;

    public float dodgeSpeed = 6f;
    public float attackForce = 4f;
    private float speed = 0.5f;
    public PlayerState playerState;

    public float attackTime = 0.5f;
    public float attackCounter = 0;

    public PlayerSubject playerSubject;
    public float angle;
    public Vector2 mouseRelativePlayer;

    public boolean needToMove = false;

    public Inventory inventory;
    public Equips equips;

    public boolean showInventory;

    public boolean nextDialog = false;

    public ArrayList<Projectile> projectilesOnScreen = new ArrayList<Projectile>();
    public Vector2 mousePos;

    public Player( Vector2 position){
        super( position, "PLAYER");

        sprite = new Sprite(new Texture("Sprites/Player/player.png"));
        sprite.setBounds(0, 0, sprite.getTexture().getWidth() / MainGame.PPM, sprite.getTexture().getHeight() / MainGame.PPM);
        sprite.setOrigin(sprite.getTexture().getWidth() / MainGame.PPM, sprite.getTexture().getHeight() / MainGame.PPM);

        direction = Direction.DOWN;
        moveDirection = Direction.DOWN;
        playerState = PlayerState.IDLE;

        playerSubject = new PlayerSubject();
        playerSubject.AddObserver(new DamageObserver());
        playerSubject.AddObserver(new ClickObserver());
        playerSubject.AddObserver(new QuestObserver());

        inputComponent = new PlayerInputComponent(this);
        physicsComponent = new PlayerPhysicsComponent(this, position);
        graphicsComponent = new PlayerGraphicsComponent(this);

        inventory = new Inventory(10);
        equips = new Equips(4);

        inventory.AddItem(Item.CHEST);
        inventory.AddItem(Item.RING);
        inventory.AddItem(Item.BAT_WING);
        inventory.AddItem(Item.HEAD);
        inventory.AddItem(Item.SWORD);
        inventory.AddItem(Item.HEALTH_POTION);
    }

    public void updatePlayer(float delta, Camera cam){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

        Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(pos);
        mousePos = new Vector2(pos.x, pos.y);

        mouseRelativePlayer = new Vector2(mousePos.x - sprite.getX(), mousePos.y - sprite.getY());
        angle = mouseRelativePlayer.angle();

        inputComponent.updateInput(this);
        physicsComponent.updatePhysics(this, speed);

        for(Projectile p : projectilesOnScreen){
            p.update(delta, projectilesOnScreen);
        }

        if(needToMove){
            Array<Body> bodies = new Array<Body>();
            MainGame.world.getBodies(bodies);
            for(Body body : bodies){
                if (body.getUserData() instanceof Projectile){
                    MainGame.world.destroyBody(body);
                }
            }

            MainGame.mapToLoad.loadMap();
            MainGame.currentMap = MainGame.mapToLoad;
            if(MainGame.currentMap.mapName == Statics.M_PURGATORY){
                MainGame.setCurrentPlayerSpawn(MainGame.gameMaps.get(Statics.M_PURGATORY).playerSpawns.keySet().iterator().next());
            }

            MainGame.renderer = new OrthogonalTiledMapRenderer(MainGame.currentMap.tiledMap, 1 / MainGame.PPM);
            Vector2 newPosition = new Vector2(MainGame.currentMap.playerSpawns.get(MainGame.getCurrentPlayerSpawn()).x, MainGame.currentMap.playerSpawns.get(MainGame.getCurrentPlayerSpawn()).y);

            body.setTransform(newPosition, 0);
            needToMove = false;
            body.setAwake(true);
            playerState = PlayerState.IDLE;
        }

        switch (playerState){
            case IDLE:
                attackBody.setActive(false);
                break;
            case ATTACKING:
                if(playerClass == PlayerClass.WARRIOR){
                    attackCounter += Gdx.graphics.getDeltaTime();
                    attackBody.setActive(true);
                    if(attackCounter >= attackTime){
                        playerState = PlayerState.IDLE;
                        attackCounter = 0;
                    }
                }
                if(playerClass == PlayerClass.RANGER){
                    attackCounter += Gdx.graphics.getDeltaTime();
                    if(attackCounter >= attackTime){
                        shootProjectile(Projectile.ProjectileType.ARROW);
                        playerState = PlayerState.IDLE;
                        attackCounter = 0;
                    }
                }
                break;

            case DODGING:
                body.applyLinearImpulse(new Vector2(body.getLinearVelocity().x * dodgeSpeed, body.getLinearVelocity().y * dodgeSpeed), body.getWorldCenter(), true);
                break;
        }

        if(health <= 0){
            MainGame.PurgatoryLoad();
            health = 100;
        }
    }

    private void shootProjectile( Projectile.ProjectileType projectileType){
        Projectile projectile = new Projectile(this, projectileType);
        projectilesOnScreen.add(projectile);
        projectile.isActive = true;
    }
}
