package com.yoyiyi.honglv.base;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/16.
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    //获取Activity
    private final String mPackageNameUmeng = this.getClass().getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initWidget();
        initData();
        //umeng analytics
        MobclickAgent.setDebugMode(false);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.mPackageNameUmeng);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.mPackageNameUmeng);
        MobclickAgent.onPause(this);
    }

    protected void initData() {
    }

    protected void initWidget() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutId();

}
