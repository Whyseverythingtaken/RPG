package GameState;

import Occupant.Player;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;
import rpg.RPGDataBase;

public class GameStateManager {

    public boolean paused;
    public boolean inShop;
    public boolean inProfile;
    public PauseState pauseState;
    public ShopState shopState;
    public ProfileState profileState;

    private GameState[] gameStates;
    private Player player;
    private int currentState;
    private int previousState;
    private String playerName;

    private RPGDataBase db;

    public static final int NUM_STATES = 5;
    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int HIGHSCORE = 2;
    public static final int INTRO = 3;
    public static final int STORY = 4;

    public GameStateManager() {
        db = new RPGDataBase();
        gameStates = new GameState[NUM_STATES];
        paused = false;
        inShop = false;
        inProfile = false;
        pauseState = new PauseState(this);
        profileState = new ProfileState(this);
        setState(INTRO);
    }

    public void setState(int i) {
        previousState = currentState;
        unloadState(previousState);
        currentState = i;
        if (i == INTRO) {
            gameStates[i] = new IntroState(this);
            gameStates[i].init();
        } else if (i == STORY) {
            this.playerName = askForName();

            if (playerName != null) {
                if (playerName.length() > 10) {
                    JOptionPane.showMessageDialog(null, "enter name less than 10 character");
                    setState(MENU);
                }
            } else {
                setState(MENU);
            }
            gameStates[i] = new StoryState(this);
            gameStates[i].init();
        } else if (i == MENU) {
            gameStates[i] = new MenuState(this);
            gameStates[i].init();
        } else if (i == PLAY) {
            PlayState ps = new PlayState(this);
            gameStates[i] = ps;
            gameStates[i].init();
            this.player = ps.getPlayer();
            this.player.setName(playerName);
            this.shopState = new ShopState(this);
        } else if (i == HIGHSCORE) {
            gameStates[i] = new HighScore(this);
            gameStates[i].init();
        }
    }

    private String askForName() {
        String playerName = JOptionPane.showInputDialog(
                "What is your name?", null);
        if (playerName == null || playerName.length() <= 0) {
            setState(MENU);
        }

        return playerName;
    }

    public void unloadState(int i) {
        gameStates[i] = null;
    }

    public void setInShop(boolean inShop) {
        this.inShop = inShop;
    }

    public void setInProfile(boolean inProfile) {
        this.inProfile = inProfile;
        if (inProfile) {
            profileState.init();
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getCurrentState() {
        return currentState;
    }

    public GameState getState() {
        return gameStates[currentState];
    }

    public void update() {
        if (inProfile) {
            profileState.update();
        } else if (inShop) {
            shopState.update();
        } else if (paused) {
            pauseState.update();
        } else if (gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void draw(Graphics2D g) {
        if (inProfile) {
            profileState.draw(g);
        } else if (inShop) {
            shopState.draw(g);
        } else if (paused) {
            pauseState.draw(g);
        } else if (gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public RPGDataBase getDb() {
        return db;
    }

}
