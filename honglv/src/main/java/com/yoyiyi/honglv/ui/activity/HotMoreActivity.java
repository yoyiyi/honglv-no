package com.yoyiyi.honglv.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.bean.HotNew;
import com.yoyiyi.honglv.ui.adapter.another.HotMoreAdapter;
import com.yoyiyi.honglv.ui.anim.BaseAnimator;
import com.yoyiyi.honglv.ui.widget.Loading;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class HotMoreActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private String mTitle;
    private ArrayList<HotNew.ChildBean.OtherListBean> mMore;
    private HotMoreAdapter mHotMoreAdapter;
    private TextView mTotal;
    private Loading mLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_more;
    }

    @Override
    protected void initWidget() {
        getInfoIntent();
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mHotMoreAdapter = new HotMoreAdapter(new ArrayList<>());

        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mHotMoreAdapter);
        addHeader();
        if (mMore.size() != 0 && mMore != null) {
            mTotal.setText(mTitle);
            mHotMoreAdapter.setNewData(mMore);
        }
    }


    private void addHeader() {
        View view = View.inflate(this, R.layout.item_bangumi_header, null);
        mTotal = (TextView) view.findViewById(R.id.total);
        mLoading = new Loading(this);
        mLoading.setType(4);
        mHotMoreAdapter.addHeaderView(view);
        mHotMoreAdapter.setLoadingView(mLoading);
        //添加动画
        mHotMoreAdapter.openLoadAnimation(new BaseAnimator());
    }

    private void initToolbar() {
        mToolbar.setTitle(mTitle);
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    protected void getInfoIntent() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mTitle = extras.getString("title");
            mMore = (ArrayList<HotNew.ChildBean.OtherListBean>)
                    extras.getParcelableArrayList("list").get(0);
        }

    }
}
