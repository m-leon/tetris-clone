package org.mleon.tetris.blocks;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class Block {
    private int speed;
    private int timePassed;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Block(int type) {
        speed = 1000; // TODO: Calculate based on level, add modifiers if down is being pressed
        for (int i = 0; i < Tile.BLOCK_SCHEMA[type].length; i++)
            for (int j = 0; j < Tile.BLOCK_SCHEMA[type][i].length; j++)  {
                if (Tile.BLOCK_SCHEMA[type][i][j] == 1)
                    tiles.add(new Tile(type, j, i));
            }
    }

    public void render(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).render(g);
        }
    }

    public void update(int delta) {
        timePassed += delta;
        if (timePassed > speed) {
            timePassed = 0;
            moveDown();
        }
    }

    public void moveDown() {
        int[] highestBlock = TileMgr.getHighestTiles();
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getY() >= highestBlock[tiles.get(i).getX()]) {
                TileMgr.blockHit = true;
                return;
            }
        }

        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).moveDown();
    }

    public void moveLeft() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getX() <= TileMgr.MIN_TILE_X)
                return;

            if (TileMgr.isTileAt(tiles.get(i).getX() - 1, tiles.get(i).getY()))
                return;
        }

        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).moveLeft();
    }

    public void moveRight() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getX() >= TileMgr.MAX_TILE_X)
                return;

            if (TileMgr.isTileAt(tiles.get(i).getX() + 1, tiles.get(i).getY()))
                return;
        }

        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).moveRight();
    }

    public void rotate() {
        // TODO: Rotate.
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
