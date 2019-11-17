package com.example.asus.clock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 2018/2/22.
 */

public class DataBaseOperator {

    MyDataBaseHelper helper;
    SQLiteDatabase dbWriter;
    public DataBaseOperator(Context context) {
        helper=MyDataBaseHelper.getInstance(context);
        dbWriter=helper.getWritableDatabase();
    }

    public void intsert(String tbName,ContentValues values){
        dbWriter.insert(tbName,null,values);
    }

    public Cursor query(String tb){

        return dbWriter.query(tb,null,null,null,null,null,null);
    }


    public Alarm queryAlarmWithId(int id){
        Alarm alarm=new Alarm();
        String QUERY_ALARMINFO="SELECT * FROM "
                +MyDataBaseHelper.ALARM_TB_NAME+" WHERE "
                +"_id='"+String.valueOf(id)+"'";
        Cursor cursor = dbWriter.rawQuery(QUERY_ALARMINFO,null);
        while (cursor.moveToNext()){

            alarm.setMusic(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COL_ALARM_MUSIC)));
            alarm.setAlarmStatus(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COL_ALARM_STATUS)));
            alarm.setTime(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COL_TIME)));
            return alarm;
        }
        return alarm;
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
        return dbWriter.update(table,values,whereClause,whereArgs);
    }

    public int deleteAlarm(String time) {

        String[] args = new String[]{time};
        return dbWriter.delete(MyDataBaseHelper.ALARM_TB_NAME,"alarm_time=?",args);
    }

}
