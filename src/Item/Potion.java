/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Item.Item;

/**
 *
 * @author USER
 */
public class Potion extends Item{
    private final int healPoints; 

    //Potion Types
    public enum PotionType{
        SMALL("Small Potion", 20, 10),
        MEDIUM("Medium Potion", 30, 15),
        LARGE("Large Potion", 40, 20);
        
        private String name;
        private int heal;
        private int cost;
        PotionType(String name, int heal, int cost) {
            this.heal = heal;
            this.cost = cost;
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name + " || Heal Points: " + heal + " || Cost: " + cost;
        }
    }
    
    //Constructor for Potion 
    public Potion(PotionType type) {
        healPoints = type.heal;
        cost = type.cost;
        name = type.name;
    }
    
    public int getHeal() {
        return healPoints;
    }
    
    public String[] detail(){
        String[] detail = new String [3];
        detail[0] = getName();
        detail[1] = "Heal : "+ getHeal();
        detail[2] = "Cost : "+ getCost();
        return detail;
    }
    
    @Override
    public String toString() {
        return name + " || Heal:  " + healPoints;
    }
    
    
}
