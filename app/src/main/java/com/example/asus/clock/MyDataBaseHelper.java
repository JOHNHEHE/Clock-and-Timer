package com.example.asus.clock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2018/2/22.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static MyDataBaseHelper helper;
    private static final int DB_VERSION = 2;
    private static final String DB_NAME ="wrist.db";
    public static final String ALARM_TB_NAME="alarm_tb";
    public static final String COL_TIME="alarm_time";
    public static final String COL_ALARM_STATUS="alarm_status";
    public static final String COL_ALARM_MUSIC="alarm_music";
    public static final String COL_ALARM_NOTE="alarm_note";

    private static final String CREATE_ALARM_TABLE="CREATE TABLE "+ALARM_TB_NAME
            +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COL_ALARM_NOTE+" TEXT NOT NULL,"
            +COL_ALARM_STATUS +" TEXT NOT NULL,"
            +COL_ALARM_MUSIC+" Text NOT NULL,"
            +COL_TIME+" TEXT NOT NULL);";

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static synchronized MyDataBaseHelper getInstance(Context context) {

        if (helper == null) {
            helper = new MyDataBaseHelper(context, DB_NAME, null, DB_VERSION);
        }
        return helper;
    }


    private void createTable(SQLiteDatabase db)
    {
        db.execSQL(CREATE_ALARM_TABLE);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
