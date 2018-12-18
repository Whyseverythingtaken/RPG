/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Occupant;

import Board.Position;
import GameState.PlayState;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class SavePoint extends Occupant{
    
    private Player player;
    private ArrayList<Monster> monster;
    
    public SavePoint(Position pos, PlayState ps) {
        super(pos);
    }
    
    public boolean saveGame() {
        
        return true;
    }

    @Override
    public String getString() {
        return "M";
    }


    @Override
    public int reduceHealth(int damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
