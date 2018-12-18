package GameState;

import Action.Attack;
import Board.Board;
import Board.Direction;
import Board.Door;
import Board.Position;
import Board.Tile;
import Item.Key;
import Occupant.Monster;
import Occupant.Monster.MonsterType;
import Occupant.Player;
import Occupant.ShopKeeper;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import rpg.GamePanel;
import rpg.Keys;
import rpg.RPGDataBase;

public class PlayState extends GameState {

    private long timer;
    private int score;
    private Player player;
    private ArrayList<Monster> monsters;
    private Board board;
    private ShopKeeper shopKeeper;
    private RPGDataBase db;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        timer = System.currentTimeMillis();
        score = 0;
        db = gsm.getDb();
    }

    @Override
    public void init() {

        board = new Board("finalmap.txt");
        player = new Player(new Position(4, 5));
        monsters = new ArrayList<>();
        shopKeeper = new ShopKeeper(new Position(5, 4));
        board.addOccupant(shopKeeper);
        board.addOccupant(player);

        generateGhostMonsters(20);
        generateCrabMonsters(20);
        generateSnailMonsters(20);
        generateWolfMonsters(15);
        generateBossMonsters();

        Monster drag = new Monster(MonsterType.DRAGON, new Position(30, 85));
        drag.addKey(Key.Silver);
        Monster wol = new Monster(MonsterType.WOLF, new Position(40, 45));
        wol.addKey(Key.Bronze);
        monsters.add(wol);
        monsters.add(drag);

        for (Monster mons : monsters) {
            board.addOccupant(mons);
        }
        board.setX((int) (GamePanel.WIDTH / 2 - player.getPosition().getColumn() * board.getTileSize()));
        board.setY((int) (GamePanel.HEIGHT / 2 - player.getPosition().getRow() * board.getTileSize()));
    }

    private void generateGhostMonsters(int num) {
        MonsterType x = MonsterType.DEMON;
        for (int i = 0; i < num; i++) {
            Monster a = new Monster(x, board.getRandomPosition(0, 21, 0, 47));
            monsters.add(a);
        }
    }

    private void generateCrabMonsters(int num) {
        MonsterType x = MonsterType.CRAB;
        for (int i = 0; i < num; i++) {
            Monster a = new Monster(x, board.getRandomPosition(22, 40, 0, 80));
            monsters.add(a);
        }
    }

    private void generateWolfMonsters(int num) {
        MonsterType x = MonsterType.WOLF;
        for (int i = 0; i < num; i++) {
            Monster a = new Monster(x, board.getRandomPosition(22, 40, 47, 80));
            monsters.add(a);
        }
    }

    private void generateSnailMonsters(int num) {
        MonsterType x = MonsterType.SNAIL;
        for (int i = 0; i < num; i++) {
            Monster a = new Monster(x, board.getRandomPosition(10, 40, 10, 42));
            monsters.add(a);
        }
    }

    private void generateBossMonsters() {
        Monster boss = new Monster(MonsterType.BOSS, new Position(5, 80));
        monsters.add(boss);
    }

    @Override
    public void update() {
        handleInput();
        player.update();
        for(Monster monster: monsters){
            monster.update();
        }
        this.getCurrentTime();
        if (player.isIsDead()) {

            String playerName = gsm.getPlayer().getName();
            int score = getScore();
            db.connectDB();
            db.createTable();
            db.insertPlayerData(playerName, score);
            JOptionPane.showMessageDialog(null, "Player killed");
            gsm.setState(0);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        board.draw(g);
        player.draw(g, board.getX() + player.getPosition().getColumn() * 30, board.getY() + player.getPosition().getRow() * 30);
        for (Monster monster : monsters) {
            if (!monster.isIsDead()) {
                monster.draw(g, board.getX() + monster.getPosition().getColumn() * 30,
                        board.getY() + monster.getPosition().getRow() * 30);
            }
        }
        for (Monster monster : monsters) {
            if (!monster.isIsDead()) {
                monster.drawHealth(g, board.getX() + monster.getPosition().getColumn() * 30,
                       ( board.getY() + monster.getPosition().getRow() * 30)-7);
            }
        }
        player.drawHealth(g, board.getX() + player.getPosition().getColumn() * board.getTileSize(),
                (board.getY() + player.getPosition().getRow() * board.getTileSize()) - 7);

        shopKeeper.draw(g, board.getX() + shopKeeper.getPosition().getColumn() * 30,
                board.getY() + shopKeeper.getPosition().getRow() * 30);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, 30);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString(player.getName(), 5, 15);
        g.drawString("HP", 65, 15);
        player.drawHealth(g, 80, 10);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString("EXP", 115, 15);
        player.drawEXP(g, 140, 10);
        g.setColor(Color.white);
        g.drawString(this.getPlayer().getCurrentLevel() + "", GamePanel.WIDTH - 120, 15);
        g.setColor(Color.yellow);
        g.drawString("Score : " + this.getScore(), GamePanel.WIDTH - 75, 10);
        g.drawString("Gold : " + this.getPlayer().getGold(), GamePanel.WIDTH - 75, 20);
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public int getScore() {
        return score;
    }

    public void move(Direction direction) {
        Position newPosition = board.validToMove(player.getPosition(), direction);
        if (newPosition != null) {
            Tile x = board.getSquare(player.getPosition());
            x.removeOccupant();
            player.setPosition(newPosition);
            Tile b = board.getSquare(player.getPosition());
            b.addOccupant(player);
        }
        player.setDirection(direction);
        board.setX((int) (GamePanel.WIDTH / 2 - player.getPosition().getColumn() * 30));
        board.setY((int) (GamePanel.HEIGHT / 2 - player.getPosition().getRow() * 30));
    }

    public void action() {
        Position faceDirection = board.calculateDestination(player.getPosition(), player.getDirection());
        Tile x = board.getSquare(faceDirection);

        if (x.hasOccupant() && x.getOccupant() instanceof Monster) {
            Monster monster = (Monster) x.getOccupant();

            Attack a = new Attack(player, monster);
            this.score += a.getDamage();
            player.setIsAttack(false);
            if (monster.isIsDead()) {
                player.addGold(monster.getGold());
                board.getSquare(monster.getPosition()).removeOccupant();
                System.out.println("You killed " + monster.getName());
                System.out.println("Gold acquired: " + monster.getGold());
                System.out.println("Exp Gained: " + monster.getExp());

                if (monster.hasKey()) {
                    player.addKey(monster.getKey());
                }
                if (monster.getMonsterType() == MonsterType.BOSS) {
                    System.out.println("Congratulations " + player.getName() + " you killed The Boss Monster");
                    System.out.println("Game Over");
                    String playerName = gsm.getPlayer().getName();
                    int score = getScore();
                    db.connectDB();
                    db.createTable();
                    db.insertPlayerData(playerName, score);
                    JOptionPane.showMessageDialog(null, "Player Won");
                    gsm.setState(0);
                }
            }
            player.setIsAttack(false);
        } else if (x.getOccupant() instanceof ShopKeeper) {
            //gsm.setState(3);
            gsm.setInShop(true);
        } else if (x instanceof Door) {
            Door a = (Door) x;
            Key b = a.getKeyNeeded();
            if (a.isLock()) {
                if (player.getPocket().contains(b)) {
                    player.removeKey(b);
                    System.out.println(b.toString() + " Key Used.");
                    System.out.println(a.toString() + " Unlocked.");
                    a.unlock();
                } else {
                    System.out.println("You need " + b.toString()
                            + " to unlock the " + a.toString());
                }
            } else {
                System.out.println("Door is already unlocked.");
            }
        }
    }

    public double getCurrentTime() {
        long now = System.currentTimeMillis();
        return (now - timer) / 1000.0;
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.DOWN)) {
            move(Direction.SOUTH);
        }
        if (Keys.isPressed(Keys.UP)) {
            move(Direction.NORTH);
        }
        if (Keys.isPressed(Keys.RIGHT)) {
            move(Direction.EAST);
        }
        if (Keys.isPressed(Keys.LEFT)) {
            move(Direction.WEST);
        }
        if (Keys.isPressed(Keys.SPACE)) {
            action();
        }
        if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(true);
        }
        if (Keys.isPressed(Keys.ENTER)) {
            gsm.setInProfile(true);
        }
    }

}
