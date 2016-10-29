package com.yoyiyi.honglv.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by yoyiyi on 2016/10/29.
 */
public class DownActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down;
    }

    @Override
    protected void initWidget() {
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setTitle("我的下载");
        mToolbar.setNavigationOnClickListener(v -> this.finish());
    }

}
