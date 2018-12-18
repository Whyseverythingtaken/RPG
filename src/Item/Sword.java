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
public class Sword extends Item{
    
    private final int minDamage;
    private final int maxDamage;
    
    public enum SwordType {
        BROADSWORD("Broad Sword", 3, 6, 100),
        SHORTSWORD("Short Sword", 5, 7, 150),
        LONGSWORD("Long Sword", 7, 10, 200);
        private String name;
        private int minDamage;
        private int maxDamage;
        private int cost;
        
        SwordType(String name, int minDamage, int maxDamage, int cost) {
            this.name = name;
            this.minDamage = minDamage;
            this.maxDamage = maxDamage;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return name + "|| Min Damage: " + minDamage + " || Max Damage: " + maxDamage + " || Cost: " + cost;
        }
    }
    private final SwordType swordType;
    /**
     *
     * @param type
     */
    public Sword(SwordType type) {
        this.minDamage = type.minDamage;
        this.maxDamage = type.maxDamage;
        this.name = type.name;
        this.cost = type.cost;
        this.swordType = type;
    }
    
    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }
    
    public SwordType getSwordType() {
        return swordType;
    }
    
    public String[] detail(){
        String[] detail = new String [4];
        detail[0] = getName();
        detail[1] = "Min Damage : "+ getMinDamage();
        detail[2] = "Max Damage : "+ getMaxDamage();
        detail[3] = "Cost : "+ getCost();
        return detail;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return name + " || Damage: " + minDamage + " - " + maxDamage;
    }
    
    
    
}
