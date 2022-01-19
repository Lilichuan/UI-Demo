package com.example.luffy.ui_demo.util;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import androidx.annotation.NonNull;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class TimeTool{
    public static Calendar StringToCalendar(String pattern, String time){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;

    }

    public static String calendarToString(@NonNull String pattern, Calendar calendar){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        return formatter.format(calendar.getTime());
    }
}