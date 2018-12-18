/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import Occupant.Monster;
import Occupant.Occupant;
import Occupant.Player;

/**
 *
 * @author wilson
 */
public class Attack  {

    private Occupant attacking;
    private Occupant hit;
    private int damage;

    public Attack(Occupant attacking, Occupant hit) {
        this.attacking = attacking;
        this.hit = hit;
        if (attacking instanceof Player) {
            playerAttack();
        } else {
            monsterAttack();
        }
    }

    public void monsterAttack() {
        Monster monster = (Monster) attacking;
        if (hit != null && hit instanceof Player) {
            Player player = (Player) hit;
            player.reduceHealth(monster.getDamage());
            if (player.isIsDead()) {
                System.out.println("Player has been slain");
            }
        }
    }

    public void playerAttack() {
        Player player = (Player) attacking;
        
        if (hit != null && hit instanceof Monster) {
            Monster monster = (Monster) hit;
            if (player.isHit(monster)) {
                int damage = player.generateDamage();
                monster.reduceHealth(damage);
                this.damage = damage;
                System.out.println(monster.getName() + " Health: " + monster.getHealth());
                if (monster.isIsDead()) {
                    player.addExp(monster.getExp());
                } else {
                    monster.attack(player);
                }
            }
        }
    }

    public int getDamage() {
        return damage;
    }


}
