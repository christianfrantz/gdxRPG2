package com.gdx.rpg.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Observer.Event;
import com.gdx.rpg.Entities.Player;


/**
 * Created by imont_000 on 2/28/2017.
 */
public class PlayerInputComponent {

    public PlayerInputComponent(Player player){

    }

    public void updateInput(Player player){
        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.playerState != Player.PlayerState.ATTACKING){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.LEFT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && player.playerState != Player.PlayerState.ATTACKING){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.RIGHT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.W) && player.playerState != Player.PlayerState.ATTACKING){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.UP;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)  && player.playerState != Player.PlayerState.ATTACKING){
            player.playerState = Player.PlayerState.MOVING;
            player.moveDirection = Entity.Direction.DOWN;
        }
        else if(player.attackCounter == 0){
            player.playerState = Player.PlayerState.IDLE;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F) && player.playerState != Player.PlayerState.ATTACKING){
            player.playerState = Player.PlayerState.ATTACKING;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
            player.nextDialog = true;
        }
        else{
            player.nextDialog = false;
        }

        if(player.mouseRelativePlayer.y > 01)
            player.direction = Entity.Direction.UP;
        if(player.mouseRelativePlayer.y < -01)
            player.direction = Entity.Direction.DOWN;
        if(player.mouseRelativePlayer.x < -01)
            player.direction = Entity.Direction.LEFT;
        if(player.mouseRelativePlayer.x > 01)
            player.direction = Entity.Direction.RIGHT;

        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){
            player.playerState = Player.PlayerState.DODGING;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.I) && player.showInventory)
            player.showInventory = false;
        else if(Gdx.input.isKeyJustPressed(Input.Keys.I) && !player.showInventory)
            player.showInventory = true;

        if(Gdx.input.justTouched()){
            for(Entity entity : MainGame.currentMap.mapEntities){
                if(entity.sprite.getBoundingRectangle().contains(player.cursor.getBoundingRectangle())){
                    player.playerSubject.notify(entity, Event.CLICKED_ENTITY);
                }
            }
        }
    }
}
