/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Occupant;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author wilson
 */
public class HealthBar {
    private final static int HEIGHT = 5;
    private int currentlength,maximumLength;
    private int currentHealth,maximumHealth;

    
    public HealthBar(int currentHealth,int maximumHealth){
        this.currentHealth = currentHealth;
        this.maximumHealth = maximumHealth;
        this.currentlength = this.maximumLength = 30;
    }
    
    public void calculateLength(){
        int percentage = (currentHealth/maximumHealth)*100;
        this.currentlength = (percentage*maximumLength)/100;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
        calculateLength();
    }

    public int getMaximumHealth() {
        return maximumHealth;
    }

    public void setMaximumHealth(int maximumHealth) {
        this.maximumHealth = maximumHealth;
        calculateLength();
    }
    
    public void drawHealth(Graphics2D g, int x , int y){
        g.setColor(Color.RED);
        g.fillRect(x, y, maximumLength, HEIGHT);
        g.setColor(Color.GREEN);
        g.fillRect(x, y, currentlength, HEIGHT);
    }
    
}
