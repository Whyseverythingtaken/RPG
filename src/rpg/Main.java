/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("RPG");
        Dimension dimension = new Dimension(700, 700);
        window.getContentPane().setSize(dimension);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        

    }
}
