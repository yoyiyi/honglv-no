package com.yoyiyi.honglv.ui.activity;

import android.support.v7.widget.Toolbar;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;

import butterknife.BindView;

/**
 * 关于我的界面
 * Created by yoyiyi on 2016/10/29.
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initWidget() {
        mToolbar.setTitle("关于我");
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }
}
