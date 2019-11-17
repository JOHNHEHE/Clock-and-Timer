package com.example.asus.clock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2018/2/22.
 */

public class AlarmEditActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private ListView listView;
    private List<Map<String, String>> datalist;
    private Map<String, String> map;
    private SimpleAdapter simpleAdapter;
    private Alarm alarm;
    private EditText note_edit;
    DataBaseOperator dbOperator;
    Calendar time_calender;
    String modify_time_string;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_edit);
        dbOperator = new DataBaseOperator(this);
        modify_time_string = getIntent().getStringExtra("time");
        alarm=new Alarm();
        if(modify_time_string!=null){
            alarm.setAlarmChangeWay("modify");
            alarm.setTime(modify_time_string);
            alarm.setWhichAlarm(getIntent().getIntExtra("position",0));
        }else{
            alarm.setAlarmChangeWay("new");
            alarm.setWhichAlarm(getIntent().getIntExtra("position",0)+1);        }
        datalist = new ArrayList<>();
        Button savealarm=findViewById(R.id.save_alarm);
        Button cancel=findViewById(R.id.cancel_alarm);
        savealarm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Date date = TimeAdjustment.turnStringToDate(alarm.getAlarmTime()+":00");
        note_edit=findViewById(R.id.editText);
        note_edit.setText(getIntent().getStringExtra("notes"));
        time_calender=Calendar.getInstance();
        time_calender.set(Calendar.HOUR_OF_DAY,date.getHours());
        time_calender.set(Calendar.MINUTE,date.getMinutes());//闹钟时间，新建的话就是当前时间
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        TimePicker timePicker=findViewById(R.id.timePicker);
        int hour = time_calender.get(Calendar.HOUR_OF_DAY);
        timePicker.setCurrentHour(hour);
        int minute=time_calender.get(Calendar.MINUTE);
        timePicker.setCurrentMinute(minute);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time_calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time_calender.set(Calendar.MINUTE,minute);
                time_calender.set(Calendar.SECOND, 0);
                if (time_calender.before(Calendar.getInstance())) {
                    time_calender.add(Calendar.DAY_OF_MONTH, 1);//设置时间早于当前时间，加一天
                }
            }
        });
        listView = findViewById(R.id.edit_listview);
        setDataList();
        simpleAdapter = new SimpleAdapter(this, datalist, R.layout.alarm_edit_item,
                new String[]{"name", "value"}, new int[]{R.id.name, R.id.value});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
    }

    public void setDataList() {
        datalist.clear();
        map = new HashMap<>();
        map.put("name", "铃声");
        map.put("value", alarm.getMusic());
        datalist.add(map);
        map = new HashMap<>();
        map.put("name", "状态");
        map.put("value", alarm.getAlarmStatus());
        datalist.add(map);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_alarm:
                saveAlarm();
                finish();
                break;
            case R.id.cancel_alarm:
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                chooseMusic();
                break;
            case 1:
                chooseAlarmStatus();
        }
    }

    private void chooseMusic() {

        String items[] = {"Watching You","Tidal Wave","Runaways","Wild"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("铃声")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                alarm.setMusic("Watching You");
                                flag=0;
                                break;
                            case 1:
                                alarm.setMusic("Tidal Wave");
                                flag=2;
                                break;
                            case 2:
                                alarm.setMusic("Runaways");
                                flag=3;
                                break;
                            case 3:
                                alarm.setMusic("Wild");
                                flag=3;
                                break;
                        }
                        setDataList();
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    private void chooseAlarmStatus() {

        String items[] = {"ON","OFF"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("状态")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:alarm.setAlarmStatus("ON");break;
                            case 1:alarm.setAlarmStatus("OFF");break;
                        }

                        setDataList();
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    private void saveAlarm(){
        String time_string = TimeAdjustment.turnDateToStringonlyTime(time_calender.getTime());
        note_edit=findViewById(R.id.editText);
        String clock_note=note_edit.getText().toString();
        if (time_calender.before(Calendar.getInstance())){
            time_calender.set(Calendar.DAY_OF_MONTH,time_calender.get(Calendar.DAY_OF_MONTH)+1);
        }
        ContentValues values = new ContentValues();
        values.put(MyDataBaseHelper.COL_TIME, time_string);
        values.put(MyDataBaseHelper.COL_ALARM_NOTE,clock_note);
        values.put(MyDataBaseHelper.COL_ALARM_STATUS, alarm.getAlarmStatus());
        values.put(MyDataBaseHelper.COL_ALARM_MUSIC,alarm.getMusic());
        Intent intent=new Intent(this, AlarmReceiver.class);
        if(flag==0){
            intent.putExtra("raw","a");
        }
        if(flag==1){
            intent.putExtra("raw","b");
        }
        if(flag==2){
            intent.putExtra("raw","c");
        }
        if(flag==3){
            intent.putExtra("raw","d");
        }
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if(alarm.getAlarmChangeWay().equals("new")){
            dbOperator.intsert(MyDataBaseHelper.ALARM_TB_NAME, values);
        }else {
            dbOperator.update(MyDataBaseHelper.ALARM_TB_NAME,values,"alarm_time = ?",new String[]{modify_time_string});
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,alarm.getWhichAlarm(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarm.getAlarmStatus().equals("ON")){
                assert alarmManager != null;
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time_calender.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }
}
