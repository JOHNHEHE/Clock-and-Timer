package com.example.asus.clock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS on 2018/2/22.
 */

public class TimeAdjustment {
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        return format.format(now);
    }


    public static Date turnStringToDate(String sTime){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date now = null;
        try {
            now =format.parse(sTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return now;
    }

    public static String turnDateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time;
        time =format.format(date);
        return time;

    }

    public static String turnDateToStringonlyTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time;
        time =format.format(date);
        return time;
    }
}
