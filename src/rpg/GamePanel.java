/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg;

import GameState.GameStateManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author USER
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 300;
    public static final int HEIGHT2 = HEIGHT + 16;
    public static final int SCALE = 2;

    // game loop stuff
    private Thread thread;
    private boolean running;
    private final int FPS = 20;
    private final int TARGET_TIME = 1000 / FPS;

    // drawing stuff
    private BufferedImage image;
    private Graphics2D g;

    // game state manager
    private GameStateManager gsm;

    // constructor
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
        setFocusable(true);
        setBackground(Color.BLACK);
        requestFocus();
    }

    // ready to display
    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }

    // run new thread
    @Override
    public void run() {

        init();

        long start;
        long elapsed;
        long wait;

        // game loop
        while (running) {

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = TARGET_TIME - elapsed / 1000000;
            if (wait < 0) {
                wait = TARGET_TIME;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // initializes fields
    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT2, 1);
        g = (Graphics2D) image.getGraphics();
        gsm = new GameStateManager();
    }

    // updates game
    private void update() {
        gsm.update();
        Keys.update();
    }

    // draws game
    private void draw() {
        super.paint(g);
        gsm.draw(g);
    }

    // copy buffer to screen
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT2 * SCALE, null);
        g2.dispose();
    }

    // key event
    @Override
    public void keyTyped(KeyEvent key) {
    }

    @Override
    public void keyPressed(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), false);
    }

}
