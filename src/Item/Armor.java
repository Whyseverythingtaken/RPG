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
public class Armor extends Item{
    
    private final int defense;

    public enum ArmorType {
        COTTON("Cotton Armor", 2, 50),
        LEATHER("Leather Armor",3, 100),
        CHAIN("Chain Armor",4, 150);
        
        private String name;
        private int defense;
        private int cost;
        
        ArmorType(String name, int defense, int costs) {
            this.name = name;
            this.defense = defense;
            this.cost = costs;
        }

        @Override
        public String toString() {
            return name + " || Defense: " + defense + " || Cost: " + cost;
        }
        
    }
    
    public Armor(ArmorType type) {
            name = type.name;
            defense = type.defense;
            cost = type.cost;
    }

    public int getDefense() {
        return defense;
    }
    
    public String[] detail() {
        String[] detail = new String[3];
        detail[0] = getName();
        detail[1] = "Defense : " + getDefense();
        detail[2] = "Cost : " + getCost();
        return detail;
    }
    
    @Override
    public String toString() {
        return name + " || Defense: " + defense;
    }
    
}
