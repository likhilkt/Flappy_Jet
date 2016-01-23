package com.flappy.race.Utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Likhil on 8/30/2015.
 */
public class Constants {
    public static final String PREFS = "likhilprefsballs";

    public static final String SKIN_DEFAULT_UI = "uiskin.json";
    public static final String TEXTURE_ATLAS_UI = "";
    public static final float VIEWPORT_HEIGHT = 19.68f;
    public static final float VIEWPORT_WIDTH = 35.f;

    public static final float MAC_HILL_HEIGHT = 25.68f;


    public static final int TYPE_GROUND = 0,
            TYPE_BALL = 1, TYPE_ENEMY = 2,
            TYPE_ENEMY1 = 4,
            TYPE_BULLET = 3, TYPE_RDX = 5;

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 50000f;
    public static final float GROUND_HEIGHT = 3.5f;
    public static final float GROUND_DENSITY = 0f;
    public static final String HDIST = "hdist";
    public static final String HSCORE = "hscore";

    public static short CATEGORY_CAR = 0x0001;  // 0000000000000001 in binary
    public static short CATEGORY_NCAR = 0x0002; // 0000000000000010 in binary

    public static final String TEXTURE_ATLAS_OBJECTS =
            "forest/GameAssets.pack";
    public static final String BODY_LOAD_PATH =
            "forest/for_body.json";
    public static final String BODY_VEH_LOAD_PATH =
            "loader/valli.json";
    public static final String BODY_JMP_LOAD_PATH =
            "forest/jumps1.json";
    public static final String BODY_JMP_LOAD_PATH2 =
            "forest/jumps2.json";

    public static final String BODY_JMP_LOAD_PATH3 =
            "forest/jumps3.json";
    public static final String BODY_JMP_LOAD_PATH4 =
            "forest/jumps4.json";


    public static final short CATEGORY_FLIGHT = 0x0001;  // 0000000000000001 in binary
    public static final short CATEGORY_BULLET = 0x0002; // 0000000000000010 in binary
    public static final short CATEGORY_COIN = 0x0004; // 0000000000000100 in binary
    public static final short CATEGORY_ENEMY = 0x0008; // 0000000000000100 in binary
    public static final short CATEGORY_WALL = 0x0010; // 0000000000000100 in binary
    public static final short CATEGORY_RDX = 0x0100; // 0000000000000100 in binary


    public static final short MASK_FLIGHT = CATEGORY_ENEMY | CATEGORY_COIN | CATEGORY_WALL; // or ~CATEGORY_PLAYER
    public static final short MASK_BULLET = CATEGORY_ENEMY | CATEGORY_FLIGHT; // or ~CATEGORY_MONSTER
    public static final short MASK_COIN = CATEGORY_FLIGHT;
    public static final short MASK_ENEMY = CATEGORY_FLIGHT | CATEGORY_BULLET | CATEGORY_WALL;
    public static final short MASK_RDX = CATEGORY_FLIGHT | CATEGORY_BULLET | CATEGORY_WALL;
    public static final short MASK_WALL = -1;


}
