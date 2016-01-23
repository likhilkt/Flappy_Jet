package com.flappy.race.WController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.flappy.race.Assets.Assets;
import com.flappy.race.Utils.BodyEditorLoader;
import com.flappy.race.Utils.Constants;
import com.flappy.race.Utils.UserData;

/**
 * Created by Likhil on 10/20/2015.
 */
public class Ball {
    float maxSpeed,lastMaxSpeed;
    Body mainBody;
    UserData box2DSprite,beam;

    final short GROUP_PLAYER = -1;
    int del;
    int maxSpeedFact = 1;

    Array<Body> bullets ;
    public Ball(){del=0;
    }

    public void dispose(){
       // img2.dispose();
       // img.dispose();

       /* while (mainBody.getFixtureList().size>0){
            mainBody.destroyFixture(mainBody.getFixtureList().pop());
        }
        world.destroyBody(mainBody);
        */
    }


    public Ball(World world,float x, float y){
        //initEveryShit
        bullets = new Array<Body>(20);
        maxSpeed=20;
        lastMaxSpeed = maxSpeed;
        del = 0;
        maxSpeedFact = 1;
        runway(world,x,y);
        BodyDef bodyDef = new BodyDef();
        //bodyDef.gravityScale=0;
        bodyDef.position.set(x,y);
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        mainBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction=1;
        fixtureDef.restitution=0.0f;
        fixtureDef.density = 2f;

       // fixtureDef.filter.groupIndex=GROUP_PLAYER;

        fixtureDef.filter.categoryBits = Constants.CATEGORY_FLIGHT;
        fixtureDef.filter.maskBits = Constants.MASK_FLIGHT;

        BodyEditorLoader bodyEditorLoader =new BodyEditorLoader(Gdx.files.internal("forest/jumps.json"));

        bodyEditorLoader.attachFixture(mainBody,"jet",fixtureDef,5);

        /*CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1);
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        mainBody.createFixture(fixtureDef);
        circleShape.dispose();*/


        box2DSprite = new UserData(Assets.instance.assetFlight.flightAtlasRegion, Constants.TYPE_BALL);
        box2DSprite.setZIndex(10);
        mainBody.setUserData(box2DSprite);
        //mainBody.setLinearVelocity(50,0);

        //img.dispose();
        fireInit(world);


    }

    public void runway(World world,float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x+12,y-0.5f);

        Body b = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(20,0.3f);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_WALL;
        fixtureDef.filter.maskBits = Constants.MASK_WALL;

        b.createFixture(fixtureDef);
        polygonShape.dispose();

        UserData userData = new UserData(Assets.instance.assetGear.gearAtlasRegion,-99);
        b.setUserData(userData);

    }


    public void nitro(){
        maxSpeedFact = 3;
    }
    public void releaseNitro(){
        maxSpeedFact = 1;
        mainBody.setLinearVelocity(maxSpeed,0);
    }

    public void setMaxSpeed() {
        //maxSpeed = lastMaxSpeed;
        maxSpeed=maxSpeed>45?45:maxSpeed+8f;
    }
    public void dcrMaxSpeed(){
        //lastMaxSpeed = maxSpeed;
        //maxSpeed=15;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getPositionX(){
        return mainBody.getPosition().x;
    }
    public float getPositionY(){
        return mainBody.getPosition().y;
    }

    public void goBitch(float acclY){

        if (mainBody.getLinearVelocity().x < maxSpeed * maxSpeedFact)
            mainBody.applyForceToCenter(300, 0, true);
        //if(Math.abs(acclY)>0.2) {
        if (Math.abs(mainBody.getLinearVelocity().y) < 35) {
            mainBody.applyForceToCenter(0, -acclY * 350, true);
            //mainBody.applyForceToCenter(0, -(Math.abs(acclY)/acclY )* 300, true);
        }
    //}
        //mainBody.setTransform(mainBody.getPosition(), -acclY / 20);
    }

    public void tapBitch(){
        if (mainBody.getLinearVelocity().x < maxSpeed * maxSpeedFact)
            mainBody.applyForceToCenter(300, 0, true);

    }
    public void tap(){
        //if (Math.abs(mainBody.getLinearVelocity().y) < 35)
            mainBody.applyForceToCenter(0,10000,true);

        System.out.println("fuck yoou su");
    }

    BodyDef fbodyDef;
    FixtureDef ffixtureDef;

    private void fireInit(World world){
        fbodyDef = new BodyDef();

        fbodyDef.type = BodyDef.BodyType.KinematicBody;

        ffixtureDef = new FixtureDef();
        ffixtureDef.density = 1;
        ffixtureDef.density = 0.1f;
        //ffixtureDef.filter.groupIndex = GROUP_PLAYER;

        ffixtureDef.filter.categoryBits = Constants.CATEGORY_BULLET;
        ffixtureDef.filter.maskBits = Constants.MASK_BULLET;

        fireFuck(world);
    }


    public Rectangle getBoundingRectangle(){

        return (box2DSprite.getBoundingRectangle());
    }
    private void fireFuck(World world){
        for(int i=0;i<20;i++){
            fbodyDef.position.set(mainBody.getPosition().x-10,mainBody.getPosition().y);
            Body b=world.createBody(fbodyDef);
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(0.6f,0.3f);
            ffixtureDef.shape = polygonShape;

            b.createFixture(ffixtureDef);
            polygonShape.dispose();
            beam = new UserData(Assets.instance.assetBullet.bulletAtlasRegion,Constants.TYPE_BULLET);
            beam.setZIndex(9f);
            b.setUserData(beam);

            bullets.add(b);
        }

    }

    public void resetBullet(Body temp){
            //Body temp = bullets.pop();

            temp.setLinearVelocity(0,0);
            temp.setTransform(0, 0, temp.getAngle());
            bullets.add(temp);

            //System.out.println("in resetbull "+ bullets.size);
    }

    public void fire(World world){
        if(bullets.size>0) {
            Body temp = bullets.removeIndex(0);
            temp.setTransform(mainBody.getPosition().x+3,mainBody.getPosition().y,temp.getAngle());
            temp.setLinearVelocity(120, mainBody.getLinearVelocity().y);
            //System.out.println("pop " + bullets.size + "total :"+world.getBodyCount());
            // bullets.add(temp);
        }
      /*  if(bullets.size>0) {
            Body temp = bullets.removeIndex(0);
            temp.setTransform(mainBody.getPosition().x+3,mainBody.getPosition().y+0.9f,temp.getAngle());
            temp.setLinearVelocity(120, mainBody.getLinearVelocity().y);
            //System.out.println("pop " + bullets.size + "total :"+world.getBodyCount());
            // bullets.add(temp);
        }
*/

    }


}
