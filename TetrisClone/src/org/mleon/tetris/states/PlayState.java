package org.mleon.tetris.states;

import org.mleon.tetris.blocks.Tile;
import org.mleon.tetris.blocks.TileMgr;
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
    private int boardWidth, boardHeight, boardX, boardY;

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
        TileMgr.init(boardX, boardY, boardWidth, boardHeight);
        Tile.init();
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
}
