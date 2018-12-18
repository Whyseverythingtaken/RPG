/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Occupant.Occupant;

/**
 *
 * @author USER
 */
public class Position {
    private int x;
    private int y;
    
    //private Occupant occupant;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        //occupant = null;
    }
    
    
    public int getRow() {
        return x;
    }
    
    public int getColumn() {
        return y;
    }
    
    
}
