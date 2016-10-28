package com.yoyiyi.honglv.bean.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by yoyiyi on 2016/10/28.
 */
public class TitleEntity implements CustomTabEntity {
    public String title;

    @Override
    public String getTabTitle() {
        return this.title;
    }

    public TitleEntity(String title) {
        this.title = title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}
