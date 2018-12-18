/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Occupant;

import java.util.ArrayList;
import Board.Position;
import Item.Key;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *
 * @author USER
 */
public class Monster extends Occupant {

    public enum MonsterType {

        //    name, rep, health, min, max, exp, hit, evasion, defense, gold

        CRAB("Crab", "C", 30, 2, 5, 20, 0.5, 0.2, 3, 10),
        SNAIL("Snail", "L", 25, 3, 7, 30, 0.6, 0.3, 0, 15),
        WOLF("Wolf", "Y", 45, 7, 9, 40, 0.7, 0.4, 1, 15),
        DEMON("Demon", "G", 15, 2, 5, 20, 0.5, 0.1, 0, 10),
        DRAGON("Dragon", "D", 50, 10, 15, 80, 0.7, 0.6, 3, 50),
        BOSS("Boss", "B", 80, 17, 21, 100, 0.7, 0.6, 4, 70);

        private String name;
        private final String stringRep;
        private int health;
        private int minDamage;
        private int maxDamage;
        private int exp;
        private double hitChance;
        private double evasion;
        private int defense;
        private int gold;

        private ArrayList<MonsterType> list;

        MonsterType(String name, String stringRep, int health, int min, int max,
                int exp, double hit, double evasion, int def, int gold) {
            this.name = name;
            this.stringRep = stringRep;
            this.health = health;
            this.minDamage = min;
            this.maxDamage = max;
            this.exp = exp;
            this.hitChance = hit;
            this.evasion = evasion;
            this.defense = def;
            this.gold = gold;

        }

    }
    private final MonsterType monsterType;
    private final String monsStringRep;
    private Key key;
    private ImageIcon image;
    private double maximumLength , currentlength ;
    private int height;
    private double percentage ;

    public Monster(MonsterType type, Position pos) {
        super(pos);
        monsterType = type;
        name = type.name;
        monsStringRep = type.stringRep;
        health = type.health;
        minDamage = type.minDamage;
        maxDamage = type.maxDamage;
        damage = generateDamage();
        exp = type.exp;
        accuracy = type.hitChance;
        evasion = type.evasion;
        defense = type.defense;
        gold = type.gold;
        inventory = new ArrayList<>();
        isDead = false;
        key = null;
        maximumLength = 30.00;
        currentlength = 0;
        height = 5;
        if (monsterType == MonsterType.DEMON) {
            image = new ImageIcon("Images/demon.gif");
        } else if (monsterType == MonsterType.CRAB) {
            image = new ImageIcon("Images/minion.gif");
        } else if (monsterType == MonsterType.WOLF) {
            image = new ImageIcon("Images/wolf.gif");
        } else if (monsterType == MonsterType.SNAIL) {
            image = new ImageIcon("Images/snail.gif");
        } else if (monsterType == MonsterType.DRAGON) {
            image = new ImageIcon("Images/dragon.png");
        } else {
            image = new ImageIcon("Images/ghost.gif");
        }
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public Key getKey() {
        Key c = key;
        key = null;
        System.out.println("You accuired " + c.toString() + " Key.");
        return c;
    }

    public boolean hasKey() {
        return key != null;
    }

    public void addKey(Key key) {
        this.key = key;
    }

    @Override
    public String getString() {
        return monsStringRep;
    }

    public void attack(Occupant occ) {
        if (!isDead) {
            System.out.println("Monster attacked: " + occ.reduceHealth(generateDamage()));
        }
    }

    @Override
    public String printInventory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int reduceHealth(int damage) {
        int at = damage - defense;
        if (at <= 0) {
            at = 1;
        }
        health -= at;
        if (health <= 0) {
            isDead = true;
        }
        return at;
    }

    @Override
    public String toString() {
        return monsterType.name;
    }
    
    public void update(){
        
        percentage = (this.getHealth() * 100) / this.monsterType.health;
        currentlength = (percentage * maximumLength) / 100;
    }

    public void drawHealth(Graphics2D g, int x, int y) {
        
        
        g.setColor(Color.BLACK);
        g.fillRect(x - 1, y - 1, (int) maximumLength + 2, height + 2);
        g.setColor(Color.RED);
        g.fillRect(x, y, (int) maximumLength, height);

        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int) currentlength, height);
    }

    public void draw(Graphics2D g, int x, int y) {
        //drawHealth(g, x, y -7);
        g.drawImage(image.getImage(), x, y, null);
    }
}
