package GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import rpg.GamePanel;
import rpg.Keys;

public class MenuState extends GameState {

    private MenuList[] mls;
    private String[] selectList;
    private int select;
    private ImageIcon anim;
    private ImageIcon logo;

    public enum MenuList {

        STARTGAME("Start Game"),
        HIGHSCORE("High Scores"),
        EXIT("Exit");

        private String menuName;

        private MenuList(String menuName) {
            this.menuName = menuName;
        }

        public String getMenuName() {
            return menuName;
        }
    }

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    public void select(int select) {

        if (mls.length < select) {
            this.select = 1;
        }
        if (select <= 0) {
            this.select = mls.length;
        }
        //g.fillRect(0, 20 + (select * 10), GamePanel.WIDTH, 10);
        for (int i = 0; i < mls.length; i++) {
            selectList[i] = "";
        }
        selectList[this.select - 1] += "> ";
    }

    public void convertToString() {
//        for (int i = 0; i < mls.length; i++) {
//            selectList[i] = "";
//        }
        for (int i = 0; i < mls.length; i++) {
            selectList[i] += mls[i].getMenuName();
        }
    }

    public void drawList(Graphics2D g) {
        int rowPos = 80;
        g.drawString("__________________", 60, rowPos);
        rowPos += 30;
        for (String s : selectList) {
            g.drawString(s, 90, rowPos);
            rowPos += 20;
        }

    }

    @Override
    public void init() {
        anim = new ImageIcon("Images/bg.jpg");
        logo = new ImageIcon("Images/logo.png");
        mls = MenuList.values();
        select = 1;
        selectList = new String[3];
        select(select);
        convertToString();
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(anim.getImage(), 0, 0, null);
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
//        g.setColor(Color.YELLOW);
        //select(select , g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawImage(logo.getImage(), 65, 50, null);
        drawList(g);
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
        if (Keys.isPressed(Keys.ENTER)) {
            if (select == 1) {
                gsm.setState(GameStateManager.STORY);
            }
            if (select == 2) {
                gsm.setState(GameStateManager.HIGHSCORE);
            }
            if (select == 3) {
                System.exit(0);
            }
        }
    }

}
