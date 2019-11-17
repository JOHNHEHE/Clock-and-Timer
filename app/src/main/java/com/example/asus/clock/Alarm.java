package com.example.asus.clock;

import java.util.Calendar;

/**
 * Created by ASUS on 2018/2/22.
 */

public class Alarm {
    //闹钟类
    private int whichAlarm=1;
    private String time;
    private String alarmStatus="ON";
    private String Music="Watching You";
    private String alarmChangeWay="new";

    public int getWhichAlarm() {
        return whichAlarm;
    }

    public Alarm(Calendar time) {
        this.time = TimeAdjustment.turnDateToString(time.getTime());
    }

    public Alarm(){
        this(Calendar.getInstance());
    }

    public String getAlarmChangeWay() {
        return alarmChangeWay;
    }

    public String getTime() {
        return time;
    }

    public String getAlarmTime() {
        return time;
    }

    public String getMusic() {
        return Music;
    }

    public void setMusic(String music) {
        this.Music=music;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }


    public void setAlarmChangeWay(String alarmChangeWay) {
        this.alarmChangeWay = alarmChangeWay;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWhichAlarm(int whichAlarm) {
        this.whichAlarm = whichAlarm;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
}
