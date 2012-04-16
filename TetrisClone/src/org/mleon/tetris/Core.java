package org.mleon.tetris;

import org.mleon.tetris.states.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.minlog.Log;

public class Core extends StateBasedGame {
    public Core() {
        super("TetrisClone");
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new PlayState());
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
            Log.error("Unable to start application", e);
        }
    }
}
