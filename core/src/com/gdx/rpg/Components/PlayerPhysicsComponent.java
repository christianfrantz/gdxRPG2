package com.gdx.rpg.Components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Player;
import com.gdx.rpg.Statics;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class PlayerPhysicsComponent {

    private Body body;
    private Body attackBody;

    public PlayerPhysicsComponent(Player player, Vector2 position){
        createBody(player, position);
        this.body = player.body;
        this.attackBody = player.attackBody;
    }

    private void createBody(Player player, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        player.body = MainGame.world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / MainGame.PPM, 32 / MainGame.PPM);

        fixtureDef.shape = shape;

        player.body.setUserData(Statics.PLAYER_BODY);
        player.body.createFixture(fixtureDef).setUserData(player);

        BodyDef attackBodyDef = new BodyDef();
        attackBodyDef.position.set(position.x, (position.y - 0.5f));
        attackBodyDef.type = BodyDef.BodyType.DynamicBody;

        player.attackBody = MainGame.world.createBody(attackBodyDef);

        FixtureDef attackFixture = new FixtureDef();
        PolygonShape attackShape = new PolygonShape();
        attackShape.setAsBox(64 / MainGame.PPM, 18 / MainGame.PPM);
        attackFixture.isSensor = true;
        attackFixture.shape = attackShape;


        player.attackBody.setUserData(Statics.PLAYER_ATTACK_BODY);
        player.attackBody.createFixture(attackFixture).setUserData(player);

        player.body.setLinearDamping(10f);
        player.attackBody.setLinearDamping(10f);
    }

    public void updatePhysics(Player player, float speed){

            switch (player.direction) {
                case DOWN:
                    if(player.playerState == Player.PlayerState.MOVING)
                        body.applyLinearImpulse(new Vector2(0, -speed), body.getWorldCenter(), true);
                    attackBody.applyLinearImpulse(new Vector2(0, -speed), body.getWorldCenter(), true);
                    attackBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y - (48 / MainGame.PPM), 0);
                    break;
                case UP:
                    if(player.playerState == Player.PlayerState.MOVING)
                        body.applyLinearImpulse(new Vector2(0, speed), body.getWorldCenter(), true);
                    attackBody.applyLinearImpulse(new Vector2(0, speed), body.getWorldCenter(), true);
                    attackBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y + (48 / MainGame.PPM), 0);
                    break;
                case LEFT:
                    if(player.playerState == Player.PlayerState.MOVING)
                        body.applyLinearImpulse(new Vector2(-speed, 0), body.getWorldCenter(), true);
                    attackBody.applyLinearImpulse(new Vector2(-speed, 0), body.getWorldCenter(), true);
                    attackBody.setTransform(body.getWorldCenter().x - (48 / MainGame.PPM), body.getWorldCenter().y, 90 * MathUtils.degreesToRadians);
                    break;
                case RIGHT:
                    if(player.playerState == Player.PlayerState.MOVING)
                        body.applyLinearImpulse(new Vector2(speed, 0), body.getWorldCenter(), true);
                    attackBody.applyLinearImpulse(new Vector2(speed, 0), body.getWorldCenter(), true);
                    attackBody.setTransform(body.getWorldCenter().x + (48 / MainGame.PPM), body.getWorldCenter().y, 90 * MathUtils.degreesToRadians);
                    break;
            }

    }
}
