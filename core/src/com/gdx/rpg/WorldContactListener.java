package com.gdx.rpg;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.Entities.Enemy;
import com.gdx.rpg.Entities.Entity;
import com.gdx.rpg.Entities.NPC;
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
        Entity entity;
        Projectile projectile;

        if(fixtureA == null || fixtureB == null)return;
        if(fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null)return;

        if(isProjectileHitEnemy(fixtureA, fixtureB)){
            entity = fixtureB.getBody().getUserData() instanceof Entity ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            projectile = fixtureA.getBody().getUserData().equals(Statics.PLAYER_PROJECTILE) ? (Projectile)fixtureA.getUserData() : (Projectile)fixtureB.getUserData();

            subject.notify(projectile, entity, Event.ENEMY_DAMAGE);
//            projectile.isActive = false;
            System.out.println("PROJECTILE HIT ENEMY");
        }

        if(isProjectileHitPlayer(fixtureA, fixtureB)){
            projectile = fixtureB.getBody().getUserData().equals(Statics.ENEMY_PROJECTILE) ? (Projectile)fixtureB.getUserData() : (Projectile)fixtureA.getUserData();
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();

//            projectile.body.setActive(false);
            subject.notify(player, Event.PLAYER_DAMAGE);
            System.out.println("PROJECTILE HIT PLAYER");

        }

        if(isPlayerAttackContact(fixtureA, fixtureB)){
            entity = fixtureB.getBody().getUserData() instanceof Entity ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();

            if(player.playerState == Player.PlayerState.SWORD_ATTACK) {
                float x = entity.body.getLinearVelocity().x * player.attackForce;
                x = x * -1;
                float y = entity.body.getLinearVelocity().y * player.attackForce;
                y = y * -1;
                entity.body.applyLinearImpulse(new Vector2(x, y), entity.body.getWorldCenter(), true);
                subject.notify(player, entity, Event.ENEMY_DAMAGE);
            }
        }

        if(isOtherContactPlayer(fixtureA, fixtureB)){
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();
            entity = fixtureB.getBody().getUserData() instanceof Enemy ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            subject.notify(player,entity, Event.PLAYER_DAMAGE);
        }

        if(isEnemyChasePlayer(fixtureA, fixtureB)){
            entity = fixtureA.getBody().getUserData().equals("CHASE_BODY") ? (Entity)fixtureA.getUserData() : (Entity)fixtureB.getUserData();
            entity.enemyState = Enemy.EnemyState.ATTACKING;
        }

        if(isPlayerTeleport(fixtureA, fixtureB)){
            if(fixtureA.getBody().getUserData().equals("teleport")) {
                MapObject object = (MapObject)fixtureA.getUserData();
                MainGame.ChangeMap(object.getName());
                MainGame.setCurrentPlayerSpawn(object.getProperties().get(object.getName()).toString());
            }
            else if(fixtureA.getBody().getUserData().equals("teleport")){
                MapObject object = (MapObject)fixtureB.getUserData();
                MainGame.ChangeMap(object.getName());
                MainGame.setCurrentPlayerSpawn(object.getProperties().get(object.getName()).toString());
            }
        }

        if(isPlayerContactNPC(fixtureA, fixtureB)){
            entity = fixtureA.getBody().getUserData() instanceof NPC ? (NPC)fixtureA.getBody().getUserData() : (NPC)fixtureB.getBody().getUserData();
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

    public boolean isProjectileHitPlayer(Fixture a, Fixture b){
        if(a.getBody().getUserData().equals(Statics.ENEMY_PROJECTILE) || b.getBody().getUserData().equals(Statics.ENEMY_PROJECTILE)){
            if(a.getBody().getUserData().equals(Statics.PLAYER_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_BODY)){
                System.out.println("PROJECTILE HIT PLAYER");
                return true;
            }
        }
        return false;
    }

    public boolean isProjectileHitEnemy(Fixture a, Fixture b){
        if(a.getBody().getUserData() instanceof Enemy || b.getBody().getUserData() instanceof Enemy){
            if(a.getBody().getUserData().equals(Statics.PLAYER_PROJECTILE) || b.getBody().getUserData().equals(Statics.PLAYER_PROJECTILE)){

                System.out.println("PROJECTILE HIT ENEMY");
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

    public boolean isPlayerContactNPC(Fixture a, Fixture b){
        if(a.getBody().getUserData().equals(Statics.PLAYER_DIALOG_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_DIALOG_BODY)){
            if(a.getBody().getUserData() instanceof NPC || b.getBody().getUserData() instanceof NPC){
                return true;
            }
        }

        return false;
    }
}
