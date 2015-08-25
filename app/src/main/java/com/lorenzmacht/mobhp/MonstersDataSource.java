package com.lorenzmacht.mobhp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lo on 17/08/2015.
 */
public class MonstersDataSource {

    private MySQLiteHelper helper;
    private SQLiteDatabase db;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_MAXHP};

    public MonstersDataSource(Context context) {
        helper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteException { db = helper.getReadableDatabase(); }

    public void close() { db.close(); }

    public Monster insertMonster(String monsterName, int monsterMaxHP) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, monsterName);
        values.put(MySQLiteHelper.COLUMN_MAXHP, monsterMaxHP);
        long insertedId = db.insert(MySQLiteHelper.TABLE_MONSTERS, null, values);
        Cursor cursor = db.query(
                MySQLiteHelper.TABLE_MONSTERS,
                allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertedId, null, null, null, null);

        Monster monster = cursorToMonster(cursor);
        cursor.close();
        return monster;
    }

    public void deleteRandomMonster() {
        //Some basic deleting function so it doesn't clog up
        //the view.
    }

    public List<Monster> getAllMonsters() {
        List<Monster> monsterList = new ArrayList<Monster>();

        Cursor cursor = db.query(MySQLiteHelper.TABLE_MONSTERS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Monster monster = cursorToMonster(cursor);
            monsterList.add(monster);
            cursor.moveToNext();
        }
            cursor.close();
        return monsterList;
    }

    private Monster cursorToMonster(Cursor cursor) {
        Monster monster = new Monster();
        if (cursor.getPosition() < 0) {cursor.moveToFirst();}
        monster.setId(cursor.getLong(0));
        monster.setMonsterName(cursor.getString(1));
        monster.setMonsterMaxHP(cursor.getInt(2));
        return monster;
    }
}
