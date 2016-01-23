package com.flappy.race.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.flappy.race.Assets.Assets;
import com.flappy.race.Utils.Constants;
import com.flappy.race.android.IActivityRequestHandler;


/**
 * Created by Likhil on 8/30/2015.
 */
public class CarSelectScreen extends AbstractGameScreen {
    //MenuScreen.MyGameCallback myGameCallback;
    Stage stage;
    Skin defaultSkin;
    BitmapFont font12;
    TextButton playTextButton, pl2, p13;
    TextButton.TextButtonStyle textButtonStyle;
    IActivityRequestHandler iActivityRequestHandler;

    public CarSelectScreen(Game game, IActivityRequestHandler myRequestHandler) {
        super(game);
        Gdx.input.setCatchBackKey(true);
        //this.myGameCallback = myGameCallback;
        this.iActivityRequestHandler = myRequestHandler;
        stage = new Stage();

        defaultSkin = new Skin(Gdx.files.internal(Constants.SKIN_DEFAULT_UI));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        parameter.shadowOffsetX = 1;
        parameter.shadowColor = Color.WHITE;
        parameter.color = Color.WHITE;

        font12 = generator.generateFont(parameter); // font size 12 pixel
        generator.dispose();


        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font12;


    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(255f / 255f, 0f / 255f, 0f / 255f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        // create the table actor


    }

    private void onPlayClicked(int car) {

        game.setScreen(new GameScreen(game, car, iActivityRequestHandler));//dummy level and vid..need to change after creating leve and veh secting page
    }

    Label label;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table(defaultSkin);
        table.setSize(stage.getWidth(), stage.getHeight());
        // add the table to the stage and retrieve its layout
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font12;


        labelStyle.fontColor = Color.WHITE;


        label = new Label("Select Control", defaultSkin);
        label.setStyle(labelStyle);
        //label.setSize(30,200);
        table.add(label).pad(30);


        p13 = new TextButton("BACK", defaultSkin);
        p13.setStyle(textButtonStyle);

        table.add(p13);
        table.row();

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font12;
        textButtonStyle.up = new Image(Assets.instance.assetButtons.tap).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetButtons.tappress).getDrawable();

        playTextButton = new TextButton("", defaultSkin);
        playTextButton.setStyle(textButtonStyle);

        table.add(playTextButton).width(stage.getWidth() / 2.3f).height(stage.getWidth() / 2.3f).pad(10);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font12;
        textButtonStyle.up = new Image(Assets.instance.assetButtons.motion).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetButtons.motionpress).getDrawable();

        pl2 = new TextButton("", defaultSkin);
        pl2.setStyle(textButtonStyle);

        table.add(pl2).width(stage.getWidth() / 2.3f).height(stage.getWidth() / 2.3f);


        /*p13 = new TextButton("Play3",defaultSkin);
        p13.setStyle(textButtonStyle);

        table.add(p13);
*/


        playTextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                playTextButton.setText("Loading...");
                hideButs();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                onPlayClicked(1);
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });


        pl2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                pl2.setText("Loading...");
                hideButs();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                onPlayClicked(2);
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });

        p13.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //playTextButton.setText("Loading...");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                //onPlayClicked(3);
                game.setScreen(new MenuScreen(game, iActivityRequestHandler));
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });


    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {
        super.dispose();
        font12.dispose();
        defaultSkin.dispose();
        stage.dispose();
    }

    public void hideButs() {
        //playTextButton.setVisible(false);
        //pl2.setVisible(false);
        //label.setText("Loading...");

    }
}
