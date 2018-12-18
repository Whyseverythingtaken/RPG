/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import rpg.Keys;

/**
 *
 * @author wilson
 */
public class PauseState extends GameState {

    public PauseState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        handleInput();
    }

    
    public String[] instruction() {
        String[] instruction;
        String ins = "Instruction:\n"
                + "Arrow Keys - Directional Movements\n"
                + "Space - Player Interaction\n"
                + "Enter - Enter Player Profile\n";
        instruction = ins.split("\n");
        return instruction;
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.yellow);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("Pause", 20, 20);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        
        int space = 40;
        for(String s: instruction()) {
            g.drawString(s, 20 , space );
            space += 20;
        }
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(false);
        }
    }

}
