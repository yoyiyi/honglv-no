package com.yoyiyi.honglv.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yoyiyi.honglv.base.BaseApplication;
import com.yoyiyi.honglv.ui.activity.BangumiDetailActivity;
import com.yoyiyi.honglv.ui.activity.NewsDetailActivity;
import com.yoyiyi.honglv.ui.activity.TBSBrowerActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.UUID;

/**
 *
 */
public class TDevice {

    public static float dp2px(float dp) {
        return dp * getDisplayMetrics().density;
    }

    public static float px2dp(float px) {
        return px / getDisplayMetrics().density;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return BaseApplication.get_context().getResources().getDisplayMetrics();
    }

    public static float getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public static float getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public static String[] getStringArray(int resId) {
        return BaseApplication.get_context().getResources().getStringArray(resId);
    }

    public static int getStatusBarHeight(Activity context) {
        Rect rectangle = new Rect();
        Window window = context.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public static boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.get_context()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    public static boolean isPortrait() {
        return BaseApplication.get_context().getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isTablet() {
        int s = BaseApplication.get_context().getResources().getConfiguration().screenLayout;
        s &= Configuration.SCREENLAYOUT_SIZE_MASK;
        return s >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null) return;
        View mFocusView = view;

        Context context = view.getContext();
        if (context != null && context instanceof Activity) {
            Activity activity = ((Activity) context);
            mFocusView = activity.getCurrentFocus();
        }
        if (mFocusView == null) return;
        mFocusView.clearFocus();
        InputMethodManager manager = (InputMethodManager) mFocusView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mFocusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showSoftKeyboard(View view) {
        if (view == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        if (!view.isFocused()) view.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    //获取客户端唯一标识
    public static String getAppId() {
        String uniqueID = PreferenceUtil.get("APP_UNIQUEID", "");
        if (TextUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            PreferenceUtil.set("APP_UNIQUEID", uniqueID);
        }
        return uniqueID;
    }

    public static int getVersionCode() {
        return getVersionCode(BaseApplication.get_context().getPackageName());
    }

    //获取版本号
    public static int getVersionCode(String packageName) {
        try {
            return BaseApplication.get_context()
                    .getPackageManager()
                    .getPackageInfo(packageName, 0)
                    .versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            return 0;
        }
    }

    public static String getVersionName() {
        try {
            return BaseApplication
                    .get_context()
                    .getPackageManager()
                    .getPackageInfo(BaseApplication.get_context().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "undefined version name";
        }
    }

    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static boolean openAppActivity(Context context,
                                          String packageName,
                                          String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public static boolean isWifiOpen() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication
                .get_context().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        if (!info.isAvailable() || !info.isConnected()) return false;
        if (info.getType() != ConnectivityManager.TYPE_WIFI) return false;
        return true;
    }

    private static Toast sToast;

    public static void showToast(String msg) {
        if (sToast == null) {
            sToast = new Toast(BaseApplication.get_context());
        }
        sToast.makeText(BaseApplication.get_context(), msg, Toast.LENGTH_LONG).show();
    }

    //public static void showToast(String msg) {
    //Toast.makeText(BaseApplication.get_context(), msg, Toast.LENGTH_LONG).show();
    //}
    public static void startActivity(Context context, Class<?> clz) {
        context.startActivity(new Intent(context, clz));
    }

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, TBSBrowerActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void launchDetail(Context context, Bundle bundle) {
        Intent intent = new Intent(context, BangumiDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showMessage(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static String setTextColor(String text) {
        return String.format("<font color=\"#FF0000\">%s</font>", text);

    }

    /**
     * 下载手机迅雷
     *
     * @param context
     * @param pck
     */
    public static void gotoMarket(Context context, String pck) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pck));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void setTextType(TextView tv, String mTotalCount) {

        SpannableString spannableString = new SpannableString("一共" + mTotalCount + "部番剧");
        spannableString.setSpan(
                new ForegroundColorSpan(Color.RED), 2, 2 + mTotalCount.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spannableString);

    }

    public static void setTextType(TextView tv, String text, String end) {

        SpannableString spannableString = new SpannableString("一共" + text + "部番剧");
        spannableString.setSpan(
                new ForegroundColorSpan(Color.RED), 2, 2 + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spannableString);

    }

    // /////////////////判断是否运行在主线程//////////////////////////
    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    public static boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }

        return false;
    }

    // 运行在主线程
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }


    public static void launchBangumiDetail(Context context, Bundle bundle) {
        Intent intent = new Intent(context, BangumiDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public static void launchNewsDetail(Context context, Bundle bundle) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    //图片适应屏幕
    public static String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        if (elements.size() != 0) {
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "100%");
            }
        }
        return doc.toString();
    }

}
