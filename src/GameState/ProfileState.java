/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Item.Item;
import Occupant.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import rpg.GamePanel;
import rpg.Keys;
import rpg.RPGDataBase;

/**
 *
 * @author wilson
 */
public class ProfileState extends GameState {

    private String[] profileDetail;
    private String[] equipDetail;
    private int select;
    private int selectInven;
    private ProfileList[] pls;
    private String[] selectList;
    private ArrayList<Item> itemList;
    private String[] selectInvenList;
    private boolean profile;
    private boolean inventory;
    private boolean save;
    private boolean exit;
    private boolean isInInventory;
    private int pivot;

    public enum ProfileList {

        PROFILE("Profile"),
        INVENTORY("Inventory"),
        HIGHSCORE("HighScore"),
        EXIT("Exit");

        private String menuName;

        private ProfileList(String menuName) {
            this.menuName = menuName;
        }

        public String getMenuName() {
            return menuName;
        }
    }

    public void select(int select) {

        if (pls.length < select) {
            this.select = 1;
        }
        if (select <= 0) {
            this.select = pls.length;
        }
        for (int i = 0; i < pls.length; i++) {
            selectList[i] = "";
        }
        selectList[this.select - 1] += "> ";
        changeState();
    }

    public void changeState() {
        if (select == 1) {
            profile = true;
            inventory = false;
            save = false;
            exit = false;
        } else if (select == 2) {
            profile = false;
            inventory = true;
            save = false;
            exit = false;
        } else if (select == 3) {
            profile = false;
            inventory = false;
            save = true;
            exit = false;
        } else if (select == 4) {
            profile = false;
            inventory = false;
            save = false;
            exit = true;
        }

    }

    public void convertToString() {
        for (int i = 0; i < pls.length; i++) {
            selectList[i] += pls[i].getMenuName();
        }
    }

    public void selectInventory(int select) {
        if (!gsm.getPlayer().getInventory().isEmpty()) {
            selectInvenList = new String[gsm.getPlayer().getInventory().size()];
            if (itemList.size() < select) {
                this.selectInven = 1;
            }
            if (select <= 0) {
                this.selectInven = itemList.size();
            }
            for (int i = 0; i < itemList.size(); i++) {
                selectInvenList[i] = "";
            }
            selectInvenList[this.selectInven - 1] += "> ";
        }
    }

    public void convertToStringInvenTory() {
        if (!gsm.getPlayer().getInventory().isEmpty()) {
            for (int i = 0; i < itemList.size(); i++) {
                selectInvenList[i] += itemList.get(i).getName();
            }
        }
    }

    public ProfileState(GameStateManager gsm) {
        super(gsm);
    }

    public void drawProfileList(Graphics2D g) {
        int row = 40;
        g.drawString("Player Details", pivot, row);
        row += 10;
        g.drawString("_____________", pivot, row);
        row += 30;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        for (String s : profileDetail) {
            g.drawString(s, pivot, row);
            row += 20;
        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
    }

    public void drawEquipList(Graphics2D g) {
        int row = 200;
        g.drawString("Equipments", pivot, row);
        row += 10;
        g.drawString("_____________", pivot, row);
        row += 30;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        for (String s : equipDetail) {
            g.drawString(s, pivot, row);
            row += 20;
        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
    }

    public void drawInventoryList(Graphics2D g) {
        g.drawString("inventory", pivot, 40);
        g.drawString("_____________", pivot, 45);
        int rowPos = 65;
        for (String s : selectInvenList) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            g.drawString(s, pivot, rowPos);
            rowPos += 20;
            g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        }
    }

    public void drawSaveList(Graphics2D g) {
        g.drawString("SAVE", pivot, 40);

    }

    public void drawList(Graphics2D g) {
        int rowPos = 40;

        for (String s : selectList) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
            g.drawString(s, 5, rowPos);
            rowPos += 20;
        }

    }

    public void drawExit(Graphics2D g) {
        int rowPos = 40;

        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        g.drawString("EXIT", pivot, rowPos);

    }

    @Override
    public void init() {
        profileDetail = gsm.getPlayer().detail();
        equipDetail = gsm.getPlayer().equipement();
        pls = ProfileList.values();
        select = 1;
        selectList = new String[4];
        selectInvenList = new String[gsm.getPlayer().getInventory().size()];
        select(select);
        convertToString();
        selectInven = 1;
        itemList = gsm.getPlayer().getInventory();
        selectInventory(selectInven);
        convertToStringInvenTory();
        profile = true;
        inventory = false;
        save = false;
        exit = false;
        isInInventory = false;
        pivot = 120;
    }

    @Override
    public void update() {
        handleInput();
        profileDetail = gsm.getPlayer().detail();
        equipDetail = gsm.getPlayer().equipement();
        
        if (gsm.getPlayer().getInventory().isEmpty()) {
            isInInventory = false;
            selectInvenList = new String[gsm.getPlayer().getInventory().size()];
            selectInven = 1;
            itemList = gsm.getPlayer().getInventory();
            selectInventory(selectInven);
            convertToStringInvenTory();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, 20);
        g.fillRect(100, 0, 10, GamePanel.HEIGHT2);
        g.setColor(Color.BLACK);
        g.drawString("profile", 10, 15);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        drawList(g);
        if (profile) {
            drawProfileList(g);
            drawEquipList(g);
        } else if (inventory) {
            drawInventoryList(g);
        } else if (save) {
            drawSaveList(g);
        } else if (exit) {
            drawExit(g);
        }
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) {
            if (isInInventory) {
                isInInventory = false;
            } else {
                gsm.setInProfile(false);
            }
        }
        if (Keys.isPressed(Keys.DOWN)) {
            if (isInInventory) {
                this.selectInven += 1;
                this.selectInventory(this.selectInven);
                this.convertToStringInvenTory();

            } else if (!isInInventory) {
                this.select += 1;
                this.select(this.select);
                this.convertToString();
            }
        }
        if (Keys.isPressed(Keys.UP)) {
            if (isInInventory) {
                this.selectInven -= 1;
                this.selectInventory(this.selectInven);
                this.convertToStringInvenTory();
            } else if (!isInInventory) {
                this.select -= 1;
                this.select(this.select);
                this.convertToString();
            }
        }
        if (Keys.isPressed(Keys.ENTER)) {
            if (isInInventory) {
                Item item = gsm.getPlayer().getItem(selectInven - 1);
                gsm.getPlayer().useItem(item);
                if (selectInven > 1) {
                    selectInven--;
                }
                selectInvenList = new String[gsm.getPlayer().getInventory().size()];
                itemList = gsm.getPlayer().getInventory();
                selectInventory(selectInven);
                convertToStringInvenTory();
            } else if (this.select == 2 && !gsm.getPlayer().getInventory().isEmpty()) {
                isInInventory = true;
            } else if (this.select == 3) {
                RPGDataBase db = gsm.getDb();
                String playerName = gsm.getPlayer().getName();
                
                
                int score = 0;
                if(gsm.getState() instanceof PlayState){
                    PlayState ps = (PlayState)gsm.getState();
                    score = ps.getScore();
                }
                db.connectDB();
                db.createTable();
                db.insertPlayerData(playerName, score);
            } else if (this.select == 4) {
                System.exit(0);
            }

        }

//        if(Keys.isPressed(Keys.ENTER)) {
//            gsm.getPlayer().use
//        }
    }

}
