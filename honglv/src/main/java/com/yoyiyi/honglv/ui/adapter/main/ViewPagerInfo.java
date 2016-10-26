package com.yoyiyi.honglv.ui.adapter.main;

import android.support.v4.app.Fragment;

/**
 * Created by yoyiyi on 2016/10/17.
 */
public class ViewPagerInfo {
    public String title;
    public Class<?> clz;
    public int tag;
    public Fragment fragment;

    public ViewPagerInfo(int tag, String title, Class<?> clz) {
        this.title = title;
        this.clz = clz;
        this.tag = tag;
    }

    public ViewPagerInfo(int tag, String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
        this.tag = tag;
    }
}
