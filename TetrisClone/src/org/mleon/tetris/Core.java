package org.mleon.tetris;

import static org.mleon.tetris.ConfigurationMgr.*;
import org.mleon.tetris.states.*;
import org.mleon.tetris.util.Log;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Core extends StateBasedGame {
    public final static int EXIT_UNKNOWN_ERROR      = -0x01;
    public final static int EXIT_NO_ERROR           = 0x00;
    public final static int EXIT_CLOSE_REQUESTED    = 0x01;
    public final static int EXIT_CONTAINER_FAIL     = 0x02;
    public final static int EXIT_DATA_NOT_FOUND     = 0x03;

    public Core() {
        super("TetrisClone");
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new MenuState());
        addState(new PlayState());
    }

    public static void prepClose(int status) {
        if (status == EXIT_NO_ERROR || status == EXIT_CLOSE_REQUESTED)
            Log.info("Closing without error");
        else
            Log.error("Closing with error status: " + status);
    }

    public static void close() {
        close(EXIT_NO_ERROR);
    }

    public static void close(int status) {
        prepClose(status);
        System.exit(status);
    }

    public static void main(String[] args) {
        ConfigurationMgr.init();
        int width = getIntConfig(CONFIG.WIDTH);
        int height = getIntConfig(CONFIG.HEIGHT);
        boolean fullscreen = getBoolConfig(CONFIG.FULLSCREEN);
        boolean vsync = getBoolConfig(CONFIG.VSYNC);
        boolean fpsCounter = getBoolConfig(CONFIG.FPS_COUNTER);
        try {
            AppGameContainer container = new AppGameContainer(new Core());
            container.setDisplayMode(width, height, fullscreen);
            container.setVSync(vsync);
            container.setShowFPS(fpsCounter);
            container.setIcon("data/icon.png");
            container.start();
        } catch (SlickException e) {
            Log.error(e);
            close(EXIT_CONTAINER_FAIL);
        }
    }
}
