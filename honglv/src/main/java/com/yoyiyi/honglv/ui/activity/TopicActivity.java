package com.yoyiyi.honglv.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.ui.fragment.topic.TopicDetailFragment;

import butterknife.BindView;



/**
 * 专题详情界面
 * Created by yoyiyi on 2016/10/23.
 */
public class TopicActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private String mTitle;
    private static String mUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic;
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setTitle(TextUtils.isEmpty(mTitle) ? "详情" : mTitle);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initWidget() {
        getTitleIntent();
        initToolbar();
        TopicDetailFragment fragment = TopicDetailFragment.newInstance(mUrl);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, fragment)
                .show(fragment)
                .commit();

    }

    private void getTitleIntent() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mTitle = intent.getStringExtra("title");
        Logger.d(mUrl);
    }

}
