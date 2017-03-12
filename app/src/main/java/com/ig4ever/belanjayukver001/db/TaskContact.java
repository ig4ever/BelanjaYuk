package com.ig4ever.belanjayukver001.db;

import android.provider.BaseColumns;

/**
 * Created by ig4ever on 3/12/2017.
 */

public class TaskContact {
    public static final String DB_NAME = "com.example.TodoList.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
    }
}
