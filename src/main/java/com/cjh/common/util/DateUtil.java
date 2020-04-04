package com.cjh.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public static String format(Date date) {
        format(date, "yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String format(Date date, String pattern) {
        simpleDateFormat.applyPattern(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date parse(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parse(String date, String pattern) {
        simpleDateFormat.applyPattern(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
