package org.mleon.tetris.blocks;

import java.util.ArrayList;

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

    private static int getRandomType() {
        // TODO: Make each have a certain percentage?
        return (int)(Math.random() * Tile.BLOCK_SCHEMA.length);
    }
}
