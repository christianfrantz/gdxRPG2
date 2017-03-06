package com.gdx.rpg;

import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.math.Vector2;
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

        Player player;
        Entity enemy;

        if(fixtureA == null || fixtureB == null)return;
        if(fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null)return;

        if(isPlayerAttackContact(fixtureA, fixtureB)){
            enemy = fixtureB.getBody().getUserData() instanceof Entity ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();

            if(player.playerState == Player.PlayerState.ATTACKING) {
                float x = enemy.body.getLinearVelocity().x * player.attackForce;
                x = x * -1;
                float y = enemy.body.getLinearVelocity().y * player.attackForce;
                y = y * -1;
                enemy.body.applyLinearImpulse(new Vector2(x, y), enemy.body.getWorldCenter(), true);
                subject.notify(player, enemy, Event.ENEMY_DAMAGE);
            }
        }

        if(isOtherContactPlayer(fixtureA, fixtureB)){
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();
            enemy = fixtureB.getBody().getUserData() instanceof Enemy ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            subject.notify(player,enemy, Event.PLAYER_DAMAGE);
        }

        if(isEnemyChasePlayer(fixtureA, fixtureB)){
            enemy = fixtureA.getBody().getUserData().equals("CHASE_BODY") ? (Entity)fixtureA.getUserData() : (Entity)fixtureB.getUserData();
            enemy.enemyState = Enemy.EnemyState.ATTACKING;
        }

        if(isPlayerTeleport(fixtureA, fixtureB)){
            System.out.println(fixtureA.getUserData().toString());
            if(fixtureA.getBody().getUserData().equals("teleport"))
                MainGame.ChangeMap(fixtureA.getUserData().toString());
            else if(fixtureA.getBody().getUserData().equals("teleport")){
                MainGame.ChangeMap(fixtureB.getUserData().toString());
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
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

    public boolean isPlayerTeleport(Fixture a, Fixture b){
        if(a.getBody().getUserData().equals(Statics.PLAYER_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_BODY)){
            if(a.getBody().getUserData().equals("teleport") || b.getBody().getUserData().equals("teleport")){
                return true;
            }
        }
        return false;
    }
}
