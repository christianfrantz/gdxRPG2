package com.gdx.rpg.Observer;

import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Item;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Projectile;
import com.gdx.rpg.Quests.Quest;
import com.gdx.rpg.Statics;
import com.sun.javaws.Main;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class DamageObserver implements Observer {

    @Override
    public void onNotify(Entity enemy, Event event) {
        switch (event){
        }
    }

    @Override
    public void onNotify(Player player, Event event) {
        switch (event){
            case PLAYER_DAMAGE:{
                MainGame.player.health -= 10;
                MainGame.hud.health = MainGame.player.health;
                MainGame.hud.playerHealthLabel.setText(Statics.HUD_HEALTH + MainGame.player.health);

                break;
            }
        }
    }

    @Override
    public void onNotify(Player player, Entity enemy, Event event) {
        switch (event) {
            case ENEMY_DAMAGE:
                enemy.health -= player.attack;
                //MainGame.particleEffect.setPosition(enemy.body.getWorldCenter().x, enemy.body.getWorldCenter().y);
                //MainGame.particleEffect.start();
                System.out.println("ENEMY_DAMAGE : " + enemy.health  + " " + enemy.id + " " + enemy.flaggedForDelete);
                break;
            case PLAYER_DAMAGE:
                player.health -= 10;
                MainGame.hud.health = player.health;
                MainGame.hud.playerHealthLabel.setText(Statics.HUD_HEALTH + player.health);
                break;
        }
    }

    @Override
    public void onNotify(Projectile p, Entity enemy, Event event){
        switch (event) {
            case ENEMY_DAMAGE:
                if(enemy.enemyState == Entity.EnemyState.IDLE)
                    enemy.enemyState = Entity.EnemyState.ATTACKING;
            enemy.health -= MainGame.player.attack;
            //MainGame.particleEffect.setPosition(enemy.body.getWorldCenter().x, enemy.body.getWorldCenter().y);
            //MainGame.particleEffect.start();
                break;
        }
    }

    @Override
    public void onNotify(Quest quest, Event event) {

    }

    @Override
    public void onNotify(Item item, Event event) {

    }


}
