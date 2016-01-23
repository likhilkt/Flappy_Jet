package com.flappy.race.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * Created by Likhil on 9/25/2015.
 */
public class UserData extends Box2DSprite {
    public int TYPE = -1;

    public UserData(TextureRegion textureRegion) {
        super(textureRegion);
    }

    public UserData(TextureRegion textureRegion, int type) {
        super(textureRegion);
        this.TYPE = type;
    }

    public UserData(Texture texture, int type) {
        super(texture);
        this.TYPE = type;
    }

    public UserData(Texture img) {
        super(img);
    }


    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }
}
