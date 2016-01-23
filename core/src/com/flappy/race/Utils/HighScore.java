package com.flappy.race.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Likhil on 11/22/2015.
 */
public class HighScore {
    Preferences prefs;
    public HighScore() {
        prefs = Gdx.app.getPreferences(Constants.PREFS);
    }

    public long getLongDist(){
        return prefs.getLong(Constants.HDIST,0);
    }
    public long getHighScore(){
        return prefs.getLong(Constants.HSCORE,0);
    }

    public boolean isHigh(long ndist, long nscore){
        long dist = prefs.getLong(Constants.HDIST,0);
        long score = prefs.getLong(Constants.HSCORE,0);

        if(nscore>score){
            prefs.putLong(Constants.HSCORE,nscore);
        }

        if(ndist>dist){
            prefs.putLong(Constants.HDIST,ndist);
        }
        prefs.flush();
        return nscore>score;
    }
}
