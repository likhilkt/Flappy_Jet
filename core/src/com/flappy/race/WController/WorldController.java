package com.flappy.race.WController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.flappy.race.GameScreen.GameScreen;
import com.flappy.race.GameScreen.MenuScreen;
import com.flappy.race.Utils.Constants;
import com.flappy.race.Utils.HighScore;
import com.flappy.race.Utils.UserData;


/**
 * Created by Likhil on 8/30/2015.
 */
public class WorldController extends InputAdapter {
    public boolean safeFire = true, fireFuck;
    public World world;
    public float XX = 0, YY = 0, delDist;
    public float start, end;
    public Body prev, curr;
    HighScore highScore;
    int contrId;
    int health;
    boolean hitEffect, rdxEffect;
    Music music;
    Sound wavSound, eneExpSound, crashSound, pickupSound, hurtSound;
    int mainCount = 0;
    Vector2 eneExpPos, rdxPos;
    float dt;
    Array<Body> deleteBodies;
    Game game;
    Ball ball;
    ObjectCreator objectCreator;
    ParticleEffect p, p2, enemyExp, enemyExp1, rdxExp;
    long lastTime = 0;
    boolean hitWaitOver = false;
    boolean explode = false, delFalg, enemExpBoolean, kill, pause;
    int coinAnInt = 0, coinCount, rdxAnInt, enemyAnInt;
    int fuck = 0, fireCount = 0;
    Array<Body> resetList, rdxList;
    long score;
    boolean isDist;
    float centerY;
    GameScreen gameScreen;
    int xx = 0;
    float xx1 = 0;
    float xx2 = 0;
    boolean flag = true;
    int bCounter = 0;

    public WorldController(Game game, int vid, GameScreen gameScreen) {
        highScore = new HighScore();
        gameScreen.iActivityRequestHandler.showAds(false);
        contrId = vid;
        this.game = game;
        this.gameScreen = gameScreen;
        Gdx.input.setCatchBackKey(true);
        flag = false;
        loadAss();

        initSetup();

        music.play();


        flag = true;


    }

    public int getFireCount() {
        return fireCount;
    }

    private void initVars() {
        centerY = 0;
        isDist = false;
        score = 0;
        rdxAnInt = 0;
        enemyAnInt = 0;
        hitEffect = false;
        rdxEffect = false;
        lastTime = 0;
        hitWaitOver = true;
        health = 5;
        coinCount = 0;
        resetList = new Array<Body>();
        rdxList = new Array<Body>();
        fireCount = 0;
        safeFire = true;
        fireFuck = false;
        fuck = 0;
        coinAnInt = 0;
        pause = false;

        kill = true;
        mainCount = 0;
        enemExpBoolean = false;
        eneExpPos = new Vector2(0, 0);
        rdxPos = new Vector2(0, 0);
        XX = 0;
        YY = 0;
        delDist = 40;
        delFalg = true;
        world = new World(new Vector2(0f, -9f), true);

        deleteBodies = new Array<Body>();
        ball = new Ball(world, 0, 12);
        objectCreator = new ObjectCreator(-10, 0);
        //objectCreator.createEnemy2(world,5,5,0);
        objectCreator.initEnemy(world);
        objectCreator.initCoin();
        objectCreator.initRdx(world);

        explode = false;
        xx = 0;
        xx1 = 0;
        bCounter = 0;

        p.reset();
        p2.reset();
        enemyExp.reset();

    }

    public int getCoins() {
        return coinAnInt;
    }

    //vars
    public void loadAss() {

        p = new ParticleEffect();
        p.load(Gdx.files.internal("exh.p"), Gdx.files.internal(""));
        p.scaleEffect(0.05f);


        p2 = new ParticleEffect();
        p2.load(Gdx.files.internal("boom.p"), Gdx.files.internal(""));
        p2.scaleEffect(0.06f);


        enemyExp = new ParticleEffect();
        enemyExp.load(Gdx.files.internal("boom.p"), Gdx.files.internal(""));
        enemyExp.scaleEffect(0.05f);

        enemyExp1 = new ParticleEffect();
        enemyExp1.load(Gdx.files.internal("red_exp/red_explosion.p"), Gdx.files.internal("blue"));
        enemyExp1.scaleEffect(0.03f);

        rdxExp = new ParticleEffect();
        rdxExp.load(Gdx.files.internal("rdx.p"), Gdx.files.internal(""));
        rdxExp.scaleEffect(0.05f);


        /*enemyExp = new ParticleEffect();
        enemyExp.load(Gdx.files.internal("triangle_bomb/blue_explosion.p"), Gdx.files.internal("triangle_bomb"));
        enemyExp.scaleEffect(0.07f);
        */
        /*enemyExp = new ParticleEffect();
        enemyExp.load(Gdx.files.internal("red_exp/red_explosion.p"), Gdx.files.internal("red_exp"));
        enemyExp.scaleEffect(0.07f);
        */
        wavSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Hit_Hurt21.wav"));
        eneExpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Explosion.wav"));
        crashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/crash.wav"));
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Pickup_Coin.wav"));
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Hurt.wav"));

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/sondimg.mp3"));
        music.setLooping(true);
        music.setVolume(0.6f);


    }

    private void initSetup() {
        initVars();
        //curr=objectCreator.genRandom2(world, BodyDef.BodyType.StaticBody);
        curr = objectCreator.genStraingh2(world, BodyDef.BodyType.StaticBody);
        centerY = objectCreator.getCenterY();
        //curr = objectCreator.genInitialpath(world);
        setContact();

    }

    private void setContact() {


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {


                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();

                if (a.getUserData() instanceof UserData && b.getUserData() instanceof UserData) {
                    if (((UserData) a.getUserData()).TYPE == Constants.TYPE_BULLET) {
                        if (((UserData) b.getUserData()).TYPE == Constants.TYPE_ENEMY) {
                            coinAnInt++;
                            enemyAnInt++;
                            enemExpBoolean = true;
                            enemyExp.start();
                            eneExpSound.play();
                            eneExpPos = b.getPosition();
                            //objectCreator.resetEnemy(b);
                            resetList.add(b);
                            return;
                        } else if (((UserData) b.getUserData()).TYPE == Constants.TYPE_RDX) {
                            coinAnInt++;
                            rdxAnInt++;
                            rdxEffect = true;
                            rdxExp.start();
                            crashSound.play();//re-check it
                            rdxPos = b.getPosition();
                            rdxList.add(b);
                            return;
                        }
                    } else if (((UserData) b.getUserData()).TYPE == Constants.TYPE_BULLET) {
                        if (((UserData) a.getUserData()).TYPE == Constants.TYPE_ENEMY) {
                            coinAnInt++;
                            enemExpBoolean = true;
                            enemyExp.start();
                            eneExpSound.play();
                            eneExpPos = a.getPosition();
                            //objectCreator.resetEnemy(a);
                            resetList.add(a);
                            return;
                        } else if (((UserData) a.getUserData()).TYPE == Constants.TYPE_RDX) {
                            rdxEffect = true;
                            rdxExp.start();
                            crashSound.play();//re-check it
                            rdxPos = a.getPosition();
                            rdxList.add(a);
                            return;
                        }
                    }

                    if (((UserData) a.getUserData()).TYPE == Constants.TYPE_BALL) {
                        if (((UserData) b.getUserData()).TYPE == Constants.TYPE_RDX) {
                            rdxPos = b.getPosition();
                            explode = true;
                            rdxEffect = true;
                            p2.start();
                            rdxExp.start();
                        }
                    } else if (((UserData) b.getUserData()).TYPE == Constants.TYPE_BALL) {
                        if (((UserData) a.getUserData()).TYPE == Constants.TYPE_RDX) {
                            rdxPos = a.getPosition();
                            explode = true;
                            rdxEffect = true;
                            p2.start();
                            rdxExp.start();
                        }
                    }

                } else if (a.getUserData() instanceof Integer) {
                    if (b.getUserData() instanceof UserData)
                        if (((UserData) b.getUserData()).TYPE == Constants.TYPE_BALL) {
                            if (hitWaitOver) {
                                System.out.println("im hit : " + health--);
                                gameScreen.bar.setValue(health);
                                hitEffect = true;
                                enemyExp1.start();
                                hurtSound.play();
                                if (health == 0) {
                                    explode = true;
                                    p2.start();
                                }
                            }


                            checkHit();
                            return;
                        }

                } else if (b.getUserData() instanceof Integer) {
                    if (a.getUserData() instanceof UserData)
                        if (((UserData) a.getUserData()).TYPE == Constants.TYPE_BALL) {
                            if (hitWaitOver) {
                                System.out.println("im hit : " + health--);
                                gameScreen.bar.setValue(health);

                                hitEffect = true;
                                enemyExp1.start();
                                hurtSound.play();
                                if (health == 0) {
                                    explode = true;
                                    p2.start();
                                }
                            }
                            checkHit();
                            return;
                        }
                }


            }

            @Override
            public void endContact(Contact contact) {


            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });


    }

    public void checkHit() {
        long cTime = TimeUtils.millis();
        if (lastTime == 0) {
            lastTime = cTime;
        }
        if (hitWaitOver) {
            lastTime = cTime;
        }
        if (TimeUtils.timeSinceMillis(lastTime) > 1000)
            hitWaitOver = true;
        else {
            //System.out.println("false");
            hitWaitOver = false;
        }

    }

    public void update(float delta) {

        dt = delta;
        collisionCoinFlight();
        resetEnemy();
        genStage();
        if (!explode) {
            moveBall(delta);
               /* xx2++;
                if(xx2>10) { //impact
                    fire();
                    xx2=0;
                }*/
        }
        updateParticle(delta);

        if (explode && !pause) {
            xx++;
            if (xx == 1) {
                crashSound.play();
            }
            if (xx > 100) {
                xx = 0;
                pause = true;
                //disposeSounds();

                updateDeleteBodiesAll();
                gameScreen.showWin(getGameOverString());
            }

        }

        //delete old bodies
        if (ball.getPositionX() > delDist) {
            updateDeleteBodies();
        }
        killKinos();


    }

    private String getGameOverString() {
        score = (int) ball.getPositionX() + getCoinCount() * 20 + rdxAnInt * 50 + enemyAnInt * 30;
        String pre = "";

        if (highScore.isHigh((int) ball.getPositionX(), score))
            pre = "New High Score..!!\n\n";

        return pre + "Distance : " + getDistance() + "m\n" +
                "Coins   : " + getCoinCount() + " x 20\n" +
                "RDXBox  : " + rdxAnInt + " x 50\n" +
                "Bug     : " + enemyAnInt + " x 30\n" +
                "Total   : " + score + "\n\nLONGEST RUN : " + highScore.getLongDist();

    }

    private void updateDeleteBodiesAll() {
        Array<Body> bodyArray = new Array<Body>();
        world.getBodies(bodyArray);
        while (bodyArray.size > 0) {
            Body temp = bodyArray.pop();
            if (temp.getType() != BodyDef.BodyType.KinematicBody)
                deleteBodies.add(temp);
            temp = null;
        }
    }

    private void updateDeleteBodies() {
        delFalg = false;
        //System.out.println("in delete");
        float ds = ball.getPositionX() - 10;
        Array<Body> bodyArray = new Array<Body>();
        world.getBodies(bodyArray);


        while (bodyArray.size > 0) {
            Body temp = bodyArray.pop();
            if (temp.getType() == BodyDef.BodyType.DynamicBody && temp.getPosition().x < ds) {
                //deleteBodies.add(temp);
                // System.out.println("in if");
                if (((UserData) temp.getUserData()).TYPE == Constants.TYPE_ENEMY)
                    objectCreator.resetEnemy(temp);
                else if (((UserData) temp.getUserData()).TYPE == Constants.TYPE_RDX)
                    objectCreator.resetRdx(temp);
            }
        }
        delDist += 40;
        delFalg = true;
    }

    public void resetEnemy() {
        while (resetList.size > 0) {
            objectCreator.resetEnemy(resetList.pop());
        }
        while (rdxList.size > 0)
            objectCreator.resetEnemy(rdxList.pop());
    }

    public void disposeSounds() {
        wavSound.dispose();
        crashSound.dispose();
        eneExpSound.dispose();
        pickupSound.dispose();
        hurtSound.dispose();
        music.stop();
        music.dispose();
        p.dispose();
        p2.dispose();
        enemyExp.dispose();
        enemyExp1.dispose();
        rdxExp.dispose();
    }

    private void killKinos() {
        if (kill) {
            kill = false;
            Array<Body> temp = new Array<Body>();
            world.getBodies(temp);
            while (temp.size > 0) {
                Body tb = temp.pop();
                if (tb.getType() == BodyDef.BodyType.KinematicBody && tb.getPosition().x > ball.getPositionX() + 40) {
                    // tb.setActive(false);
                    // tb.setType(BodyDef.BodyType.StaticBody);
                    // tb.setUserData(null);
                    ball.resetBullet(tb);

                }
            }

            temp.clear();
            kill = true;
        }
    }

    private void updateParticle(float delta) {

        if (explode)
            p2.update(delta);
        else
            p.update(delta);
        if (enemExpBoolean)
            enemyExp.update(delta);
        if (enemyExp.isComplete()) enemExpBoolean = false;

        if (p.isComplete() && !explode) {
            p.reset();
        }
        if (hitEffect)
            enemyExp1.update(delta);
        if (enemyExp1.isComplete()) hitEffect = false;

        if (rdxEffect) {
            rdxExp.update(delta);
        }
        if (rdxExp.isComplete()) rdxEffect = false;
    }

    private void moveBall(float delta) {

        switch (contrId) {
            case 1:
                ball.tapBitch();
                break;
            case 2:
                try {
                    ball.goBitch(Gdx.input.getAccelerometerY());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void genStage() {

        if (flag && (ball.getPositionX() > objectCreator.getx() - 40)) {
            flag = false;
            XX = objectCreator.getx();
            YY = objectCreator.gety();

            flag = false;
            updateDeleteBodies();
            objectCreator.updateMode();
            objectCreator.resetAllSprites();
            if (mainCount == 0) {
                prev = curr;
                //curr =  .genInfinit(world, 20);
            } else {
                world.destroyBody(prev);
                //deleteBodies.add(prev);
                prev = curr;
                //curr = createBody();
            }

            if (mainCount % 2 == 0) {
                curr = objectCreator.genStraingh3(world, BodyDef.BodyType.StaticBody);
                centerY = objectCreator.getCenterY();

            } else if (mainCount % 3 == 0) {
                isDist = true;
                curr = objectCreator.genRandom2(world, BodyDef.BodyType.StaticBody);
                start = objectCreator.getStart();
                end = objectCreator.getEnd();
                // ball.dcrMaxSpeed();
            } else {
                curr = objectCreator.genStraingh2(world, BodyDef.BodyType.StaticBody);
                centerY = objectCreator.getCenterY();
            }
            ball.setMaxSpeed();
            objectCreator.incEnySpd();

            mainCount++;
            flag = true;


        }


    }


    public Body genInfinit(World world, float fx, float fy) {

        return null;
    }


    private void deadCheck() {

    }

    public void fire() {
        if (!explode && safeFire && fireFuck) {
            fireCount++;
            //System.out.println("fire");
            ball.fire(world);
            wavSound.play();
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        fireFuck = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        try {
            if (button == Input.Buttons.BACK) {
                backToMenu();
            } else if (!explode) {
                //System.out.println("fire");
                //ball.fire(world);
                //wavSound.play();
                //fire();
                fireFuck = true;
                fire();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    public void backToMenu() {
        // switch to menu screen
        gameScreen.iActivityRequestHandler.showInt(true);
        game.setScreen(new MenuScreen(game, gameScreen.iActivityRequestHandler));
    }

    public boolean keyDown(int keyCode) {

        if (Input.Keys.LEFT == keyCode) {
            //ball.nitro();
            ball.tap();
        } else if (Input.Keys.UP == keyCode) {
        } else if (keyCode == Input.Keys.BACK) {
            backToMenu();
        }
        ////System.out.println("KeyDown");
        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        //if (Input.Keys.LEFT == keyCode) {
            //ball.releaseNitro();
        //}
        return super.keyUp(keyCode);

    }


    public String getDistance() {

        return Integer.toString((int) ball.getPositionX());

    }

    public String getSpeed() {
        return Double.toString(9);
    }

    public void resetAll() {
        flag = false;
        objectCreator.dispose();
        world.dispose();
        ball.dispose();

        initSetup();
        flag = true;
    }


    public int getCoinCount() {
        return coinCount;
    }

    private void collisionCoinFlight() {
        Rectangle tempRectangle = new Rectangle(ball.getPositionX(), ball.getPositionY(), 3f, 1f);
        for (int i = 0; i < 10; i++) {
            if (objectCreator.coinSprites.get(i).getBoundingRectangle().overlaps(tempRectangle)) {
                objectCreator.resetCoin(i);
                pickupSound.play();
                coinCount++;
                //System.out.println("overlaps");

            }
        }
    }

}
