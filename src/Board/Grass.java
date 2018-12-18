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
public class Grass extends Tile {

    public Grass(Position pos, Color color) {
        super(pos, color);
    }

    @Override
    public String getString() {
        return ".";
    }
    
    
}

