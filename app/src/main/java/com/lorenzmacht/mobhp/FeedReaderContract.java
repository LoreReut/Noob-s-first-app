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
}
