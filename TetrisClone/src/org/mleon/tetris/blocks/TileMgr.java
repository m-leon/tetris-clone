package org.mleon.tetris.blocks;

import java.util.ArrayList;

import org.mleon.tetris.util.Log;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileMgr {
    public static final int MAX_BLOCK_WIDTH = Tile.TILE_WIDTH * 4;
    public static final int MAX_BLOCK_HEIGHT = Tile.TILE_HEIGHT * 2;

    public static int timePassed;
    public static Image[] blockImages;
    public static boolean blockFalling = false;
    public static int boardX, boardY, boardWidth, boardHeight;

    private static ArrayList<Tile> tiles = new ArrayList<Tile>();

    public static void init(int x, int y, int width, int height) {
        boardX = x;
        boardY = y;
        boardWidth = width;
        boardHeight = height;
        blockImages = createBlockImages(Tile.tileImages);
    }

    public static void render(Graphics g) {
        int x, y;
        Image img;
        Tile current;
        for (int i = 0; i < tiles.size(); i++) {
            current = tiles.get(i);
            img = Tile.tileImages[current.getType()];
            x = boardX + (current.getX() * Tile.TILE_WIDTH);
            y = boardY + (current.getY() * Tile.TILE_HEIGHT);
            g.drawImage(img, x, y);
        }
    }

    public static void update(int delta) {
        timePassed += delta;
    }

    private static Image[] createBlockImages(Image[] tileImages) {
        Image[] returnValue = new Image[Tile.BLOCK_SCHEMA.length];
        Graphics g;
        for (int i = 0; i < Tile.BLOCK_SCHEMA.length; i++) {
            try {
                returnValue[i] = new Image(MAX_BLOCK_WIDTH, MAX_BLOCK_HEIGHT);
                g = returnValue[i].getGraphics();
                for (int j = 0; j < Tile.BLOCK_SCHEMA[i].length; j++)
                    for (int k = 0; k < Tile.BLOCK_SCHEMA[i][j].length; k++)
                        if (Tile.BLOCK_SCHEMA[i][j][k] == 1)
                            g.drawImage(Tile.tileImages[i], (Tile.TILE_WIDTH * k), (Tile.TILE_HEIGHT * j));

                g.flush();
            } catch (SlickException e) {
                // TODO: Close
                Log.error(e);
            }
        }
        return returnValue;
    }
}
