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

    public static boolean playing;
    public static int level;
    public static long points;
    public static int levelX, levelY;
    public static int pointsX, pointsY;
    public static int boardWidth, boardHeight, boardX, boardY;
    public static int infoWidth, infoHeight, infoX, infoY, infoOffset;

    private Image bgImage;

    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getGraphics().setBackground(Color.white);
        bgImage = new Image("./data/bg.png");
        bgImage = createBgImage(bgImage, gc.getWidth(), gc.getHeight());
        playing = true;
        level = 1;
        points = 0;
        boardWidth = 300; // TODO: Base on resolution
        boardHeight = 600;
        infoOffset = 5;
        infoWidth = 200;
        infoHeight = (gc.getGraphics().getFont().getLineHeight() * 2) + (infoOffset * 2);
        boardX = (gc.getWidth() - boardWidth) / 2;
        boardY = 0;
        infoX = boardX - infoWidth;
        infoY = (gc.getHeight() - infoHeight) / 2;
        levelX = infoX + infoOffset;
        levelY = infoY + infoOffset;
        pointsX = infoX + infoOffset;
        pointsY = infoY + infoOffset + gc.getGraphics().getFont().getLineHeight();
        TileMgr.init();
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImage, 0, 0);
        g.setColor(new Color(0f, 0f, 0f, 0.5f));
        g.fillRect(infoX, infoY, infoWidth, infoHeight);
        g.fillRect(boardX, boardY, boardWidth, boardHeight);
        g.setColor(Color.white);
        g.drawString("Level: " + Integer.toString(level), levelX, levelY);
        g.drawString("Points: " + Long.toString(points), pointsX, pointsY);
        TileMgr.render(g);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        TileMgr.update(delta);
        if (points > (level * 10000))
            level += 1;
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_UP && playing)
            TileMgr.rotate();
        else if (key == Input.KEY_RIGHT && playing)
            TileMgr.moveRight();
        else if (key == Input.KEY_DOWN && playing)
            TileMgr.downPressed = true;
        else if (key == Input.KEY_LEFT && playing)
            TileMgr.moveLeft();
    }

    public void keyReleased(int key, char c) {
        if (key == Input.KEY_DOWN)
            TileMgr.downPressed = false;
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
