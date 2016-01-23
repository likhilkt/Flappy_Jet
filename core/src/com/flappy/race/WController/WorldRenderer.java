package com.flappy.race.WController;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.flappy.race.Utils.Constants;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;


/**
 * Created by Likhil on 8/30/2015.
 */
public class WorldRenderer implements Disposable {
    //private Box2DDebugRenderer b2debugRenderer;
    WorldController worldController;
    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;
    private OrthographicCamera camera;

    private SpriteBatch batch;
    private PolygonSpriteBatch polygonSpriteBatch;
    float VHX2;

    Texture background;
    boolean drawHill = true;
    float camY;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        drawHill = true;
        background = new Texture(Gdx.files.internal("bgn.jpg"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_HEIGHT);
        camY = Constants.VIEWPORT_HEIGHT / 2 + 1;
        // camera.zoom = 1.2f;


        //camera.position.set(worldController.myCar.getCarX() + 3, worldController.myCar.getCarY() + 3, 0);
        //camera.update();


        batch = new SpriteBatch();
        //b2debugRenderer = new Box2DDebugRenderer();
        polygonSpriteBatch = new PolygonSpriteBatch();

    }

    boolean sasi = true;
    public float zoom = 3f;

    public void render(float deltaTime) {
        updateBodies(deltaTime);

        accumulator += worldController.dt;
        while (accumulator >= worldController.dt && sasi) {
            worldController.world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
        if (worldController.isDist && worldController.ball.getPositionX() > worldController.start) {
            camera.position.set(worldController.ball.getPositionX() + 18, worldController.ball.getPositionY(), 0f);
            if (worldController.ball.getPositionX() > worldController.end)
                worldController.isDist = false;
        } else {
            camera.position.set(worldController.ball.getPositionX() + 18, worldController.centerY, 0f);
        }

        camera.update();
        //zoom = zoom<=1.3f?1.3f:zoom-0.01f;
        camera.zoom = 1.3f;

        batch.setProjectionMatrix(camera.combined);
        polygonSpriteBatch.setProjectionMatrix(camera.combined);

        renderObjects();
    }

    private void updateBodies(float dt) {
        while (worldController.deleteBodies.size > 0) {
            worldController.world.destroyBody(worldController.deleteBodies.pop());
        }
        worldController.update(dt);
    }


    private void renderObjects() {


        batch.begin();
        drawBg();
        Box2DSprite.draw(batch, worldController.world, false);
        worldController.p.setPosition(worldController.ball.getPositionX(), worldController.ball.getPositionY() + 0.9f);
        worldController.p.draw(batch);
        worldController.p2.setPosition(worldController.ball.getPositionX() + 2.5f, worldController.ball.getPositionY() + 0.4f);
        worldController.p2.draw(batch);
        worldController.enemyExp1.setPosition(worldController.ball.getPositionX() + 2.5f, worldController.ball.getPositionY() + 0.4f);
        worldController.enemyExp1.draw(batch);

        worldController.enemyExp.setPosition(worldController.eneExpPos.x, worldController.eneExpPos.y);
        worldController.enemyExp.draw(batch);

        worldController.rdxExp.setPosition(worldController.rdxPos.x, worldController.rdxPos.y);
        worldController.rdxExp.draw(batch);


        drawCoin(batch);
        batch.end();
        // if(drawHill)
        // {
        drawHill();
        //     drawHill=false;
        // }

        //b2debugRenderer.render(worldController.world, camera.combined);
        //worldController.p.update(worldController.dt);

    }

    private void drawCoin(SpriteBatch batch) {

        for (int i = 0; i < 10; i++)
            worldController.objectCreator.coinSprites.get(i).draw(batch);
    }

    private void drawHill() {
        polygonSpriteBatch.begin();

        Array<Body> list = new Array<Body>();
        worldController.world.getBodies(list);

        for (int i = 0; i < list.size; i++) {
            if (list.get(i).getUserData() instanceof Integer) {

                Array<Fixture> flist = list.get(i).getFixtureList();
                for (int j = 0; j < flist.size; j++) {
                    ((PolygonSprite) flist.get(j).getUserData()).draw(polygonSpriteBatch);
                }
            }

        }

        polygonSpriteBatch.end();

    }

    private void drawBg() {
        for (int i = -10; i < 10; i++)
            batch.draw(background, worldController.XX + i * Constants.VIEWPORT_WIDTH * 2f, worldController.YY - Constants.VIEWPORT_HEIGHT, Constants.VIEWPORT_WIDTH * 3f, Constants.VIEWPORT_HEIGHT * 3);


    }

    @Override
    public void dispose() {
        batch.dispose();
        polygonSpriteBatch.dispose();
        background.dispose();
    }
}
