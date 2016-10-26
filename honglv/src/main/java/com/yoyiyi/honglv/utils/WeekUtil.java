package com.yoyiyi.honglv.utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by yoyiyi on 2016/10/26.
 */
public class WeekUtil {
    public static String mWeek;

    public static String getWeek() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mWeek = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWeek)) {
            mWeek = "日";
        } else if ("2".equals(mWeek)) {
            mWeek = "一";
        } else if ("3".equals(mWeek)) {
            mWeek = "二";
        } else if ("4".equals(mWeek)) {
            mWeek = "三";
        } else if ("5".equals(mWeek)) {
            mWeek = "四";
        } else if ("6".equals(mWeek)) {
            mWeek = "五";
        } else if ("7".equals(mWeek)) {
            mWeek = "六";
        }
        return mWeek;
    }

    public static int getWeekPos() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
