package com.schoolproject.traveltour.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String stdDateFormat(Date d) {
        return new SimpleDateFormat("EEE MMM dd, yyyy", Locale.ENGLISH).format(d);
    }
}
