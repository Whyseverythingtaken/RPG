/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import java.awt.Color;
import Occupant.Occupant;

/**
 *
 * @author USER
 */
public abstract class Tile {
    
    protected Position pos;
    protected Color color;
    protected Occupant occupant;
    
    public Tile(Position pos, Color color) {
        this.pos = pos;
        this.color = color;
        occupant = null;
    }
    
    public Position getPosition() {
        return pos;
    }
    public Color getColor() {
        return color;
    }
    
    public Occupant getOccupant(){
        return occupant;
    }
    
    public void addOccupant(Occupant occupant) {
        this.occupant = occupant;
    }
    
    public void removeOccupant() {
        occupant = null;
    }
    
    public boolean hasOccupant() {
        return occupant != null;
    }
    
     
    public abstract String getString();
}
