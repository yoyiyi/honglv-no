package com.yoyiyi.honglv;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;

import com.yoyiyi.honglv.base.BaseApplication;

import java.io.File;

/**
 * Created by yoyiyi on 2016/10/21.
 */
public class AppConfig {
    public static final int PAGE_SIZE = 20;
    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH =
            Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "GUMIDM"
                    + File.separator + "download" + File.separator;

    //是否sd卡
    public static boolean isHasSDcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    // 获取手机SD卡总空间
    private static long getSDcardTotalSize() {
        if (isHasSDcard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();

            return blockSizeLong * blockCountLong;
        } else {
            return 0;
        }
    }

    // 获取SDCard可用空间
    private static long getSDcardAvailableSize() {

        if (isHasSDcard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else
            return 0;
    }

    // 获取手机内部存储总空间
    public static long getPhoneTotalSize() {
        if (!isHasSDcard()) {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();
            return blockSizeLong * blockCountLong;
        } else
            return getSDcardTotalSize();
    }

    // 获取手机内存存储可用空间
    public static long getPhoneAvailableSize() {
        if (!isHasSDcard()) {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else
            return getSDcardAvailableSize();
    }

    // 检查是否有网络
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }


    // 检查是否是WIFI
    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
        }
        return false;
    }


    // 检查是否是移动网络
    public static boolean isMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    //获取网络信息
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = BaseApplication.get_context().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
