package com.yoyiyi.honglv.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by yoyiyi on 2016/10/16.
 */
public class BaseApplication extends Application {
    private static Context _context;
    private static Handler handler;
    private static int mainThreadId;//主线程

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        mainThreadId = Process.myTid();
        QbSdk.initX5Environment(this, null);
        _context = getApplicationContext();
    }

    public static Context get_context() {
        return _context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
