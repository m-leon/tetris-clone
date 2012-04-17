package org.mleon.tetris.blocks;

public class Block {
    public static int MAX_DIMENSION; // Because blocks can be rotated, applies to both X and Y

    public static final int NO_TYPE = -1;
    public static final int BLOCK_I = 0;
    public static final int BLOCK_J = 1;
    public static final int BLOCK_L = 2;
    public static final int BLOCK_O = 3;
    public static final int BLOCK_S = 4;
    public static final int BLOCK_T = 5;
    public static final int BLOCK_Z = 6;
    public static final int TILE_I  = 7;
    public static final int TILE_J  = 8;
    public static final int TILE_L  = 9;
    public static final int TILE_O  = 10;
    public static final int TILE_S  = 11;
    public static final int TILE_T  = 12;
    public static final int TILE_Z  = 13;

    /* Schemas have to be rectangular */
    public static final int[][][] BLOCK_SCHEMA = {
        { // I
            {1, 1, 1, 1}
        },
        { // J
            {1, 0, 0},
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
            {1, 1, 0}
        },
        { // T
            {0, 1, 0},
            {1, 1, 1}
        },
        { // Z
            {1, 1, 0},
            {0, 1, 1}
        }
    };

    public static void init() {
        int maximum = -1;
        for (int i = 0; i < BLOCK_SCHEMA.length; i++) {
            if (BLOCK_SCHEMA[i].length > maximum)
                maximum = BLOCK_SCHEMA[i].length;
            for (int j = 0; j < BLOCK_SCHEMA[i].length; j++) {
                if (BLOCK_SCHEMA[i][j].length > maximum)
                    maximum = BLOCK_SCHEMA[i][j].length;
            }
        }
        MAX_DIMENSION = maximum;
    }

    public Block(int type) {
        int x = (TileMgr.MAX_TILE_X + 1 - BLOCK_SCHEMA[type][0].length) / 2;
        int y = 0;
        TileMgr.buildSchema(BLOCK_SCHEMA[type], x, y, type);
    }

    public static int[][] rotate(int[][] input) {
        final int x = input.length;
        final int y = input[0].length; // Requires rectangular input array
        int[][] returnValue = new int[y][x];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                returnValue[j][x - 1 - i] = input[i][j];

        return returnValue;
    }
}
