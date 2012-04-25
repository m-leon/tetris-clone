package org.mleon.tetris.states;

import static org.mleon.tetris.ConfigurationMgr.getIntConfig;

import org.mleon.tetris.Core;
import org.mleon.tetris.ConfigurationMgr.CONFIG;
import org.mleon.tetris.util.Log;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MenuState extends BasicGameState implements ComponentListener {
    public static final int ID = 1;
    public static final String title = "Tetris";

    public static Image bgImage;

    private Image menuImage;
    private int titleX, titleY;
    private StateBasedGame game;
    private String[] menuOptions;
    private int[] textX = new int[2];
    private int[] textY = new int[2];
    private MouseOverArea[] areas = new MouseOverArea[2];

    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        int width = getIntConfig(CONFIG.WIDTH);
        int height = getIntConfig(CONFIG.HEIGHT);
        menuOptions = new String[] {
            "Play",
            "Exit"
        };
        Font font = gc.getGraphics().getFont();
        titleX = (width - font.getWidth(title)) / 2;
        titleY = 100;
        try {
            bgImage = new Image("data/bg.png");
            menuImage = new Image("data/menuButton.png");
        } catch (SlickException e) {
            Log.error(e);
            Core.close(Core.EXIT_DATA_NOT_FOUND);
        }
        bgImage = createBgImage(bgImage, width, height);
        int areasX = (gc.getWidth() - menuImage.getWidth()) / 2;
        int y = titleY + menuImage.getHeight();
        for (int i = 0; i < areas.length; i++) {
            y += (menuImage.getHeight() * 1.5);
            areas[i] = new MouseOverArea(gc, menuImage, areasX, y, menuImage.getWidth(), menuImage.getHeight(), this);
            areas[i].setNormalColor(new Color(1,1,1,0.6f));
            areas[i].setMouseOverColor(new Color(1,1,1,0.9f));
            textX[i] = areasX + ((menuImage.getWidth() - font.getWidth(menuOptions[i])) / 2);
            textY[i] = y + ((menuImage.getHeight() - font.getLineHeight()) / 2);
        }
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(bgImage, 0, 0);
        g.setColor(new Color(0f, 0f, 0f, 0.5f));
        g.fillRect(PlayState.boardX, PlayState.boardY, PlayState.boardWidth, PlayState.boardHeight);
        g.setColor(Color.white);
        g.drawString(title, titleX, titleY);
        for (int i = 0; i < areas.length; i++) {
            areas[i].render(gc, g);
            g.drawString(menuOptions[i], textX[i], textY[i]);
        }
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
    }

    public void componentActivated(AbstractComponent source) {
        if (source == areas[0])
            game.enterState(PlayState.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        else if (source == areas[1])
            Core.close(Core.EXIT_CLOSE_REQUESTED);
    }

    private static Image createBgImage(Image img, int width, int height) {
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
