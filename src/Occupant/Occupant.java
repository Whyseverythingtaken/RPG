/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Occupant;

import Board.*;
import Item.Item;
import java.util.ArrayList;
import java.util.Random;
import Item.Armor;
import Item.Potion;
import Item.Sword;
import javax.swing.ImageIcon;

/**
 *
 * @author USER
 */
public abstract class Occupant {
    
    protected Random random;
    protected Position pos;
    protected String name;
    //attributes
    protected boolean isDead;
    protected int health;
    protected int minDamage;
    protected int maxDamage;
    protected int exp;
    protected int damage;
    protected double accuracy;
    protected double evasion;
    protected int gold;
    protected int defense;
    protected ArrayList<Item> inventory;
    protected Direction direction;
    protected boolean isHit;
    
    protected int size;
    
    //Graphics
    protected ImageIcon anim;
    
    public Occupant(Position pos) {
        this.pos = pos;
        inventory = new ArrayList<>();
        size = 30;
        random = new Random();
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public int generateDamage() {
        
        return random.nextInt(maxDamage - minDamage) + minDamage;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public void setPosition(Position pos) {
        this.pos = pos;
    }
    public Position getPosition() {
        return this.pos;
    }

    public String getName() {
        return name;
    }

    public boolean isIsDead() {
        return isDead;
    }

    public int getHealth() {
        return health;
    }

    public void removeMinAndMax(int min, int max) {
        minDamage -= min;
        maxDamage -= max;
    }
    public void addMinAndMax(int min, int max) {
        minDamage += min;
        maxDamage += max;
    }
    public int getDamage() {
        return damage = generateDamage();
    }

    public int getExp() {
        return exp;
    }

    public void addGold(int gol) 
    {
        gold += gol;
    }
    public int getGold() {
        return gold;
    }

    public int getDefense() {
        return defense;
    }
    public void addDefense(int toAdd) {
        defense += toAdd;
    }
    public void removeDefense(int toRemove) {
        defense -= toRemove;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    
    public Item getItem(int index) {
        if(index > inventory.size() - 1 || index < 0) {
            System.out.println("No item in that slot");
            return null;
        }
        return inventory.get(index);
    }
    public abstract String getString();
    public void attack(Occupant occ){
        
    }
    
    public String printInventory() {
        String toPrint = "";
        int counter = 1;
        if(inventory.isEmpty()) {
            return "Empty! \n";
        }
        Item s = null;
        for (Item item : inventory) {
            if(item instanceof Sword) {
                s = (Sword)item;
            }
            else if(item instanceof Potion) {
                s = (Potion)item;
            }
            else {
                s = (Armor)item;
            }
            toPrint += counter + ". " + s.toString() + "\n";
            counter ++;
        }
        return toPrint;
    }
    
    

    public abstract int reduceHealth(int damage);
    
    public boolean isHit(Occupant occ) {
        double evaChance = (accuracy * occ.evasion) * 100;
        if(random.nextInt(100) <= evaChance) {
            System.out.println("Miss");
            return false;
        }
        else if(evaChance < 0) {
            return true;
        }
        
        return true;
    }
    
    
    public void update()  {
        
    }
}
