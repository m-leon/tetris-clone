package org.mleon.tetris.blocks;

import org.mleon.tetris.Core;
import org.mleon.tetris.util.Log;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {
    public static Image[] tileImages;

    public static final int TILE_WIDTH = 30;
    public static final int TILE_HEIGHT = 30;

    public static void init() {
        tileImages = retrieveTileImages();
    }

    private static Image[] retrieveTileImages() {
        Image[] returnValue = new Image[Block.BLOCK_SCHEMA.length];
        for (int i = 0; i < returnValue.length; i++) {
            try {
                returnValue[i] = new Image("data/tile-" + i + ".png");
            } catch (SlickException e) {
                Log.error(e);
                Core.close(Core.EXIT_DATA_NOT_FOUND);
            }
        }
        return returnValue;
    }
}
