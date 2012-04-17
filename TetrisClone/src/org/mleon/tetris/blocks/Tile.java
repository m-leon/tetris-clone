package org.mleon.tetris.blocks;

import org.mleon.tetris.Core;
import org.mleon.tetris.states.PlayState;
import org.mleon.tetris.util.Log;

import org.newdawn.slick.Graphics;
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

    public static final int[][][] BLOCK_SCHEMA = {
        { // I
            {1, 1, 1, 1}
        },
        { // J
            {1},
            {1, 1, 1}
        },
        { // L
            {0, 0, 1},
            {1, 1, 1}
        },
        { // O
            {1, 1},
            {1, 1}
        },
        { // S
            {0, 1, 1},
            {1, 1}
        },
        { // T
            {0, 1},
            {1, 1, 1}
        },
        { // Z
            {1, 1},
            {0, 1, 1}
        }
    };

    private int x, y;
    private int type;

    public static void init() {
        tileImages = retrieveTileImages();
    }

    private static Image[] retrieveTileImages() {
        Image[] returnValue = new Image[BLOCK_SCHEMA.length];
        for (int i = 0; i < BLOCK_SCHEMA.length; i++) {
            try {
                returnValue[i] = new Image("./data/tile-" + i + ".png");
            } catch (SlickException e) {
                Log.error(e);
                Core.close(Core.EXIT_DATA_NOT_FOUND);
            }
        }
        return returnValue;
    }

    public Tile(int type, int x, int y) {
        this.type = type;
        setX(x);
        setY(y);
    }

    public void render(Graphics g) {
        Image img = Tile.tileImages[getType()];
        int drawX = PlayState.boardX + (getX() * Tile.TILE_WIDTH);
        int drawY = PlayState.boardY + (getY() * Tile.TILE_HEIGHT);
        g.drawImage(img, drawX, drawY);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
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
