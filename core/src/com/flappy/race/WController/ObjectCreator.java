package com.flappy.race.WController;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.flappy.race.Assets.Assets;
import com.flappy.race.Utils.Constants;
import com.flappy.race.Utils.UserData;

import java.util.Random;


/**
 * Created by Likhil on 9/5/2015.
 */
public class ObjectCreator {
    float X = -10;
    int lastY = 0;
    float centerY = 0;
    int dis = 10,mod1,mod2;
    float start,end;
    float muls[] = {1, 1.2f, 1.7f, 2.2f, 3.0f, 3.3f, 3.3f, 3.3f, 3.3f, 3.3f, 3.3f, 3.3f};
    int rxdMod=5;
    public ObjectCreator() {
        lastY = 0;
        gap = 21;
        dis = 10;
    }
    public void incEnySpd(){
        enemySpeedY = enemySpeedY>3?3:enemySpeedY+1f;
    }

    float gap = 15;
    float enemySpeedY=1f;
    //Texture img;// = Assets.instance.getAtlasRegion("coin");

    Array<Body> enemyBodies = new Array<Body>(20);
    public Array<Sprite> coinSprites;

    float x, y, bottom = -100, top = 100, y2;
    Texture textureSolid;
    Pixmap pix ;
    TextureRegion polyTextureRegion;
    Array<Float> enemyFloatArray;

    public ObjectCreator(float x, float y) {
        start = 0;end = 0 ;
        centerY = 0;
        rxdMod =5;
        mod1 = 1;
        mod2 = 3;
        height = 8;
        enemyBodies = new Array<Body>(20);
        enemyFloatArray = new Array<Float>(40);
        coinSprites = new Array<Sprite>(10);
        init();
        lastY = 0;
        gap = 22;
        dis = 20;
        //img = new Texture("enemy.png");
        this.x = x;
        this.y = y;
        y2 = 0;
    }

    public void createEnemy2(World world, float x, float y, int xspeed){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x+60,y+2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 10;
        fixtureDef.friction = 1;
        fixtureDef.restitution = 1;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(new float[]{0,0,3f,5.1f,6,0});

        fixtureDef.shape = polygonShape;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        polygonShape.dispose();


        UserData box2DSprite = new UserData(Assets.instance.assetEnemy.enemyAtlasRegion, Constants.TYPE_ENEMY1);
        //box2DSprite.setScale(1);
        body.setUserData(box2DSprite);
        body.setAngularVelocity(9f);


    }



    public Body createCoin(World world, float x, float y, int xspeed) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y + 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0f;

            Body body = world.createBody(bodyDef);
            CircleShape shape = new CircleShape();
            shape.setRadius(2f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 3;
            fixtureDef.friction = 0;
            fixtureDef.shape = shape;
            fixtureDef.restitution = 1;
            // final short GROUP_ENEMY = -10;
            // fixtureDef.filter.groupIndex=GROUP_ENEMY;

            fixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY;
            fixtureDef.filter.maskBits = Constants.MASK_ENEMY;

            body.createFixture(fixtureDef);
            UserData box2DSprite = new UserData(Assets.instance.assetEnemy.enemyAtlasRegion, Constants.TYPE_ENEMY);
            box2DSprite.setScale(1);
            body.setUserData(box2DSprite);
            shape.dispose();

            //body.setLinearVelocity(xspeed , 2);
            return body;


    }


    public float getx() {
        return x;
    }

    public float gety() {
        return y;
    }

    public Body genRandom2(World world, BodyDef.BodyType staticBody) {
        start = x;
         gap=gap<18?18:gap-0.8f;
        dis = dis == 5 ? 5 : dis - 1;
        Body dbody, ubody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.restitution = 0;//restitution;
        fixtureDef.friction = 1;

        fixtureDef.filter.categoryBits = Constants.CATEGORY_WALL;
        fixtureDef.filter.maskBits = Constants.MASK_WALL;

        //System.out.println("here");
        dbody = world.createBody(bodyDef);
        ubody = world.createBody(bodyDef);


        boolean up = true;

        PolygonSprite poly;


        PolygonShape polygonShape;
        PolygonRegion polyReg;

        for (int i = 0; i < 40; i++) {
            polygonShape = new PolygonShape();
            if (up)
                y2 += (random.nextFloat() + random.nextFloat() + 2 * random.nextFloat());
            else
                y2 -= (random.nextFloat() + random.nextFloat() + 2 * random.nextFloat());
            if (random.nextInt() % 18 == 0 || y2 < 20)
                up = true;
            else if (random.nextInt() % 9 == 0 || y2 > -5)
                up = false;
            float tx = x;
            float temp[] = new float[]{x, y - 0.3f, x, y, x += (random.nextFloat() + 3 + random.nextInt(dis)), y2, x, y2 - 0.3f};
            float temp2[] = new float[]{tx, y + gap + 0.3f, tx, y + gap, x, y2 + gap, x, y2 + gap + 0.3f};
            polygonShape.set(temp);

            fixtureDef.shape = polygonShape;
            Fixture ftemp = dbody.createFixture(fixtureDef);


            polyReg = new PolygonRegion(polyTextureRegion,
                    temp, new short[]{
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp.setUserData(poly);
            polygonShape.dispose();

            polygonShape = new PolygonShape();
            polygonShape.set(temp2);

            fixtureDef.shape = polygonShape;
            Fixture ftemp2 = ubody.createFixture(fixtureDef);

            polyReg = new PolygonRegion(polyTextureRegion,
                    temp2, new short[]{
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp2.setUserData(poly);
            polygonShape.dispose();


            y = y2;
            if (i > 6)
                if (i % mod2 == 0) {// || i%20==0 || i%23==0 || i%27==0 )
                        fireEnemy(x, y);
                        fireEnemy(x + 10, y);

                }
            if(i==15)
                setupCoin(x,y+gap/2);
        }


        dbody.setUserData(new Integer(4));
        ubody.setUserData(new Integer(4));
        System.out.println("im here bitch");

        end = x;

        return dbody;

    }

    Random random ;

    private void init(){

        pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xFF0000ff); // DE is red, AD is green and BE is blue.
        pix.fill();
        textureSolid = new Texture(pix);
        random = new Random();
        polyTextureRegion = new TextureRegion(textureSolid);

    }

    public void dispose(){
        pix.dispose();
        textureSolid.dispose();
        

    }

    public void initEnemy(World world){
        for(int i=0;i<20;i++){
            enemyBodies.add(createCoin(world,40000,-20,random.nextInt(10)));
        }
    }
    public void resetEnemy(Body temp){
        temp.setLinearVelocity(0,0);
        temp.setActive(false);
        temp.setTransform(40000, -10, temp.getAngle());
        enemyBodies.add(temp);
        System.out.println("in resetEnemy " + enemyBodies.size);
    }

    public void fireEnemy(float x, float y){
        if(enemyBodies.size>0) {
            Body temp = enemyBodies.removeIndex(0);
            temp.setActive(true);
            temp.setTransform(x,y+random.nextInt((int)gap/2),temp.getAngle());
            temp.setLinearVelocity(-7 + random.nextInt(14), enemySpeedY*6);
            System.out.println("pop " + enemyBodies.size);
            // bullets.add(temp);
        }
    }

    public Body genInitialpath(World world) {
        // gap=gap<13?13:gap-0.8f;

        Body dbody,ubody;
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density=1;
        fixtureDef.restitution=0;//restitution;
        fixtureDef.friction=1;

        fixtureDef.filter.categoryBits = Constants.CATEGORY_WALL;
        fixtureDef.filter.maskBits = Constants.MASK_WALL;
        //System.out.println("here");
        dbody=world.createBody(bodyDef);
        ubody = world.createBody(bodyDef);


        PolygonSprite poly;

        PolygonShape polygonShape;

        PolygonRegion polyReg;

            polygonShape=new PolygonShape();
            float tx=x;
            float temp[] =  new float[]{x,-40,x,-39.7f,x+=300,-0.3f,x,0f};
            float temp2[] = new float[]{tx,40, tx,39.7f, x,gap, x,gap+0.3f};
            polygonShape.set(temp);

            fixtureDef.shape=polygonShape;
            Fixture ftemp = dbody.createFixture(fixtureDef);

            polyReg = new PolygonRegion(polyTextureRegion,
                    temp, new short[] {
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp.setUserData(poly);
            polygonShape.dispose();

            polygonShape = new PolygonShape();
            polygonShape.set(temp2);

            fixtureDef.shape=polygonShape;
            Fixture ftemp2 = ubody.createFixture(fixtureDef);


            polyReg = new PolygonRegion(polyTextureRegion,
                    temp2, new short[] {
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp2.setUserData(poly);
            polygonShape.dispose();


          //  if((i+1)%mod2==0) {
          //      fireEnemy(x, y);
          //      fireEnemy(x + 10, y );
          //  }
          //  if(i==5)
          //      setupCoin(x,y+gap/2);



        dbody.setUserData(new Integer(4));
        ubody.setUserData(new Integer(4));
        System.out.println("im here bitch");
        return dbody;

    }

    public float getCenterY(){
        return centerY;
    }

    int height = 8;
    public Body genStraingh3(World world, BodyDef.BodyType staticBody) {
        centerY = y+(gap/2)+0.3f;
        // gap=gap<13?13:gap-0.8f;
        //pix.setColor(0x00FF00ff); // DE is red, AD is green and BE is blue.
        //pix.fill();
        height = height>=11?11:height+1;

        Body dbody,ubody;
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density=1;
        fixtureDef.restitution=0;//restitution;
        fixtureDef.friction=1;

        fixtureDef.filter.categoryBits = Constants.CATEGORY_WALL;
        fixtureDef.filter.maskBits = Constants.MASK_WALL;
        //System.out.println("here");
        dbody=world.createBody(bodyDef);
        ubody = world.createBody(bodyDef);


        PolygonSprite poly;

        PolygonShape polygonShape;

        PolygonRegion polyReg;
        float temp[],temp2[];
        for(int i=0;i<20;i++){
            polygonShape=new PolygonShape();
            float tx=x;

            float tyy=1+random.nextInt(height);
            if(i%3==0){
                if(i%2==0) {
                    x += 5 + random.nextInt(15);
                    temp = new float[]{tx, y - 0.3f, tx, y + tyy, x, y + tyy, x, y - 0.3f};
                    temp2 = new float[]{tx, y + gap + 0.3f, tx, y + gap, x, y + gap, x, y + gap + 0.3f};
                }
                else{
                        x+=50+random.nextInt(20);
                        temp =  new float[]{tx,y-0.3f,tx,y,x,y,x,y-0.3f};
                        temp2 = new float[]{tx,y+gap+0.3f, tx,y+gap, x,y+gap, x,y+gap+0.3f};
                }
            }
            else{
                if(i%2==0) {
                    x += 4 + random.nextInt(15);
                    temp = new float[]{tx, y - 0.3f, x, y , x, y - 0.3f};
                    temp2 = new float[]{tx, y + gap + 0.3f, tx, y + gap -tyy, x, y + gap-tyy, x, y + gap + 0.3f};
                }
                else{
                    x+=50+random.nextInt(20);
                    temp =  new float[]{tx,y-0.3f,tx,y,x,y,x,y-0.3f};
                    temp2 = new float[]{tx,y+gap+0.3f, tx,y+gap, x,y+gap, x,y+gap+0.3f};
                }

            }

            polygonShape.set(temp);

            fixtureDef.shape=polygonShape;
            Fixture ftemp = dbody.createFixture(fixtureDef);

            polyReg = new PolygonRegion(polyTextureRegion,
                    temp, new short[] {
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp.setUserData(poly);
            polygonShape.dispose();

            polygonShape = new PolygonShape();
            polygonShape.set(temp2);

            fixtureDef.shape=polygonShape;
            Fixture ftemp2 = ubody.createFixture(fixtureDef);


            polyReg = new PolygonRegion(polyTextureRegion,
                    temp2, new short[] {
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp2.setUserData(poly);
            polygonShape.dispose();


            if((i+1)%mod2==0) {
                fireEnemy(x, y);
                fireEnemy(x + 10, y );
            }
            if(i==5)
                setupCoin(x,y+gap/2);

        }


        dbody.setUserData(new Integer(4));
        ubody.setUserData(new Integer(4));
        System.out.println("im here bitch");




        return dbody;

    }



    public Body genStraingh2(World world, BodyDef.BodyType staticBody) {
        // gap=gap<13?13:gap-0.8f;
        centerY = y+(gap/2)+0.3f;
        Body dbody,ubody;
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density=1;
        fixtureDef.restitution=0;//restitution;
        fixtureDef.friction=1;

        fixtureDef.filter.categoryBits = Constants.CATEGORY_WALL;
        fixtureDef.filter.maskBits = Constants.MASK_WALL;
        //System.out.println("here");
        dbody=world.createBody(bodyDef);
        ubody = world.createBody(bodyDef);


        PolygonSprite poly;

        PolygonShape polygonShape;

        PolygonRegion polyReg;

        rxdMod = rxdMod==2?2:rxdMod-1;

        for(int i=0;i<20;i++){

            polygonShape=new PolygonShape();
            float tx=x;
            float temp[] =  new float[]{x,y-0.3f,x,y,x+=20+random.nextInt(8),y,x,y-0.3f};
            float temp2[] = new float[]{tx,y+gap+0.3f, tx,y+gap, x,y+gap, x,y+gap+0.3f};
            polygonShape.set(temp);

            fixtureDef.shape=polygonShape;
            Fixture ftemp = dbody.createFixture(fixtureDef);

            polyReg = new PolygonRegion(polyTextureRegion,
                    temp, new short[] {
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp.setUserData(poly);
            polygonShape.dispose();

            polygonShape = new PolygonShape();
            polygonShape.set(temp2);

            fixtureDef.shape=polygonShape;
            Fixture ftemp2 = ubody.createFixture(fixtureDef);


            polyReg = new PolygonRegion(polyTextureRegion,
                    temp2, new short[] {
                    0, 1, 2,         // Two triangles using vertex indices.
                    0, 2, 3          // Take care of the counter-clockwise direction.
            });
            poly = new PolygonSprite(polyReg);
            ftemp2.setUserData(poly);
            polygonShape.dispose();


            if((i+1)%mod2==0) {
                fireEnemy(x, y);
                fireEnemy(x + 10, y );
            }
            if(i==5)
                setupCoin(x,y+gap/2);


            if((i+1)%rxdMod==0){
                popRdx(x,y,1+random.nextInt(((int)rdxBodies.size/3)+1));
                System.out.println("RDX");
            }

        }


        dbody.setUserData(new Integer(4));
        ubody.setUserData(new Integer(4));





        return dbody;

    }

    Array<Body> rdxBodies = new Array<Body>(14);//3x3+2x2
    int rdxIndex=0;
    public void initRdx(World world){
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.active = false;


        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.restitution=1f;
        fixtureDef.density = 3;


        PolygonShape polygonShape =new PolygonShape();
        polygonShape.setAsBox(1.5f,1.5f);

        fixtureDef.shape = polygonShape;
        fixtureDef.friction = 1;
        //bodyDef.gravityScale = 0.5f;//-9.8f;
        fixtureDef.filter.categoryBits = Constants.MASK_RDX;
        fixtureDef.filter.maskBits = Constants.MASK_RDX;

        for(int i=0;i<14;i++){
            if(i%2==0)
                bodyDef.gravityScale = 0.1f;
            else
                bodyDef.gravityScale = -0.1f;

            bodyDef.position.set(40000+i*2,0);
            body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);

            UserData userData = new UserData(Assets.instance.assetRdx.rdxAtlasRegion,Constants.TYPE_RDX);
            body.setUserData(userData);

            rdxBodies.add(body);
        }
        polygonShape.dispose();
    }
    public void resetRdx(Body body){
        body.setActive(false);
        body.setTransform(40000, 0, body.getAngle());
        rdxBodies.add(body);
    }
    public void popRdx(float x, float y, int count){
        if(rdxBodies.size>=count){
            for(int i=0;i<count;i++)
            {
                Body body = rdxBodies.pop();
                body.setTransform(x+i,y+0.35f+i*3,body.getAngle());
                body.setActive(true);
            }
        }
    }




    public Sprite createRealCoin() {
        Sprite sprite = new Sprite(Assets.instance.assetCoin.coinAtlasRegion);
        sprite.setSize(2, 2);
        sprite.setPosition(-20, 0);
        return sprite;
    }



    public void initCoin(){
        for(int i=0;i<10;i++)
            coinSprites.add(createRealCoin());
    }
    public void resetCoin(int i){
        coinSprites.get(i).setAlpha(0);
        coinSprites.get(i).setPosition(-20, 0);
    }
    public void setupCoin(float x, float y){
        for(int i=0;i<coinSprites.size;i++){
            coinSprites.get(i).setAlpha(1);
            coinSprites.get(i).setPosition(x+5*i,y);
        }
    }
    public void resetAllSprites(){
        for(int i=0;i<coinSprites.size;i++){
            coinSprites.get(i).setAlpha(0);
            coinSprites.get(i).setPosition(-20,0);
        }
    }



    public void updateMode() {
        mod1 = mod1==1?1:mod1-1;
        mod2 = mod2==3?3:mod2-1;
    }

    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }
}

