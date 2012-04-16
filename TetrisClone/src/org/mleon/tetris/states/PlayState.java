package org.mleon.tetris.states;

import org.mleon.tetris.util.Log;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {
    public static final int ID = 1;

    private Image bgImage;
    private Image[] tileImages;
    private Image[] blockImages;
    private int boardWidth, boardHeight, boardX, boardY;

    private final int TILE_WIDTH = 30;
    private final int TILE_HEIGHT = 30;
    private final int MAX_BLOCK_WIDTH = TILE_WIDTH * 4;
    private final int MAX_BLOCK_HEIGHT = TILE_HEIGHT * 2;

    private final int BLOCK_I = 0;
    private final int BLOCK_J = 1;
    private final int BLOCK_L = 2;
    private final int BLOCK_O = 3;
    private final int BLOCK_S = 4;
    private final int BLOCK_T = 5;
    private final int BLOCK_Z = 6;

    private final int[] AVAILABLE_BLOCKS = {
        BLOCK_I,
        BLOCK_J,
        BLOCK_L,
        BLOCK_O,
        BLOCK_S,
        BLOCK_T,
        BLOCK_Z
    };

    private final int[][][] BLOCK_SCHEMA = {
        { // I
            {1, 1, 1, 1},
            {0, 0, 0, 0}
        },
        { // J
            {1, 0, 0, 0},
            {1, 1, 1, 0}
        },
        { // L
            {0, 0, 1, 0},
            {1, 1, 1, 0}
        },
        { // O
            {1, 1, 0, 0},
            {1, 1, 0, 0}
        },
        { // S
            {0, 1, 1, 0},
            {1, 1, 0, 0}
        },
        { // T
            {0, 1, 0, 0},
            {1, 1, 1, 0}
        },
        { // Z
            {1, 1, 0, 0},
            {0, 1, 1, 0}
        }
    };

    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getGraphics().setBackground(Color.white);
        bgImage = new Image("./data/bg.png");
        bgImage = createBgImage(bgImage, gc.getWidth(), gc.getHeight());
        tileImages = createTileImages();
        blockImages = createBlockImages(tileImages);
        boardWidth = 300; // TODO: Base on resolution
        boardHeight = 600;
        boardX = (gc.getWidth() - boardWidth) / 2;
        boardY = 0;
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImage, 0, 0);
        g.setColor(new Color(0f, 0f, 0f, 0.5f));
        g.fillRect(boardX, boardY, boardWidth, boardHeight);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
    }

    private Image createBgImage(Image img, int width, int height) {
        try {
            Image returnValue = new Image(width, height);
            int widthCount = width / bgImage.getWidth();
            int heightCount = height / bgImage.getHeight();
            int imageWidth = bgImage.getWidth();
            int imageHeight = bgImage.getHeight();
            Graphics g = returnValue.getGraphics();
            for (int i = 0; i <= heightCount; i++)
                for (int j = 0; j <= widthCount; j++)
                    g.drawImage(bgImage, imageWidth * j, imageHeight * i);

            g.flush();
            return returnValue;
        } catch (SlickException e) {
            Log.error(e);
            // Close
            return null;
        }
    }

    private Image[] createTileImages() {
        Image[] returnValue = new Image[AVAILABLE_BLOCKS.length];
        for (int i = 0; i < AVAILABLE_BLOCKS.length; i++) {
            try {
                returnValue[i] = new Image("./data/tile-" + i + ".png");
            } catch (SlickException e) {
                // TODO: Close
                Log.error(e);
            }
        }
        return returnValue;
    }

    private Image[] createBlockImages(Image[] tileImages) {
        Image[] returnValue = new Image[AVAILABLE_BLOCKS.length];
        Graphics g;
        for (int i = 0; i < AVAILABLE_BLOCKS.length; i++) {
            try {
                returnValue[i] = new Image(MAX_BLOCK_WIDTH, MAX_BLOCK_HEIGHT);
                g = returnValue[i].getGraphics();
                // TODO: Draw the images based off the schema
                g.flush();
            } catch (SlickException e) {
                // TODO: Close
                Log.error(e);
            }
        }
        return returnValue;
    }
}
