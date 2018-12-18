/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Occupant;

import Board.Direction;
import Item.Item;
import Board.Position;
import Item.Armor;
import Item.Key;
import Item.Potion;
import Item.Sword;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Player extends Occupant {

    private enum Level {

        L1(1, 0, 50, 2, 6, 1),
        L2(2, 100, 60, 1, 1, 1),
        L3(3, 300, 70, 2, 2, 1),
        L4(4, 700, 80, 3, 3, 1),
        L5(5, 1000, 90, 4, 4, 1),
        L6(6, 1200, 100, 5, 5, 1),
        L7(7, 1400, 105, 6, 6, 1),
        L8(8, 1600, 110, 8, 8, 1);

        private final int lvl;
        private final int reqExp;
        private final int health;
        private final int min;
        private final int max;
        private final int def;

        Level(int lvl, int reqExp, int health, int min, int max, int def) {
            this.lvl = lvl;
            this.reqExp = reqExp;
            this.health = health;
            this.min = min;
            this.max = max;
            this.def = def;
        }

        public Level getNext() {
            int b = this.lvl + 1;
            String a = "L" + b;
            Level x = Level.valueOf(a);
            return x;
        }

        @Override
        public String toString() {
            return "Level: " + lvl;
        }
    }

    private Sword swordEquipped;
    private Armor armorEquipped;
    private boolean isAttack;
    private int prevDamage;
    private Level level;
    private ArrayList<Key> keyPocket;

    public Player(Position pos) {
        super(pos);
        swordEquipped = null;
        armorEquipped = null;
        exp = 0;
        init();
        update();
    }

    private void init() {
        keyPocket = new ArrayList<>();
        swordEquipped = null;
        armorEquipped = null;
        accuracy = 0.9;
        evasion = 0.2;
        exp = 0;
        level = Level.L1;
        health = level.health;
        defense = level.def;
        minDamage = level.min;
        maxDamage = level.max;
        direction = Direction.WEST;
        gold = 50;
        prevDamage = 0;
    }

    @Override
    public void update() {
        if (direction == direction.SOUTH) {
            anim = new ImageIcon("Images/South.gif");
        } else if (direction == direction.NORTH) {
            anim = new ImageIcon("Images/North.gif");
        } else if (direction == direction.EAST) {
            anim = new ImageIcon("Images/East.gif");
        } else if (direction == direction.WEST) {
            anim = new ImageIcon("Images/West.gif");
        }
    }

    @Override
    public String getString() {
        return "P";
    }

    public void setPrevDamage(int prevDamage) {
        this.prevDamage = prevDamage;
    }

    public boolean getIsAttack() {
        return isAttack;
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
//            clear();
//            stars();
//            System.out.println("YOU DIED!");
//            System.out.println("GAME OVER");
//            System.exit(0);
        }
        return at;
    }

    public void addKey(Key ky) {
        keyPocket.add(ky);
    }

    public ArrayList<Key> getPocket() {
        return keyPocket;
    }

    public void removeKey(Key ky) {
        keyPocket.remove(ky);
    }

    public String printKeys() {
        String pocket = "";
        if (keyPocket.isEmpty()) {

            return "Key pocket is empty.";
        }
        for (Key key : keyPocket) {
            pocket += key.toString() + " Key\n";
        }
        return pocket;
    }

    public void buyItem(Item item) {
        if (item.getCost() > gold) {
            System.out.println("Not Enough Gold");
        } else {
            gold -= item.getCost();
            inventory.add(item);
            JOptionPane.showMessageDialog(null,"You have bought "+item.getName());
            System.out.println("Item purchased: " + item.toString());
        }
        stars();
    }

    public void useItem(Item item) {
        if (inventory.isEmpty() || item == null) {
        } else if (item instanceof Sword) {
            if (swordEquipped != null) {
                System.out.println(swordEquipped.getName() + " removed.");
                unequipItem(swordEquipped);
            }
            swordEquipped = (Sword) item;
            addMinAndMax(swordEquipped.getMinDamage(), swordEquipped.getMaxDamage());
            System.out.println(swordEquipped.getName() + " equipped.");
        } else if (item instanceof Armor) {
            if (armorEquipped != null) {
                System.out.println(armorEquipped.getName() + " removed.");
                unequipItem(armorEquipped);
            }
            armorEquipped = (Armor) item;
            addDefense(armorEquipped.getDefense());
            System.out.println(armorEquipped.getName() + " equipped.");
        } else {
            Potion pots = (Potion) item;
            health += pots.getHeal();
            if (health > level.health) {
                health = level.health;
            }
            System.out.println(pots.getName() + " used. Heal: " + pots.getHeal());
            System.out.println("Health: " + health);
        }
        stars();
        inventory.remove(item);
    }

    public String printEquipedItem() {
        String toPrint = "";
        if (swordEquipped == null) {
            toPrint += "No Weapon Equipped\n";
        } else {
            toPrint += swordEquipped.toString() + "\n";
        }
        if (armorEquipped == null) {
            toPrint += "No Armor Equipped.\n";
        } else {
            toPrint += armorEquipped.toString() + "\n";
        }
        System.out.println("Items Equipped.");
        System.out.println(toPrint);
        stars();
        return toPrint;
    }

    public void printStats() {
        String s = ("Player stats") + "\n"
                //+ ("- Position: " + direction) + "\n"
                + ("- " + level) + "\n"
                + ("- Damage: " + minDamage + " - " + maxDamage) + "\n"
                + ("- Defense:  " + defense) + "\n"
                + ("- EXP : " + exp) + "\n"
                + ("- Health : " + health) + "\n"
                + ("- Gold : " + gold) + "\n";
        System.out.println(s);
        stars();
    }

    public int getNextLevelExp() {
        return level.getNext().reqExp;
    }

    public Level getNextLevel() {
        return level.getNext();
    }

    public Level getCurrentLevel() {
        return level;
    }

    public void addExp(int exp) {
        this.exp += exp;
        if (level.getNext().reqExp <= this.exp) {
            levelUp();
        }
    }

    public void fullHeal() {
        health = level.health;
    }

    private void levelUp() {
        level = getNextLevel();
        health = level.health;
        defense += level.def;
        minDamage += level.min;
        maxDamage += level.max;
        clear();
        System.out.println("Congrats you Leveled up");
        System.out.println("Level Increased to " + level.toString());
        System.out.println("Health Increased to: " + health);
        System.out.println("Damage Increased!");
        System.out.println("Defense Increased!");
    }

    public void unequipItem(Item item) {
        if (item instanceof Sword && swordEquipped.equals(item)) {
            Sword i = (Sword) item;
            removeMinAndMax(i.getMinDamage(), i.getMaxDamage());
            swordEquipped = null;
        } else if (item instanceof Armor && armorEquipped.equals(item)) {
            Armor i = (Armor) item;
            removeDefense(i.getDefense());
            armorEquipped = null;
        } else {
            System.out.println("No Item Equipped.");
        }
        inventory.add(item);
    }

    public String[] detail() {

        String[] details = new String[]{
            ("Name : " + this.getName()),
            ("Health : " + getHealth() + "/" + level.health),
            ("Gold : " + getGold()),
            ("" + this.level)};
        return details;
    }

    public String[] equipement() {
        String sword = "";
        String armor = "";
        if (swordEquipped == null) {
            sword = "none";
        } else {
            sword = swordEquipped.getName();
        }
        if (armorEquipped == null) {
            armor = "none";
        } else {
            armor = armorEquipped.getName();
        }
        String[] equipments = new String[]{
            ("Sword : " + sword),
            ("Armor : " + armor)
        };

        return equipments;
    }

    public void stars() {
        System.out.println("**************************************************************");
    }

    public void clear() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    public void drawDamage(Graphics2D g, int x, int y) {
        g.drawString("" + generateDamage(), x, y);
    }

    public void setIsAttack(boolean isAttack) {
        this.isAttack = isAttack;
    }

    public void drawHealth(Graphics2D g, int x, int y) {
        double maximumLength = 30.00, currentlength = 30.00;
        int height = 5;
        double percentage = (this.getHealth() * 100) / this.level.health;
        currentlength = (percentage * maximumLength) / 100;
        g.setColor(Color.BLACK);
        g.fillRect(x - 1, y - 1, (int) maximumLength + 2, height + 2);
        g.setColor(Color.RED);
        g.fillRect(x, y, (int) maximumLength, height);

        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int) currentlength, height);
    }

    public void drawEXP(Graphics2D g, int x, int y) {
        double maximumLength = 30.00, currentlength = 30.00;
        int height = 5;
        double percentage = (this.getExp() * 100) / this.getNextLevelExp();
        currentlength = (percentage * maximumLength) / 100;

        g.setColor(Color.black);
        g.fillRect(x - 1, y - 1, (int) maximumLength + 2, height + 2);
        g.setColor(Color.GREEN);
        g.drawRect(x, y, (int) maximumLength, height);

        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int) currentlength, height);
    }

    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(anim.getImage(), x, y, null);
        if (isAttack) {
            g.setColor(Color.RED);
            drawDamage(g, x - 20, y - 20);
            g.setColor(Color.BLACK);
        }
    }
}
