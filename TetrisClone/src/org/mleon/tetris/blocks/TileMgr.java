package org.mleon.tetris.blocks;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TileMgr {
    private static Block currentBlock;
    private static int boardX, boardY;
    private static ArrayList<Tile> tiles = new ArrayList<Tile>();

    public static void init(int x, int y) {
        Tile.init();
        Block.init(x, y);
        boardX = x;
        boardY = y;
        currentBlock = new Block(Tile.BLOCK_O);
    }

    public static void render(Graphics g) {
        if (currentBlock != null)
            currentBlock.render(g);

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
        currentBlock.update(delta);
        // TODO: Convert block into Tile[] once it lands
    }
}
