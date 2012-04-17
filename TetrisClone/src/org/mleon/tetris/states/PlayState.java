package org.mleon.tetris.states;

import org.mleon.tetris.Core;
import org.mleon.tetris.blocks.TileMgr;
import org.mleon.tetris.util.Log;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {
    public static final int ID = 1;

    public static int boardWidth, boardHeight, boardX, boardY;

    private Image bgImage;

    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getGraphics().setBackground(Color.white);
        bgImage = new Image("./data/bg.png");
        bgImage = createBgImage(bgImage, gc.getWidth(), gc.getHeight());
        boardWidth = 300; // TODO: Base on resolution
        boardHeight = 600;
        boardX = (gc.getWidth() - boardWidth) / 2;
        boardY = 0;
        TileMgr.init();
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImage, 0, 0);
        g.setColor(new Color(0f, 0f, 0f, 0.5f));
        g.fillRect(boardX, boardY, boardWidth, boardHeight);
        TileMgr.render(g);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        TileMgr.update(delta);
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_UP)
            TileMgr.currentBlock.rotate();
        if (key == Input.KEY_RIGHT)
            TileMgr.currentBlock.moveRight();
        if (key == Input.KEY_DOWN)
            TileMgr.currentBlock.moveDown(); // TODO: While it's being held down, speed it up, not like this
        if (key == Input.KEY_LEFT)
            TileMgr.currentBlock.moveLeft();
    }

    private Image createBgImage(Image img, int width, int height) {
        Image returnValue = null;
        try {
            returnValue = new Image(width, height);
            int widthCount = width / bgImage.getWidth();
            int heightCount = height / bgImage.getHeight();
            int imageWidth = bgImage.getWidth();
            int imageHeight = bgImage.getHeight();
            Graphics g = returnValue.getGraphics();
            for (int i = 0; i <= heightCount; i++)
                for (int j = 0; j <= widthCount; j++)
                    g.drawImage(bgImage, imageWidth * j, imageHeight * i);

            g.flush();
        } catch (SlickException e) {
            Log.error(e);
            Core.close(Core.EXIT_DATA_NOT_FOUND);
        }
        return returnValue;
    }
}
