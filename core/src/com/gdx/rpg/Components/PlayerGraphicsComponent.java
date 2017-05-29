package com.gdx.rpg.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.MainGame;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class PlayerGraphicsComponent {

    private Animation walkUp;
    private Animation walkDown;
    private Animation walkLeft;
    private Animation walkRight;
    private Animation attackLeft;

    private float animationTime = 0;

    private TextureAtlas walkLeftAtlas;
    private TextureAtlas walkRightAtlas;
    private TextureAtlas walkUpAtlas;
    private TextureAtlas walkDownAtlas;
    private TextureAtlas attackLeftAtlas;

    private Player player;
    private Texture playerUp;
    private Texture playerDown;
    private Texture playerLeft;
    private Texture playerRight;
    private Texture playerDownLeft;
    private Texture playerDownRight;
    private Texture playerUpLeft;
    private Texture playerUpRight;

    public PlayerGraphicsComponent(Player player){
        this.player = player;
        playerUp = new Texture("Sprites/cloak guy/facingUp.png");
        playerDown = new Texture("Sprites/cloak guy/facingDown.png");
        playerLeft = new Texture("Sprites/cloak guy/facingLeft.png");
        playerRight = new Texture("Sprites/cloak guy/facingRight.png");
        playerDownLeft = new Texture("Sprites/cloak guy/downLeft.png");
        playerDownRight = new Texture("Sprites/cloak guy/downRight.png");
        playerUpLeft = new Texture("Sprites/cloak guy/upLeft.png");
        playerUpRight = new Texture("Sprites/cloak guy/upRight.png");

        walkUpAtlas = new TextureAtlas("Sprites/cloak guy/walkUp/walkUp.pack");
        walkDownAtlas = new TextureAtlas("Sprites/cloak guy/walkDown/walkDown.pack");
        walkLeftAtlas = new TextureAtlas("Sprites/cloak guy/walkLeft/walkLeft.pack");
        walkRightAtlas = new TextureAtlas("Sprites/cloak guy/walkRight/walkRight.pack");

        attackLeftAtlas = new TextureAtlas("Sprites/cloak guy/attackLeft/attackLeft.pack");

        walkDown = new Animation(1/6f, walkDownAtlas.getRegions());
        walkUp = new Animation(1/6f, walkUpAtlas.getRegions());
        walkRight = new Animation(1/6f, walkRightAtlas.getRegions());
        walkLeft = new Animation(1/6f, walkLeftAtlas.getRegions());

        attackLeft = new Animation(1/2, attackLeftAtlas.getRegions());
    }

    public void DrawPlayer(SpriteBatch batch){
        animationTime += Gdx.graphics.getDeltaTime();
        switch (player.direction){
            case LEFT:
                if(player.playerState == Player.PlayerState.IDLE) {
                    player.sprite.setRegion(playerLeft);
                }
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.LEFT
                        || player.moveDirection == Entity.Direction.RIGHT) {
                    player.sprite.setRegion(walkLeft.getKeyFrame(animationTime, true));
                }
                /*if(player.playerState == Player.PlayerState.SWORD_ATTACK){
                    player.sprite.setBounds(0, 0, 64 / MainGame.PPM, 64 / MainGame.PPM);
                    player.sprite.setRegion(attackLeft.getKeyFrame(animationTime, false));
                }*/
                break;
            case RIGHT:
                if(player.playerState == Player.PlayerState.IDLE)
                player.sprite.setRegion(playerRight);
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.LEFT
                        || player.moveDirection == Entity.Direction.RIGHT) {
                    player.sprite.setRegion(walkRight.getKeyFrame(animationTime, true));
                }
                break;
            case UP:
                if(player.playerState == Player.PlayerState.IDLE)
                    player.sprite.setRegion(playerUp);
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.UP
                        || player.moveDirection == Entity.Direction.DOWN) {
                    player.sprite.setRegion(walkUp.getKeyFrame(animationTime, true));
                }
                break;
            case DOWN:
                if(player.playerState == Player.PlayerState.IDLE)
                player.sprite.setRegion(playerDown);
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.DOWN
                        || player.moveDirection == Entity.Direction.UP) {
                    player.sprite.setRegion(walkDown.getKeyFrame(animationTime, true));
                }
                break;
            case DOWN_LEFT:
                if(player.playerState == Player.PlayerState.IDLE)
                    player.sprite.setRegion(playerDownLeft);
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.DOWN_LEFT
                        || player.moveDirection == Entity.Direction.UP_RIGHT) {
                    player.sprite.setRegion(playerDownLeft);
                }
                break;
            case DOWN_RIGHT:
                if(player.playerState == Player.PlayerState.IDLE){
                    player.sprite.setRegion(playerDownRight);
                }
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.DOWN_RIGHT
                        || player.moveDirection == Entity.Direction.UP_LEFT) {
                    player.sprite.setRegion(playerDownRight);
                }
                break;
            case UP_LEFT:
                if(player.playerState == Player.PlayerState.IDLE){
                    player.sprite.setRegion(playerUpLeft);
                }
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.UP_LEFT
                        || player.moveDirection == Entity.Direction.DOWN_RIGHT) {
                    player.sprite.setRegion(playerUpLeft);
                }
                break;
            case UP_RIGHT:
                if(player.playerState == Player.PlayerState.IDLE){
                    player.sprite.setRegion(playerUpRight);
                }
                 if(player.playerState == Player.PlayerState.MOVING && player.moveDirection == Entity.Direction.UP_RIGHT
                        || player.moveDirection == Entity.Direction.DOWN_LEFT) {
                    player.sprite.setRegion(playerUpRight);
                }
                break;
        }
        player.sprite.draw(batch);
        //player.cursor.draw(batch);
    }
}
