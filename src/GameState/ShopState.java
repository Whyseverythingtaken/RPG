/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;


import Item.Armor;
import Item.Armor.ArmorType;
import Item.Item;
import Item.Potion;
import Item.Potion.PotionType;
import Item.Sword;
import Item.Sword.SwordType;
import Occupant.Player;
import Occupant.ShopKeeper;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import rpg.GamePanel;
import rpg.Keys;

/**
 *
 * @author wilson
 */
public class ShopState extends GameState{
    
    private ArrayList<Item> shopItems;

    private String[] selectList;
    private int select;
    private GameStateManager gsm;
    
    public ShopState(GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;
        init();
    }
    
    
    
    /**
     * Makes the 
     * 
     * @return 
     */
    public ArrayList<Item> itemsInShop() {
        ArrayList<Item> toPut = new ArrayList<>();
        for(SwordType s : SwordType.values()) {
            Sword a = new Sword(s);
            toPut.add(a);
        }
        for(ArmorType a: ArmorType.values()) {
            Armor c = new Armor(a);
            toPut.add(c);
        }
        for(PotionType p: PotionType.values()) {
            Potion b = new Potion(p);
            toPut.add(b);
        }
        
        return toPut;
    }
    
    /**
     * 
     * @param player
     * @param item 
     */
    public void buyItem(Player player, Item item) {
        player.buyItem(item);        
    }
    
     public void select(int select) {
        if (shopItems.size() < select) {
            this.select = 1;
        }
        if (select <= 0) {
            this.select = shopItems.size();
        }
        for (int i = 0; i < shopItems.size(); i++) {
            selectList[i] = "";
        }
        selectList[this.select - 1] += "> ";
    }

    public void convertToString() {
        for (int i = 0; i < shopItems.size(); i++) {
            selectList[i] += shopItems.get(i).getName();
        }
    }

    public void drawList(Graphics2D g) {
        int rowPos = 40;
        for (String s : selectList) {
            g.drawString(s, 5, rowPos);
            rowPos += 20;
        }
    }
    
    public void drawDetail(Graphics2D g){
        g.drawString("> Detail", 150, 45);
        String [] details = shopItems.get(select-1).detail();
        int rowPos = 70;
        for (String s : details) {
            g.drawString(s, 155, rowPos);
            rowPos += 20;
        }
    }

    @Override
    public void init() {
        shopItems = new ArrayList<>();
        shopItems = itemsInShop();
        select = 1;
        selectList = new String[shopItems.size()];
        select(select);
        convertToString();
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, 20);
        g.setColor(Color.BLUE);
        g.drawRect(0, 20, 145, GamePanel.HEIGHT2 - 60);
        g.setColor(Color.RED);
        g.drawRect(0, GamePanel.WIDTH-20, GamePanel.WIDTH, 40);
        
        g.setColor(Color.GREEN);
        g.drawRect(150,200, 145,75);
        
        g.setColor(Color.white);
        g.drawString("Player Gold : ", 155, 220);
        g.drawString(gsm.getPlayer().getGold() +" G", 155, 250);
        
        
        
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, GamePanel.WIDTH, 20);
        g.drawString("Welcome to shop", 5, 15);
        
        
        g.setColor(Color.white);
        drawList(g);
        drawDetail(g);
        g.drawString("Enter - buy", 20 , GamePanel.HEIGHT2-15);
        g.drawString("Escape - exit", GamePanel.WIDTH - 90 , GamePanel.HEIGHT2-15);
    }

    @Override
    public void handleInput() {
         if (Keys.isPressed(Keys.DOWN)) {
            this.select += 1;
            this.select(this.select);
            this.convertToString();
        }
        if (Keys.isPressed(Keys.UP)) {
            this.select -= 1;
            this.select(this.select);
            this.convertToString();
        }
        if (Keys.isPressed(Keys.ESCAPE)) {
            this.gsm.setInShop(false);
        }
        if (Keys.isPressed(Keys.ENTER)) {
            if(gsm.getCurrentState() == GameStateManager.PLAY){
                buyItem(gsm.getPlayer(),shopItems.get(select-1));  
            }
        }
    }
    
}
