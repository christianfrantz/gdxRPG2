package com.gdx.rpg.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.Event;
import com.gdx.rpg.Entities.Player;


/**
 * Created by imont_000 on 2/28/2017.
 */
public class PlayerInputComponent implements InputProcessor{

    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean upPressed = false;
    boolean downPressed = false;

    Player player;
    public PlayerInputComponent(Player player){
        this.player = player;
        Gdx.input.setInputProcessor(this);
    }

    public void updateInput(Player player){
        if(player.attackCounter == 0){
            player.playerState = Player.PlayerState.IDLE;
        }

        if(leftPressed && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.LEFT;
        }
         if(rightPressed && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.RIGHT;
        }
         if(upPressed && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.UP;
        }
         if(downPressed  && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.DOWN;
        }
         if(leftPressed && downPressed && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.DOWN_LEFT;
        }
         if(rightPressed && downPressed && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.DOWN_RIGHT;
        }
        if(leftPressed && upPressed && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.UP_LEFT;
        }
        if(rightPressed && upPressed && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.UP_RIGHT;
        }



        if(Gdx.input.justTouched() && player.playerState != Player.PlayerState.SWORD_ATTACK){
            player.playerState = Player.PlayerState.SWORD_ATTACK;
        }

        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && player.playerState != Player.PlayerState.PROJECTILE_ATTACK){
            player.playerState = Player.PlayerState.PROJECTILE_ATTACK;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
            player.nextDialog = true;
        }
        else{
            player.nextDialog = false;
        }

        if(player.mouseRelativePlayer.y > 1)
            player.direction = Entity.Direction.UP;
        if(player.mouseRelativePlayer.y < -1)
            player.direction = Entity.Direction.DOWN;
        if(player.mouseRelativePlayer.x < -1)
            player.direction = Entity.Direction.LEFT;
        if(player.mouseRelativePlayer.x > 1)
            player.direction = Entity.Direction.RIGHT;
        if(player.mouseRelativePlayer.y > 1 && player.mouseRelativePlayer.x > 1)
            player.direction = Entity.Direction.UP_RIGHT;
        if(player.mouseRelativePlayer.y > 1 && player.mouseRelativePlayer.x < -1)
            player.direction = Entity.Direction.UP_LEFT;
        if(player.mouseRelativePlayer.y < -1 && player.mouseRelativePlayer.x > 1)
            player.direction = Entity.Direction.DOWN_RIGHT;
        if(player.mouseRelativePlayer.y < -1 && player.mouseRelativePlayer.x < -1)
            player.direction = Entity.Direction.DOWN_LEFT;


        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){// && player.stamina >= 25){
            player.playerState = Player.PlayerState.DODGING;
            player.stamina -= 25;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.I) && player.showInventory)
            player.showInventory = false;
        else if(Gdx.input.isKeyJustPressed(Input.Keys.I) && !player.showInventory)
            player.showInventory = true;

        if(Gdx.input.justTouched()){
            for(Entity entity : MainGame.currentMap.mapEntities){
                if(entity.sprite.getBoundingRectangle().contains(player.mousePos)){
                    player.playerSubject.notify(entity, Event.CLICKED_ENTITY);
                }
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
                leftPressed = true;
                break;
            case Input.Keys.D:
                rightPressed = true;
                break;
            case Input.Keys.S:
                downPressed = true;
                break;
            case Input.Keys.W:
                upPressed = true;
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
                leftPressed = false;
                break;
            case Input.Keys.D:
                rightPressed = false;
                break;
            case Input.Keys.S:
                downPressed = false;
                break;
            case Input.Keys.W:
                upPressed = false;
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
