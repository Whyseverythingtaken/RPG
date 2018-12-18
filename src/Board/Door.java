/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Item.Key;
import java.awt.Color;

/**
 *
 * @author USER
 */
public class Door extends Tile{

    boolean isLock;
    public enum DoorType{
        Gold("Q", Key.Gold ),
        Silver("R", Key.Silver ),
        Bronze("J", Key.Bronze );
        
        String stringRep;
        Key keyNeeded;
        DoorType(String string, Key key) {
            stringRep = string;
            keyNeeded = key;
        }
        
    }
    private final DoorType type;
    public Door(DoorType type,Position pos, Color color) {
        super(pos, color);
        isLock = true;
        this.type = type;
        
    }
    public boolean isLock() {
        return isLock;
    }

    public void unlock(){
        isLock = false;
    }
    @Override
    public String getString() {
       return type.stringRep;
    }
    public Key getKeyNeeded() {
        return type.keyNeeded;
    }
    
    @Override
    public String toString() {
        return type.name() + " Door.";
    }
    
}
