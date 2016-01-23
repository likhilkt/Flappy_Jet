package com.flappy.race.Assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.flappy.race.Utils.Constants;


public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public AssetEnemy assetEnemy;
    public AssetFlight assetFlight;
    public AssetBullet assetBullet;
    public AssetGear assetGear;
    public AssetGearDown assetGearDown;
    //public AssetEnemy1 assetEnemy1;
    public AssetCoin assetCoin;
    public AssetHealth assetHealth;
    public AssetRdx assetRdx;
    public AssetUp assetUp;
    public AssetButtons assetButtons;
    TextureAtlas atlas;

    public Assets(){}

    public void init(AssetManager assetManager){
        this.assetManager=assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        assetManager.finishLoading();
        System.out.println("# of assets loaded: " + assetManager.getAssetNames().size);

        atlas =assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        for (Texture t : atlas.getTextures())
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        assetEnemy = new AssetEnemy(atlas);
        //assetEnemy1 = new AssetEnemy1(atlas);
        assetFlight = new AssetFlight(atlas);
        assetBullet = new AssetBullet(atlas);
        assetGearDown = new AssetGearDown(atlas);
        assetGear = new AssetGear(atlas);
        assetCoin = new AssetCoin(atlas);
        assetHealth = new AssetHealth(atlas);
        assetRdx = new AssetRdx(atlas);
        assetUp = new AssetUp(atlas);
        assetButtons = new AssetButtons(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        atlas.dispose();
        assetManager.dispose();

    }

    public class AssetFlight {
        public final AtlasRegion flightAtlasRegion;
        public AssetFlight (TextureAtlas atlas) {
            flightAtlasRegion = atlas.findRegion("jet");

        }
    }

    public class AssetEnemy {
        public final AtlasRegion enemyAtlasRegion;
        public AssetEnemy (TextureAtlas atlas) {
            enemyAtlasRegion = atlas.findRegion("enemy2");
        }
    }

    public class AssetBullet {
        public final AtlasRegion bulletAtlasRegion;
        public AssetBullet (TextureAtlas atlas) {
            bulletAtlasRegion = atlas.findRegion("lasertext");
        }
    }

    public class AssetHealth {
        public final AtlasRegion healthAtlasRegion;
        public AssetHealth (TextureAtlas atlas) {
            healthAtlasRegion = atlas.findRegion("health");
        }
    }

    public class AssetGearDown {
        public final AtlasRegion gearDownAtlasRegion;
        public AssetGearDown (TextureAtlas atlas) {
            gearDownAtlasRegion = atlas.findRegion("geardown");
        }
    }

    public class AssetGear {
        public final AtlasRegion gearAtlasRegion;
        public AssetGear (TextureAtlas atlas) {
            gearAtlasRegion = atlas.findRegion("gear");
        }
    }

    /*public class AssetEnemy1{
        public final AtlasRegion enemy1AtlasRegion;
        public AssetEnemy1(TextureAtlas atlas){
            enemy1AtlasRegion = atlas.findRegion("enemy1");
        }
    }*/

    public class AssetCoin{
        public final AtlasRegion coinAtlasRegion;
        public AssetCoin(TextureAtlas atlas){
            coinAtlasRegion = atlas.findRegion("coin");
        }
    }

    public class AssetRdx{
        public final AtlasRegion rdxAtlasRegion;
        public AssetRdx(TextureAtlas atlas){
            rdxAtlasRegion = atlas.findRegion("rdx");
        }
    }

    public class AssetUp{
        public final AtlasRegion upAtlasRegion;
        public AssetUp(TextureAtlas atlas){upAtlasRegion = atlas.findRegion("healthup");}
    }

    public class AssetButtons {
        public final AtlasRegion play,help,about,share,lbd, playpress,helppress,aboutpress,sharepress,lbdpress,motion,tap,motionpress,tappress;
        public AssetButtons(TextureAtlas atlas) {

            play = atlas.findRegion("play");
            playpress = atlas.findRegion("playpress");
            help = atlas.findRegion("htp");
            helppress = atlas.findRegion("htppress");
            about = atlas.findRegion("about");
            aboutpress = atlas.findRegion("aboutpress");
            share = atlas.findRegion("share");
            sharepress = atlas.findRegion("sharepress");
            lbd = atlas.findRegion("lbd");
            lbdpress = atlas.findRegion("lbdpress");
            tap = atlas.findRegion("tctrl");
            motion = atlas.findRegion("mctrl");
            tappress = atlas.findRegion("tctrlpress");
            motionpress = atlas.findRegion("mctrlpress");
        }
    }

}


