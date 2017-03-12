package com.ig4ever.belanjayukver001.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ig4ever on 3/12/2017.
 */

public class TaskDBHelper extends SQLiteOpenHelper {
    public TaskDBHelper(Context context) {
        super(context, TaskContact.DB_NAME, null, TaskContact.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT)", TaskContact.TABLE,
                        TaskContact.Columns.TASK);

        Log.d("TaskDBHelper","Query to form table: "+sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+TaskContact.TABLE);
        onCreate(sqlDB);
    }
}
