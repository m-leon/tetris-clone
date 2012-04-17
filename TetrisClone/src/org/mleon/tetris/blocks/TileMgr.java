package org.mleon.tetris.blocks;

import java.util.ArrayList;
import java.util.Arrays;

import org.mleon.tetris.states.PlayState;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TileMgr {
    public static int speed;
    public static int[][] tiles;
    public static int timePassed;
    public static int MIN_TILE_X = 0;
    public static int MAX_TILE_X = 9;
    public static int MIN_TILE_Y = 0;
    public static int MAX_TILE_Y = 19;
    public static boolean downPressed;

    public static void init() {
        speed = 1000;
        tiles = new int[MAX_TILE_X + 1][MAX_TILE_Y + 1];
        for (int i = 0; i < tiles.length; i++)
            Arrays.fill(tiles[i], Block.NO_TYPE);

        Tile.init();
        new Block(getRandomType());
    }

    public static void render(Graphics g) {
        int x, y;
        Image img;
        int tileType;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tileType = tiles[i][j];
                if (tileType == -1)
                    continue;

                if (isTileType(tileType))
                    tileType -= 7;

                img = Tile.tileImages[tileType];
                x = PlayState.boardX + (i * Tile.TILE_WIDTH);
                y = PlayState.boardY + (j * Tile.TILE_HEIGHT);
                g.drawImage(img, x, y);
            }
        }
    }

    public static void update(int delta) {
        timePassed += delta;
        if (downPressed) {
            if (timePassed > (speed / 3)) {
                moveDown();
                timePassed = 0;
            }
        } else {
            if (timePassed > speed) {
                moveDown();
                timePassed = 0;
            }
        }
    }

    public static void moveDown() {
        ArrayList<int[]> foundBlocks = findBlocks();
        int[] current;
        int nextType;
        for (int i = 0; i < foundBlocks.size(); i++) {
            current = foundBlocks.get(i);
            if (current[1] == MAX_TILE_Y) {
                convertToTiles(foundBlocks);
                new Block(getRandomType());
                return;
            }
            nextType = tiles[current[0]][current[1] + 1];
            if (nextType != current[2] && nextType != Block.NO_TYPE) {
                convertToTiles(foundBlocks);
                new Block(getRandomType());
                return;
            }
        }
        for (int i = foundBlocks.size(); i > 0; i--) {
            int[] blockInfo = foundBlocks.get(i - 1);
            int x = blockInfo[0];
            int y = blockInfo[1];
            tiles[x][y] = Block.NO_TYPE;
            tiles[x][y + 1] = blockInfo[2];
        }
    }

    public static void moveRight() {
        ArrayList<int[]> foundBlocks = findBlocks();
        int[] current;
        int nextType;
        for (int i = 0; i < foundBlocks.size(); i++) {
            current = foundBlocks.get(i);
            if (current[0] == MAX_TILE_X)
                return;
            nextType = tiles[current[0] + 1][current[1]];
            if (nextType != current[2] && nextType != Block.NO_TYPE)
                return;
        }
        for (int i = foundBlocks.size(); i > 0; i--) {
            int[] blockInfo = foundBlocks.get(i - 1);
            int x = blockInfo[0];
            int y = blockInfo[1];
            tiles[x][y] = Block.NO_TYPE;
            tiles[x + 1][y] = blockInfo[2];
        }
    }

    public static void moveLeft() {
        ArrayList<int[]> foundBlocks = findBlocks();
        int[] current;
        int nextType;
        for (int i = 0; i < foundBlocks.size(); i++) {
            current = foundBlocks.get(i);
            if (current[0] == MIN_TILE_X)
                return;
            nextType = tiles[current[0] - 1][current[1]];
            if (nextType != current[2] && nextType != Block.NO_TYPE)
                return;
        }
        for (int i = 0; i < foundBlocks.size(); i++) {
            int[] blockInfo = foundBlocks.get(i);
            int x = blockInfo[0];
            int y = blockInfo[1];
            tiles[x][y] = Block.NO_TYPE;
            tiles[x - 1][y] = blockInfo[2];
        }
    }

    public static void rotate() {
        // TODO: Rotate
    }

    private static void convertToTiles(ArrayList<int[]> blocks) {
        int[] current;
        for (int i = 0; i < blocks.size(); i++) {
            current = blocks.get(i);
            tiles[current[0]][current[1]] = current[2] + 7;
        }
    }

    private static ArrayList<int[]> findBlocks() {
        ArrayList<int[]> returnValue = new ArrayList<int[]>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (isBlockType(tiles[i][j])) {
                    int[] blockLocation = {i, j, tiles[i][j]};
                    returnValue.add(blockLocation);
                }
            }
        }
        return returnValue;
    }

    private static boolean isBlockType(int type) {
        switch (type) {
        case Block.BLOCK_I:
        case Block.BLOCK_J:
        case Block.BLOCK_L:
        case Block.BLOCK_O:
        case Block.BLOCK_S:
        case Block.BLOCK_T:
        case Block.BLOCK_Z:
            return true;
        }
        return false;
    }

    private static boolean isTileType(int type) {
        switch (type) {
        case Block.TILE_I:
        case Block.TILE_J:
        case Block.TILE_L:
        case Block.TILE_O:
        case Block.TILE_S:
        case Block.TILE_T:
        case Block.TILE_Z:
            return true;
        }
        return false;
    }

    private static int getRandomType() {
        // TODO: Make each have a certain percentage?
        return (int)(Math.random() * Block.BLOCK_SCHEMA.length);
    }
}
