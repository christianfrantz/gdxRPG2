package com.gdx.rpg;

import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Observer.DamageObserver;
import com.gdx.rpg.Observer.Event;
import com.gdx.rpg.Observer.Subject;


/**
 * Created by imont_000 on 2/27/2017.
 */
public class WorldContactListener implements ContactListener{

    Subject subject;

    public WorldContactListener(){
        subject = new Subject();
        subject.AddObserver(new DamageObserver());
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //Gdx.app.log("begin contact", "between " + fixtureA.getBody().getUserData() + " and " + fixtureB.getBody().getUserData());
        Player player;
        Entity enemy;

        if(fixtureA == null || fixtureB == null)return;
        if(fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null)return;

        if(isPlayerAttackContact(fixtureA, fixtureB)){
            enemy = fixtureB.getBody().getUserData() instanceof Entity ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();

            if(player.playerState == Player.PlayerState.ATTACKING) {
                subject.notify(player, enemy, Event.ENEMY_DAMAGE);
            }
        }

        if(isOtherContactPlayer(fixtureA, fixtureB)){
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();
            enemy = fixtureB.getBody().getUserData() instanceof Enemy ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            subject.notify(player, Event.PLAYER_DAMAGE);
        }

        if(isEnemyChasePlayer(fixtureA, fixtureB)){
            enemy = fixtureA.getBody().getUserData().equals("CHASE_BODY") ? (Entity)fixtureA.getUserData() : (Entity)fixtureB.getUserData();
            System.out.println(enemy.id);
            enemy.enemyState = Enemy.EnemyState.ATTACKING;

        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //Gdx.app.log("end contact", "between " + fixtureA.getBody().getUserData() + " and " + fixtureB.getBody().getUserData());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerAttackContact(Fixture a, Fixture b){
       if (a.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY)){
            if(a.getBody().getUserData() instanceof Enemy || b.getBody().getUserData() instanceof Enemy){
               return true;
           }
        }
        return false;
    }

    public boolean isOtherContactPlayer(Fixture a, Fixture b){
        if (a.getBody().getUserData() instanceof Enemy|| b.getBody().getUserData() instanceof Enemy){
            if(a.getBody().getUserData().equals(Statics.PLAYER_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_BODY)){
                return true;
            }
        }
        return false;
    }

    public boolean isEnemyChasePlayer(Fixture a, Fixture b){
        if(a.getBody().getUserData().equals("CHASE_BODY") || b.getBody().getUserData().equals("CHASE_BODY")){
            if(a.getBody().getUserData().equals(Statics.PLAYER_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_BODY)){
                return true;
            }
        }
        return false;
    }
}
