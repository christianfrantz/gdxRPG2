package com.gdx.rpg.Components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.rpg.MainGame;
import com.gdx.rpg.Entities.Player;
import com.gdx.rpg.Statics;

public class PlayerPhysicsComponent {

    private Body body;
    private Body attackBody;
    private Body dialogBody;

    public PlayerPhysicsComponent(Player player, Vector2 position){
        createBody(player, position);
        this.body = player.body;
        this.attackBody = player.attackBody;
        this.dialogBody = player.dialogBody;
    }

    private void createBody(Player player, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        player.body = MainGame.world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16 / MainGame.PPM, 32 / MainGame.PPM);

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
        attackFixture.restitution = 3f;
        attackFixture.shape = attackShape;

        player.attackBody.setUserData(Statics.PLAYER_ATTACK_BODY);
        player.attackBody.createFixture(attackFixture).setUserData(player);

        BodyDef dialogBodyDef = new BodyDef();
        dialogBodyDef.position.set(position.x, position.y);
        dialogBodyDef.type = BodyDef.BodyType.DynamicBody;

        player.dialogBody = MainGame.world.createBody(dialogBodyDef);

        FixtureDef dialogFixture = new FixtureDef();
        PolygonShape dialogShape = new PolygonShape();
        dialogShape.setAsBox(80 / MainGame.PPM, 80 / MainGame.PPM);
        dialogFixture.isSensor = true;
        dialogFixture.shape = dialogShape;

        player.dialogBody.setUserData(Statics.PLAYER_DIALOG_BODY);
        player.dialogBody.createFixture(dialogFixture).setUserData(player);

        player.dialogBody.setFixedRotation(true);
        player.body.setLinearDamping(10f);
        player.attackBody.setLinearDamping(10f);
        player.dialogBody.setLinearDamping(10f);
    }

    public void updatePhysics(Player player, float speed){
            switch (player.moveDirection) {
                case DOWN:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(0, -speed), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(0, -speed), body.getWorldCenter(), true);
                    }

                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);


                    break;
                case UP:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(0, speed), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(0, speed), body.getWorldCenter(), true);
                    }
                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);

                    break;
                case LEFT:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(-speed, 0), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(-speed, 0), body.getWorldCenter(), true);

                    }
                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);

                    break;
                case RIGHT:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(speed, 0), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(speed, 0), body.getWorldCenter(), true);
                    }
                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);
                    break;
                case DOWN_LEFT:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(-speed, -speed), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(-speed, -speed), body.getWorldCenter(), true);

                    }
                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);

                    break;
                case DOWN_RIGHT:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(speed, -speed), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(speed, -speed), body.getWorldCenter(), true);

                    }
                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);

                    break;
                case UP_LEFT:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(-speed, speed), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(-speed, speed), body.getWorldCenter(), true);

                    }
                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);

                    break;
                case UP_RIGHT:
                    if(player.playerState == Player.PlayerState.MOVING) {
                        body.applyLinearImpulse(new Vector2(speed, speed), body.getWorldCenter(), true);
                        attackBody.applyLinearImpulse(new Vector2(speed, speed), body.getWorldCenter(), true);

                    }
                    dialogBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y, 0);

                    break;
            }

            switch (player.direction){
                case UP:
                    attackBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y + (70 / MainGame.PPM), 0);
                    break;
                case DOWN:
                    attackBody.setTransform(body.getWorldCenter().x, body.getWorldCenter().y - (70 / MainGame.PPM), 0);
                    break;
                case LEFT:
                    attackBody.setTransform(body.getWorldCenter().x - (60 / MainGame.PPM), body.getWorldCenter().y, 90 * MathUtils.degreesToRadians);
                    break;
                case RIGHT:
                    attackBody.setTransform(body.getWorldCenter().x + (60 / MainGame.PPM), body.getWorldCenter().y, 90 * MathUtils.degreesToRadians);
                    break;
                case DOWN_LEFT:
                    attackBody.setTransform(body.getWorldCenter().x -(48 / MainGame.PPM), body.getWorldCenter().y - (48 / MainGame.PPM), 87);
                    break;
                case DOWN_RIGHT:
                    attackBody.setTransform(body.getWorldCenter().x +(48 / MainGame.PPM), body.getWorldCenter().y - (48 / MainGame.PPM), 180);
                    break;
                case UP_LEFT:
                    attackBody.setTransform(body.getWorldCenter().x -(48 / MainGame.PPM), body.getWorldCenter().y + (48 / MainGame.PPM), 180);
                    break;
                case UP_RIGHT:
                    attackBody.setTransform(body.getWorldCenter().x +(48 / MainGame.PPM), body.getWorldCenter().y +(48 / MainGame.PPM), 87);
                    break;
            }

    }
}
