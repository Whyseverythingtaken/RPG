/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import rpg.GamePanel;
import rpg.Keys;

/**
 *
 * @author wilson
 */
public class StoryState extends GameState {

    private BufferedImage logo;

    private int alpha;
    private int ticks;

    private final int FADE_IN = 60;
    private final int LENGTH = 30;
    private final int FADE_OUT = 60;

    public StoryState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {
        ticks = 0;
        try {
            logo = ImageIO.read(new File("Images/story.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        handleInput();
        ticks++;
        if (ticks < FADE_IN) {
            alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
            if (alpha < 0) {
                alpha = 0;
            }
        }
        if (ticks > FADE_IN + LENGTH) {
            alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
            if (alpha > 255) {
                alpha = 255;
            }
        }
        if (ticks > FADE_IN + LENGTH + FADE_OUT) {
            gsm.setState(GameStateManager.PLAY);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        g.drawImage(logo, 0, 0, null);
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            gsm.setState(GameStateManager.PLAY);
        }
    }

}
