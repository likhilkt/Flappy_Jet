package com.flappy.race.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.flappy.race.Assets.Assets;
import com.flappy.race.Utils.Constants;
import com.flappy.race.WController.WorldController;
import com.flappy.race.WController.WorldRenderer;
import com.flappy.race.android.IActivityRequestHandler;


/**
 * Created by Likhil on 8/30/2015.
 */
public class GameScreen extends AbstractGameScreen {

    @Override
    public void dispose() {
        super.dispose();
        font12.dispose();
        defaultSkin.dispose();
        stage.dispose();
    }

    private static final String TAG = GameScreen.class.getName();
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused, WAIT;
    float sx, sy;
    //modi
    Stage stage;
    Skin defaultSkin;
    BitmapFont font12;
    TextButton gr1TextButton, gr2TextButton, gr3TextButton, startTextButton, gr4TextButton;
    TextButton.TextButtonStyle textButtonStyle;
    Label.LabelStyle labelStyle;
    public ProgressBar bar;


    Label speedLabel, healthLabel;
    //emodi
    int LEVEL, VID;

    //public MenuScreen.MyGameCallback myGameCallback;
    public IActivityRequestHandler iActivityRequestHandler;

    public GameScreen(Game game, int vehicle, IActivityRequestHandler iActivityRequestHandler) {
        super(game);
        //this.myGameCallback = myGameCallback;
        this.iActivityRequestHandler = iActivityRequestHandler;
        WAIT = true;

        VID = vehicle;

        Gdx.input.setCatchBackKey(true);
        sx = Gdx.graphics.getWidth();
        sy = Gdx.graphics.getHeight();
        //modi
        stage = new Stage();

        defaultSkin = new Skin(Gdx.files.internal(Constants.SKIN_DEFAULT_UI));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal/*("Carnevale.ttf"));*/("Roboto.ttf"));//"DroidSerif-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 28;
        //parameter.borderColor = Color.WHITE;
        //parameter.borderWidth = 2;
        parameter.color = Color.YELLOW;


        font12 = generator.generateFont(parameter); // font size 12 pixel
        generator.dispose();

        textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.font = font12;
        textButtonStyle.up = new Image(Assets.instance.assetGear.gearAtlasRegion).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetGearDown.gearDownAtlasRegion).getDrawable();

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font12;
        labelStyle.fontColor = Color.WHITE;


        speedLabel = new Label("00", defaultSkin);
        healthLabel = new Label("Health ", defaultSkin);

//emodi


    }


    @Override
    public void render(float deltaTime) {
        if (!paused) {
            // Sets the clear screen color to: Cornflower Blue
            Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);

            // Clears the screen
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // Render game world to screen
            // Update game world by the time that has passed
            // since last rendered frame.
            // worldController.update(deltaTime);
            worldRenderer.render(deltaTime);
            speedLabel.setText("Coin : " + worldController.getCoinCount() + "   Hit : " + worldController.getCoins() + "   Distance : " + worldController.getDistance() + "m" + " Bullet : " + worldController.getFireCount());
            stage.act(deltaTime);
            stage.draw();

        }


    }

    float accX;


    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {
        worldController = new WorldController(game, VID, this);


        worldRenderer = new WorldRenderer(worldController);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(worldController);
        Gdx.input.setInputProcessor(multiplexer);


        Table table = new Table(defaultSkin);
        table.setSize(stage.getWidth(), stage.getHeight());
        // add the table to the stage and retrieve its layout
        stage.addActor(table);


        speedLabel.setPosition(10, sy - speedLabel.getHeight());
        speedLabel.setStyle(labelStyle);
        stage.addActor(speedLabel);

        gr1TextButton = new TextButton("TAP", defaultSkin);
        gr1TextButton.setStyle(textButtonStyle);
        gr1TextButton.setPosition(sx - 120, 0);
        gr1TextButton.setSize(120, 120);
        stage.addActor(gr1TextButton);

        gr1TextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                worldController.keyDown(Input.Keys.LEFT);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                worldController.keyUp(Input.Keys.LEFT);
            }
        });

        //Health meter
        TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(Assets.instance.assetHealth.healthAtlasRegion));
        textureBar.setMinHeight(30);
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(defaultSkin.newDrawable("white", Color.DARK_GRAY), textureBar);
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, 5, 1f, false, barStyle);
        bar.setValue(5);

        bar.setSize(150, bar.getPrefHeight());
        bar.setPosition(sx - bar.getWidth(), sy - bar.getHeight());
        bar.setAnimateDuration(1);
        stage.addActor(bar);

        healthLabel.setPosition(bar.getX() - healthLabel.getWidth() * 1.8f, sy - healthLabel.getHeight());
        healthLabel.setStyle(labelStyle);
        stage.addActor(healthLabel);


        /*gr2TextButton = new TextButton("",defaultSkin);
        gr3TextButton = new TextButton("up",defaultSkin);
        gr4TextButton = new TextButton("down",defaultSkin);

        startTextButton = new TextButton("Start",defaultSkin);

        gr2TextButton.setStyle(textButtonStyle);
        gr3TextButton.setStyle(textButtonStyle);
        gr4TextButton.setStyle(textButtonStyle);
        startTextButton.setStyle(textButtonStyle);
        // gr1TextButton.setPosition(sx - sy / 10, 0);
       // gr1TextButton.setSize(sy/10,sy/10);
        gr1TextButton.setPosition(sx - 120, 0);
        gr1TextButton.setSize(120, 120);

        gr2TextButton.setPosition(10, 0);
        gr2TextButton.setSize(120, 120);

        gr3TextButton.setPosition(sx - 240, 0);
        gr3TextButton.setSize(90, 90);


        gr4TextButton.setPosition(140, 0);
        gr4TextButton.setSize(90, 90);

        startTextButton.setSize(200, 80);
        startTextButton.setPosition(sx / 2, sy / 2);

        stage.addActor(gr1TextButton);
        stage.addActor(gr2TextButton);
        stage.addActor(gr3TextButton);
        stage.addActor(gr4TextButton);
       // stage.addActor(startTextButton);
        //gr1TextButton.addListener();

      /*  gr1TextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {


                //System.out.println("clicked");
                //onPlayClicked();
                worldController.keyDown(Input.Keys.LEFT);
            }
        });
*/
        // gr1TextButton.setBackground();
      /*  gr1TextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               // Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                worldController.keyDown(Input.Keys.LEFT);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               // Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                worldController.keyUp(Input.Keys.LEFT);
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });

        gr2TextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                worldController.keyDown(Input.Keys.DOWN);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                worldController.keyUp(Input.Keys.DOWN);
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });

        gr3TextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });


        gr4TextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //worldController.keyDown(Input.Keys.UP);

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                //worldController.keyUp(Input.Keys.UP);
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });

        startTextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                startTextButton.setText("loading levels");

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                // worldController.keyUp(Input.Keys.SPACE);
            }
        });


*/


    }

    public void showWin(String string) {


        Label label = new Label(string, defaultSkin);
        label.setWrap(true);
        label.setFontScale(1.2f);
        label.setStyle(labelStyle);
        label.setAlignment(Align.center);
        iActivityRequestHandler.showAds(true);
        Dialog dialog =
                new Dialog("Game Over", defaultSkin, "dialog") {
                    protected void result(Object object) {
                        //System.out.println("Chosen: " + object.getClass());
                        if ((Boolean) object) {
                            iActivityRequestHandler.showInt(true);
                            worldController.backToMenu();


                        } else {
                            iActivityRequestHandler.showAds(false);
                            //game.setScreen(new GameScreen(game,1));
                            System.out.println("what the fuck?");
                            worldController.resetAll();
                            worldRenderer.zoom = 3f;
                            bar.setValue(5);
                            WAIT = true;

                        }
                    }
                };

        dialog.padTop(50).padBottom(50);
        dialog.getContentTable().add(label).width(850).row();
        dialog.getButtonTable().padTop(50);

        TextButton dbutton = new TextButton("Go Back", defaultSkin);
        dialog.button(dbutton, true);


        dbutton = new TextButton("Play Again", defaultSkin);
        dialog.button(dbutton, false);
        dialog.key(Input.Keys.ENTER, true).key(Input.Keys.ESCAPE, false);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        worldController.world.dispose();
        worldController.disposeSounds();
        dispose();
        //Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }


}
