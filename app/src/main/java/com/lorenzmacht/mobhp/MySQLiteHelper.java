package com.lorenzmacht.mobhp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lo on 17/08/2015.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "monsters.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_MONSTERS = "Monsters";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_MAXHP = "Max_HP";

    private static final String TEXT_COMMA = ", ";

    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_MONSTERS + "( "
            + COLUMN_ID + " integer primary key autoincrement" + TEXT_COMMA
            + COLUMN_NAME + " text not null" + TEXT_COMMA
            + COLUMN_MAXHP + " integer not null )";

    public MySQLiteHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONSTERS);
        onCreate(db);
    }
}
