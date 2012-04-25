package org.mleon.tetris.states;

import org.mleon.tetris.blocks.TileMgr;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class PlayState extends BasicGameState {
    public static final int ID = 2;

    public static int level = 1;
    public static long points = 0;
    public static boolean playing;
    public static int boardWidth, boardHeight, boardX, boardY;

    private long playCounter;
    private boolean playedOnce;
    private int levelX, levelY;
    private StateBasedGame game;
    private int pointsX, pointsY;
    private int infoWidth, infoHeight, infoX, infoY, infoOffset;

    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getGraphics().setBackground(Color.white);
        this.game = game;
        playedOnce = false;
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
        if (!playing && playedOnce) {
            String gameOver;
            if (playCounter > 0)
                gameOver = "Restarting in: " + playCounter;
            else
                gameOver = "Press any button to restart";
            g.setColor(new Color(0f, 0f, 0f, 0.5f));
            int gameOverWidth = 275;
            int gameOverHeight = 100;
            int gameOverX = (gc.getWidth() - gameOverWidth) / 2;
            int gameOverY = (gc.getHeight() - gameOverHeight) / 2;
            g.fillRect(gameOverX, gameOverY, gameOverWidth, gameOverHeight);
            g.setColor(Color.white);
            g.drawString(gameOver, (gameOverX + (gameOverWidth - g.getFont().getWidth(gameOver)) / 2), (gameOverY + (gameOverHeight - g.getFont().getLineHeight()) / 2));
        }
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (!playing) {
            if (playCounter > 0)
                playCounter -= delta;
            else
                playCounter = 0;
        }

        TileMgr.update(delta);
        if (points > (level * 10000))
            level += 1;
    }

    public void enter(GameContainer gc, StateBasedGame game) {
        playing = true;
        playedOnce = true;
        playCounter = 5000;
    }

    public void leave(GameContainer gc, StateBasedGame game) {
        level = 1;
        points = 0;
        playing = true; // So that the restart screen doesn't come up when we reenter
        TileMgr.reinit();
        playCounter = 5000;
    }

    public void keyPressed(int key, char c) {
        if (playCounter == 0)
            game.enterState(PlayState.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

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
