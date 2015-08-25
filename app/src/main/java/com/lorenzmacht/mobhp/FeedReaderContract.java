package com.lorenzmacht.mobhp;

import android.provider.BaseColumns;

/**
 * Created by Lo on 09/08/2015.
 */
public class FeedReaderContract {

    public FeedReaderContract() {}

    public static abstract class FeedEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "mobs";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "name";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }

        private static final String TEXT_TYPE = "TEXT";
        private static final String COMMA = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE" + FeedEntry.TABLE_NAME +
                " (" + FeedEntry._ID + " INTEGER PRIMARY KEY," +
                FeedEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA +
                FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA +
                //CREATE COMMAND OPTIONS I DON'T YET UNDERSTAND
                " )";


}
