package org.mleon.tetris.blocks;

import org.mleon.tetris.util.Log;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {
    public static Image[] tileImages;

    public static final int TILE_WIDTH = 30;
    public static final int TILE_HEIGHT = 30;

    public static final int BLOCK_I = 0;
    public static final int BLOCK_J = 1;
    public static final int BLOCK_L = 2;
    public static final int BLOCK_O = 3;
    public static final int BLOCK_S = 4;
    public static final int BLOCK_T = 5;
    public static final int BLOCK_Z = 6;

    public static final int[] AVAILABLE_BLOCKS = {
        BLOCK_I,
        BLOCK_J,
        BLOCK_L,
        BLOCK_O,
        BLOCK_S,
        BLOCK_T,
        BLOCK_Z
    };

    public static final int[][][] BLOCK_SCHEMA = {
        { // I
            {1, 1, 1, 1},
            {0, 0, 0, 0}
        },
        { // J
            {1, 0, 0, 0},
            {1, 1, 1, 0}
        },
        { // L
            {0, 0, 1, 0},
            {1, 1, 1, 0}
        },
        { // O
            {1, 1, 0, 0},
            {1, 1, 0, 0}
        },
        { // S
            {0, 1, 1, 0},
            {1, 1, 0, 0}
        },
        { // T
            {0, 1, 0, 0},
            {1, 1, 1, 0}
        },
        { // Z
            {1, 1, 0, 0},
            {0, 1, 1, 0}
        }
    };

    private int x, y;
    private int type;

    public static void init() {
        tileImages = retrieveTileImages();
    }

    private static Image[] retrieveTileImages() {
        Image[] returnValue = new Image[AVAILABLE_BLOCKS.length];
        for (int i = 0; i < AVAILABLE_BLOCKS.length; i++) {
            try {
                returnValue[i] = new Image("./data/tile-" + i + ".png");
            } catch (SlickException e) {
                // TODO: Close
                Log.error(e);
            }
        }
        return returnValue;
    }

    public Tile(int type) {
        this.type = type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }
}
