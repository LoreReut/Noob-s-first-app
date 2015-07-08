package com.lorenzmacht.mobhp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

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
    public void update(int barMonsterHP) {
        monsterHP = barMonsterHP;
    }

    public int getHP() {
        return monsterHP;
    }


}
