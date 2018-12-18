/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

/**
 *
 * @author USER
 */
public abstract class Item {
    protected int cost;
    protected String name;
    protected int rarity;
    public abstract String[] detail();
    
    public int getCost() {
        return cost;
    }
    public String getName() {
        return name;
    }
    public int getRarity() {
        return rarity;
    }
    
}
