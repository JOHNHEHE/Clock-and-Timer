package com.example.asus.clock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by ASUS on 2018/2/22.
 */

public class DeleteAlarmActivity extends AppCompatActivity {

    ListView listView;
    MyListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        Button button = findViewById(R.id.cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listView = findViewById(R.id.delete_listview);
        initData();
    }

    private void initData(){
        MyDataBaseHelper helper = MyDataBaseHelper.getInstance(this);
        SQLiteDatabase dWriter = helper.getWritableDatabase();
        Cursor cursor = dWriter.query(MyDataBaseHelper.ALARM_TB_NAME,null,null,null,null,null,null);
        String[] alarmColums = new String[]{MyDataBaseHelper.COL_TIME,MyDataBaseHelper.COL_ALARM_NOTE};
        int[] layoutId = new int[]{R.id.delete_time,R.id.delete_note};
        adapter = new MyListViewAdapter(this,R.layout.delete_item,cursor,alarmColums,layoutId,CursorAdapter.FLAG_AUTO_REQUERY);
        listView.setAdapter(adapter);
    }

    public class MyListViewAdapter extends SimpleCursorAdapter{
        public MyListViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }


        @Override
        public void bindView(View view, final Context context, final Cursor cursor) {
            super.bindView(view, context, cursor);
            final int id = cursor.getInt(0);
            Button delete_clock = view.findViewById(R.id.delete_clock);
            delete_clock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseOperator operator=new DataBaseOperator(context);
                    Alarm delete_alarm=operator.queryAlarmWithId(id);
                    String delete_alarm_time=delete_alarm.getAlarmTime();
                    operator.deleteAlarm(delete_alarm_time);
                    cursor.requery();
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
