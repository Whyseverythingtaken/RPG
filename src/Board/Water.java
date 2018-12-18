/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import java.awt.Color;

/**
 *
 * @author USER
 */
public class Water extends Tile{
    
    public Water(Position pos, Color colour) 
    {
        super(pos, colour);
    }
    
    @Override
    public String getString() {
        return "W";
    }

    
}