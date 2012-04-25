package org.mleon.tetris.states;

import org.mleon.tetris.blocks.TileMgr;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {
    public static final int ID = 2;

    public static boolean playing;
    public static int level;
    public static long points;
    public static int levelX, levelY;
    public static int pointsX, pointsY;
    public static int boardWidth, boardHeight, boardX, boardY;
    public static int infoWidth, infoHeight, infoX, infoY, infoOffset;

    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getGraphics().setBackground(Color.white);
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
        g.drawImage(MenuState.bgImage, 0, 0);
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
}
