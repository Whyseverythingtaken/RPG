/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import rpg.Keys;
import rpg.RPGDataBase;

/**
 *
 * @author wilson
 */
public class HighScore extends GameState {

    private RPGDataBase db;
    ArrayList<String[]> data;

    public HighScore(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    public void init() {
        db = gsm.getDb();
        db.connectDB();
        data = db.loadData();
//        String playerName = "playerJhon";
//        int score = 10;
//        db.connectDB();
//        db.createTable();
//        db.insertPlayerData(playerName, score);
    }

    public void drawScores(Graphics2D g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        int height = 50;
        g.drawString("Name", 10, height);
        g.drawString("Score", 200, height);
        height+=20;
        for (int i = 0; i < data.size(); i++) {
            String[] row = new String[data.get(i).length];
            row = data.get(i);
            g.drawString(row[0], 10, height);
            g.drawString(row[1], 200, height);
            height+=20;
        }

    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("HighScore", 10, 20);
        g.drawString("_________", 10, 25);
        drawScores(g);
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setState(GameStateManager.MENU);
        }
    }

}
