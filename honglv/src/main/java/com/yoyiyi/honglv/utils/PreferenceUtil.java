package com.yoyiyi.honglv.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yoyiyi.honglv.base.BaseApplication;


/**
 * Created by yoyiyi on 2016/10/16.
 */
public class PreferenceUtil {
    private static SharedPreferences sp;
    private static final String DEFAULT_CONFIG = "config";

    public static void set(String key, String value) {
        if (sp == null) {
            sp = BaseApplication.get_context().getSharedPreferences(DEFAULT_CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static void set(String key, boolean value) {
        if (sp == null) {
            sp = BaseApplication.get_context().getSharedPreferences(DEFAULT_CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static void set(String key, long value) {
        if (sp == null) {
            sp = BaseApplication.get_context().getSharedPreferences(DEFAULT_CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).commit();
    }

    public static void set(String key, int value) {
        if (sp == null) {
            sp = BaseApplication.get_context().getSharedPreferences(DEFAULT_CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static String get(String key, String value) {
        if (sp == null) {
            sp = BaseApplication.get_context().getSharedPreferences(DEFAULT_CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getString(key, "");
    }

    public static boolean get(String key, boolean value) {
        if (sp == null) {
            sp = BaseApplication.get_context().getSharedPreferences(DEFAULT_CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, value);
    }

    public static int get(String key, int value) {
        if (sp == null) {
            sp = BaseApplication.get_context().getSharedPreferences(DEFAULT_CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, value);
    }
}
