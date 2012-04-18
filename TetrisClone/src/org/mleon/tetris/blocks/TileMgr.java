package org.mleon.tetris.blocks;

import java.util.ArrayList;
import java.util.Arrays;

import org.mleon.tetris.states.PlayState;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TileMgr {
    public static int[][] tiles;
    public static int MIN_TILE_X = 0;
    public static int MAX_TILE_X = 9;
    public static int MIN_TILE_Y = 0;
    public static int MAX_TILE_Y = 19;
    public static boolean downPressed;

    private static int baseSpeed;
    private static int timePassed;
    private static int blockRotation; // 0 - 3

    public static void init() {
        baseSpeed = 1000;
        timePassed = 0;
        blockRotation = 0;
        tiles = new int[MAX_TILE_X + 1][MAX_TILE_Y + 1];
        for (int i = 0; i < tiles.length; i++)
            Arrays.fill(tiles[i], Block.NO_TYPE);

        Tile.init();
        Block.init();
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
            if (timePassed > (baseSpeed / (6 * PlayState.level))) {
                if (PlayState.playing) {
                    PlayState.points += 10;
                    moveDown();
                }
                timePassed = 0;
            }
        } else {
            if (timePassed > (baseSpeed / PlayState.level)) {
                moveDown();
                timePassed = 0;
            }
        }
    }

    public static void moveDown() {
        int[][] foundBlocks = findBlocks();
        int nextType;
        for (int i = 0; i < foundBlocks.length; i++) {
            if (foundBlocks[i][1] == MAX_TILE_Y) {
                convertToTiles(foundBlocks);
                if (PlayState.playing)
                    new Block(getRandomType());

                return;
            }
            nextType = tiles[foundBlocks[i][0]][foundBlocks[i][1] + 1];
            if (nextType != foundBlocks[i][2] && nextType != Block.NO_TYPE) {
                convertToTiles(foundBlocks);
                if (PlayState.playing)
                    new Block(getRandomType());

                return;
            }
        }
        for (int i = foundBlocks.length - 1; i >= 0; i--) {
            int x = foundBlocks[i][0];
            int y = foundBlocks[i][1];
            tiles[x][y] = Block.NO_TYPE;
            tiles[x][y + 1] = foundBlocks[i][2];
        }
    }

    public static void moveRight() {
        int[][] foundBlocks = findBlocks();
        int nextType;
        for (int i = 0; i < foundBlocks.length; i++) {
            if (foundBlocks[i][0] == MAX_TILE_X)
                return;
            nextType = tiles[foundBlocks[i][0] + 1][foundBlocks[i][1]];
            if (nextType != foundBlocks[i][2] && nextType != Block.NO_TYPE)
                return;
        }
        for (int i = foundBlocks.length - 1; i >= 0; i--) {
            int x = foundBlocks[i][0];
            int y = foundBlocks[i][1];
            tiles[x][y] = Block.NO_TYPE;
            tiles[x + 1][y] = foundBlocks[i][2];
        }
    }

    public static void moveLeft() {
        int[][] foundBlocks = findBlocks();
        int nextType;
        for (int i = 0; i < foundBlocks.length; i++) {
            if (foundBlocks[i][0] == MIN_TILE_X)
                return;
            nextType = tiles[foundBlocks[i][0] - 1][foundBlocks[i][1]];
            if (nextType != foundBlocks[i][2] && nextType != Block.NO_TYPE)
                return;
        }
        for (int i = 0; i < foundBlocks.length; i++) {
            int x = foundBlocks[i][0];
            int y = foundBlocks[i][1];
            tiles[x][y] = Block.NO_TYPE;
            tiles[x - 1][y] = foundBlocks[i][2];
        }
    }

    public static void rotate() {
        int[][] foundBlocks = findBlocks();
        int currentType = foundBlocks[0][2];
        int[][] currentSchema = Block.BLOCK_SCHEMA[currentType];
        for (int i = 0; i < blockRotation; i++)
            currentSchema = Block.rotate(currentSchema);

        int[][] newSchema = Block.rotate(currentSchema);
        int xBase = MAX_TILE_X, yBase = MIN_TILE_Y; // Lower left hand corner of the found blocks
        for (int i = 0; i < foundBlocks.length; i++) {
            if (foundBlocks[i][0] < xBase)
                xBase = foundBlocks[i][0];
            if (foundBlocks[i][1] > yBase)
                yBase = foundBlocks[i][1];
        }
        yBase -= currentSchema.length / 2;
        if (ensurePlacement(newSchema, xBase, yBase)) {
            removeBlocksAndTiles(foundBlocks);
            buildSchema(newSchema, xBase, yBase, currentType);
            blockRotation += 1;
            if (blockRotation > 3 || blockRotation < 0)
                blockRotation = 0;
        }
    }

    public static void endGame() {
        PlayState.playing = false;
        // TODO: Allow restart
    }

    private static boolean ensurePlacement(int[][] schema, int x, int y) {
        for (int i = 0; i < schema.length; i++)
            for (int j = 0; j < schema[i].length; j++)
                if (!isWithinBounds(x + j, y + i))
                    return false;

        return true;
    }

    private static boolean isWithinBounds(int x, int y) {
        if (x < MIN_TILE_X)
            return false;
        else if (x > MAX_TILE_X)
            return false;
        else if (y < MIN_TILE_Y)
            return false;
        else if (y > MAX_TILE_Y)
            return false;

        return true;
    }

    public static void buildSchema(int[][] schema, int x, int y, int type) {
        for (int i = 0; i < schema.length; i++) {
            for (int j = 0; j < schema[i].length; j++) {
                if (schema[i][j] == 1) {
                    if (tiles[x + j][y + i] != Block.NO_TYPE)
                        endGame();

                    tiles[x + j][y + i] = type;
                }
            }
        }
    }

    private static void removeBlocksAndTiles(int[][] input) {
        for (int i = 0; i < input.length; i++)
            tiles[input[i][0]][input[i][1]] = Block.NO_TYPE;
    }

    private static void convertToTiles(int[][] blocks) {
        for (int i = 0; i < blocks.length; i++)
            tiles[blocks[i][0]][blocks[i][1]] = blocks[i][2] + 7;
    }

    private static int[][] findBlocks() {
        // TODO: Rewrite without arraylist, preferably.
        ArrayList<int[]> foundBlocks = new ArrayList<int[]>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (isBlockType(tiles[i][j])) {
                    int[] blockLocation = {i, j, tiles[i][j]};
                    foundBlocks.add(blockLocation);
                }
            }
        }
        int[][] returnValue = new int[foundBlocks.size()][3]; // 0 - X, 1 - Y, 2 - Type
        for (int i = 0; i < foundBlocks.size(); i++)
            returnValue[i] = foundBlocks.get(i);

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
