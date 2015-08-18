package com.lorenzmacht.mobhp;

/**
 * Created by Lo on 17/08/2015.
 */
public class Monster {

    private long id;
    private String monsterName;
    private int monsterMaxHP;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public int getMonsterMaxHP() {
        return monsterMaxHP;
    }

    public void setMonsterMaxHP(int monsterMaxHP) {
        this.monsterMaxHP = monsterMaxHP;
    }

    //Used by ArrayAdapter, probably
    @Override
    public String toString() {
        return monsterName;
    }

}
