package org.mleon.tetris.blocks;

import org.mleon.tetris.util.Log;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileMgr {
    public static final int MAX_BLOCK_WIDTH = Tile.TILE_WIDTH * 4;
    public static final int MAX_BLOCK_HEIGHT = Tile.TILE_HEIGHT * 2;

    public static Image[] blockImages;

    public static void init(int x, int y, int width, int height) {
        blockImages = createBlockImages(Tile.tileImages);
    }

    private static Image[] createBlockImages(Image[] tileImages) {
        Image[] returnValue = new Image[Tile.AVAILABLE_BLOCKS.length];
        Graphics g;
        for (int i = 0; i < Tile.AVAILABLE_BLOCKS.length; i++) {
            try {
                returnValue[i] = new Image(MAX_BLOCK_WIDTH, MAX_BLOCK_HEIGHT);
                g = returnValue[i].getGraphics();
                // TODO: Draw the images based off the schema
                g.flush();
            } catch (SlickException e) {
                // TODO: Close
                Log.error(e);
            }
        }
        return returnValue;
    }
}
