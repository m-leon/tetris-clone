package org.mleon.tetris.blocks;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Graphics;

public class TileMgr {
    public static int MIN_TILE_X = 0;
    public static int MAX_TILE_X = 9;
    public static int MIN_TILE_Y = 0;
    public static int MAX_TILE_Y = 19;
    public static Block currentBlock;
    public static boolean blockHit = false;

    private static ArrayList<Tile> tiles = new ArrayList<Tile>();

    public static void init() {
        Tile.init();
        currentBlock = new Block(getRandomType());
    }

    public static void render(Graphics g) {
        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).render(g);

        if (currentBlock != null)
            currentBlock.render(g);
    }

    public static void update(int delta) {
        if (blockHit) {
            blockHit = false;
            ArrayList<Tile> newTiles = currentBlock.getTiles();
            for (int i = 0; i < newTiles.size(); i++)
                tiles.add(newTiles.get(i));

            currentBlock = new Block(getRandomType());
        }
        currentBlock.update(delta);
    }

    public static boolean isTileAt(int x, int y) {
        for (int i = 0; i < tiles.size(); i++)
            if (tiles.get(i).getX() == x)
                if (tiles.get(i).getY() == y)
                    return true;

        return false;
    }

    public static int[] getHighestTiles() {
        int[] returnValue = new int[10];
        Arrays.fill(returnValue, MAX_TILE_Y);
        Tile current;
        for (int i = 0; i < tiles.size(); i++) {
            current = tiles.get(i);
            if (current.getY() < returnValue[current.getX()])
                returnValue[current.getX()] = current.getY() - 1;
        }
        return returnValue;
    }

    public static int getHighestTile(int row) {
        int returnValue = MAX_TILE_Y;
        Tile current;
        for (int i = 0; i < tiles.size(); i++) {
            current = tiles.get(i);
            if (current.getY() < returnValue)
                returnValue = current.getY() - 1;
        }
        return returnValue;
    }

    private static int getRandomType() {
        // TODO: Make each have a certain percentage?
        return (int)(Math.random() * Tile.BLOCK_SCHEMA.length);
    }
}
