package com.lorenzmacht.mobhp;

/**
 * Created by Lo on 02/07/2015.
 */
public class MonsterHP {

    private int monsterHP;
    private int barMonsterHP;

    public MonsterHP (int monsterHP, int barMonsterHP) {
        this.monsterHP = monsterHP;
        this.barMonsterHP = barMonsterHP;
    }
    public void update() {
        monsterHP = barMonsterHP;
    }

    public int getHP() {
        return monsterHP;
    }
}
