/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Occupant;

import Item.Item;
import java.util.ArrayList;
import Board.Position;
import GameState.PlayState;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *
 * @author USER
 */
public class ShopKeeper extends Occupant {

    private ArrayList<Item> shopItems;
    private String toPrint;
    private PlayState ps;
    private ImageIcon anim;
   
    public ShopKeeper(Position pos) {
        super(pos);
        this.anim = new ImageIcon("images/shop.png");
    }
    
    @Override
    public String getString() {
        return "S";
    }

    @Override
    public int reduceHealth(int damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void attack(Occupant occ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void draw(Graphics2D g, int x , int y){
        g.drawImage(anim.getImage(),x, y,null);
    }
}
