package org.mleon.tetris.blocks;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Block {
    public static int boardX, boardY;

    private int timePassed;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public static void init(int x, int y) {
        boardX = x;
        boardY = y;
    }

    public Block(int type) {
        for (int i = 0; i < Tile.BLOCK_SCHEMA[type].length; i++)
            for (int j = 0; j < Tile.BLOCK_SCHEMA[type][i].length; j++)  {
                if (Tile.BLOCK_SCHEMA[type][i][j] == 1)
                    tiles.add(new Tile(type, j, i));
            }
    }

    public void render(Graphics g) {
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

    public void update(int delta) {
        timePassed += delta;
        if (timePassed > 1000) {
            timePassed = 0;
            moveDown();
        }
    }

    public void moveDown() {
        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).moveDown();
    }

    public void moveLeft() {
        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).moveLeft();
    }

    public void moveRight() {
        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).moveRight();
    }
}
