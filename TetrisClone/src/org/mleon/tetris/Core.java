package org.mleon.tetris;

import org.mleon.tetris.states.*;
import org.mleon.tetris.util.Log;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Core extends StateBasedGame {
    public static int EXIT_UNKNOWN_ERROR    = -0x01;
    public static int EXIT_NO_ERROR         = 0x00;
    public static int EXIT_CLOSE_REQUESTED  = 0x01;
    public static int EXIT_CONTAINER_FAIL   = 0x02;

    public Core() {
        super("TetrisClone");
    }

    public void initStatesList(GameContainer gc) throws SlickException {
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
        // TODO: Make the following options
        int width = 800;
        int height = 600;
        boolean fullscreen = false;
        boolean vsync = false;
        boolean fpsCounter = false;
        try {
            AppGameContainer container = new AppGameContainer(new Core());
            container.setDisplayMode(width, height, fullscreen);
            container.setVSync(vsync);
            container.setShowFPS(fpsCounter);
            container.start();
        } catch (SlickException e) {
            Log.error(e);
            close(EXIT_CONTAINER_FAIL);
        }
    }
}
