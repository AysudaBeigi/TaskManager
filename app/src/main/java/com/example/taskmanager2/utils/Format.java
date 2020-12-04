package com.example.taskmanager2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {


    /************************* GET STRING FORMAT DATE ******************/
    public static String getStringFormatDateAndTime(Date date, Date time) {
        String dateFormat = new SimpleDateFormat("yyy/MM/dd  ").format(date);
        String timeFormat = new SimpleDateFormat("HH:mm:ss").format(time);
        return dateFormat + timeFormat;

    }
}
