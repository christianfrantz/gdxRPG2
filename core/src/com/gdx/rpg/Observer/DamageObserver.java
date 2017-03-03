package com.gdx.rpg.Observer;

import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Quests.Quest;
import com.gdx.rpg.Statics;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class DamageObserver implements Observer {

    @Override
    public void onNotify(Entity enemy, Event event) {
        switch (event){
            case ENEMY_DAMAGE:{
                enemy.health -= 10;
                //System.out.println("ENEMY_DAMAGE : " + enemy.health + " " + enemy.id + " " + enemy.flaggedForDelete);
                break;
            }
        }
    }

    @Override
    public void onNotify(Player player, Event event) {
        switch (event){
            case PLAYER_DAMAGE:{
                player.health -= 10;
                MainGame.hud.health = player.health;
                MainGame.hud.playerHealthLabel.setText(Statics.HUD_HEALTH +  player.health);
                break;
            }
        }
    }

    @Override
    public void onNotify(Player player, Entity enemy, Event event) {
        switch (event) {
            case ENEMY_DAMAGE:
                enemy.health -= player.damage;
                System.out.println("ENEMY_DAMAGE : " + enemy.health  + " " + enemy.id + " " + enemy.flaggedForDelete);
                break;
            case PLAYER_DAMAGE:
                player.health -= 10;
                MainGame.hud.health = player.health;
                MainGame.hud.playerHealthLabel.setText(Statics.HUD_HEALTH + player.health);
        }
    }

    @Override
    public void onNotify(Quest quest, Event event) {

    }


}
