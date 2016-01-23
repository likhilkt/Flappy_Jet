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
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.flappy.race.Assets.Assets;
import com.flappy.race.Utils.Constants;
import com.flappy.race.Utils.HighScore;
import com.flappy.race.android.IActivityRequestHandler;


/**
 * Created by Likhil on 8/30/2015.
 */
public class MenuScreen extends AbstractGameScreen {
    Stage stage;
    Skin defaultSkin;
    BitmapFont font12;
    TextButton playTextButton,helpTextButton,aboutTextButton,shareTextButton,lbdTextButton;
    TextButton.TextButtonStyle textButtonStyle;

    Cell playCell,aboutCell,helpCell;
    IActivityRequestHandler iActivityRequestHandler;
    public MenuScreen(Game game,IActivityRequestHandler myRequestHandler){
        super(game);
        Gdx.input.setCatchBackKey(false);
        iActivityRequestHandler= myRequestHandler;
        //setMyGameCallback(myGameCallback);
        stage = new Stage();

        defaultSkin = new Skin(Gdx.files.internal(Constants.SKIN_DEFAULT_UI));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        parameter.shadowOffsetX=1;
        parameter.shadowColor=Color.WHITE;
        parameter.color=Color.ORANGE;

        font12 = generator.generateFont(parameter); // font size 12 pixel
        generator.dispose();

       // textButtonStyle = new TextButton.TextButtonStyle();

    }



    @Override
    public void dispose() {
        System.out.println("dispose");
        super.dispose();
        font12.dispose();
        defaultSkin.dispose();
        stage.dispose();

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(255/255f, 0/255f, 0/255f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        // create the table actor
    }

    private void onPlayClicked() {

        game.setScreen(new CarSelectScreen(game,iActivityRequestHandler));//dummy level and vid..need to change after creating leve and veh secting page
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table( defaultSkin );
        table.setSize(stage.getWidth(), stage.getHeight());

        //Drawable bg= (new Image(new Texture(Gdx.files.internal("bggg.jpg"))).getDrawable());
        //table.setBackground(bg);
        // add the table to the stage and retrieve its layout
        stage.addActor(table);


        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font12;
        textButtonStyle.up = new Image(Assets.instance.assetButtons.about).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetButtons.aboutpress).getDrawable();
        aboutTextButton = new TextButton("",defaultSkin);
        aboutTextButton.setStyle(textButtonStyle);
        table.add(aboutTextButton).width(115).height(140).pad(10);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font12;
        textButtonStyle.up = new Image(Assets.instance.assetButtons.lbd).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetButtons.lbdpress).getDrawable();
        lbdTextButton = new TextButton("",defaultSkin);
        lbdTextButton.setStyle(textButtonStyle);
        aboutCell = table.add(lbdTextButton).width(115).height(140).pad(10);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font12;
        textButtonStyle.up = new Image(Assets.instance.assetButtons.play).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetButtons.playpress).getDrawable();

        playTextButton = new TextButton("",defaultSkin);
        playTextButton.setStyle(textButtonStyle);
        //playTextButton.setSize(10, 10);
        playCell = table.add(playTextButton).width(155).height(187).pad(10);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font12;
        textButtonStyle.up = new Image(Assets.instance.assetButtons.help).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetButtons.helppress).getDrawable();
        helpTextButton = new TextButton("",defaultSkin);
        helpTextButton.setStyle(textButtonStyle);
        helpCell = table.add(helpTextButton).width(115).height(140).pad(10);


        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font12;
        textButtonStyle.up = new Image(Assets.instance.assetButtons.share).getDrawable();
        textButtonStyle.down = new Image(Assets.instance.assetButtons.sharepress).getDrawable();
        shareTextButton = new TextButton("",defaultSkin);
        shareTextButton.setStyle(textButtonStyle);
        table.add(shareTextButton).width(115).height(140).pad(10);



        helpTextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //playTextButton.setText("Loading...");
                //playCell.width(135).height(130);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                //playCell.width(140).height(138);
                iActivityRequestHandler.fbLike();
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });




        aboutTextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //playTextButton.setText("Loading...");
                //playCell.width(135).height(130);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                //playCell.width(140).height(138);
                iActivityRequestHandler.showCredits(true);
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });


        shareTextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //playTextButton.setText("Loading...");
                //playCell.width(135).height(130);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                //playCell.width(140).height(138);
                iActivityRequestHandler.share();
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });



        playTextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //playTextButton.setText("Loading...");
                playCell.width(135).height(130);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                //playCell.width(140).height(138);
                onPlayClicked();
                // worldController.keyUp(Input.Keys.SPACE);
            }
        });


        lbdTextButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                //playTextButton.setText("Loading...");
                playCell.width(135).height(130);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                //playCell.width(140).height(138);

                iActivityRequestHandler.onStartSomeActivity(new HighScore().getHighScore(), new HighScore().getLongDist());
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
}
