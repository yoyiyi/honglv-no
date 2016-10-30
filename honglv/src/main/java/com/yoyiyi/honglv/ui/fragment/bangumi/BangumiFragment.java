package com.yoyiyi.honglv.ui.fragment.bangumi;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.ui.adapter.bangumi.BangumiAdapter;

import butterknife.BindView;

/**
 * 番剧
 * Created by yoyiyi on 2016/10/19.
 */
public class BangumiFragment extends BaseFragment {

    private boolean isPrepared = false;
    @BindView(R.id.stl_bangumi)
    SegmentTabLayout mStlBangumi;
    @BindView(R.id.vp_bangumi)
    ViewPager mVpBangumi;
    private static final String BANGUMI_INDEX = "bangumi_index";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_bangumi;
    }

    public static BangumiFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(BANGUMI_INDEX, index);
        BangumiFragment fragment = new BangumiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

    }

    @Override
    protected void finishCreateView(Bundle state) {
        super.finishCreateView(state);
        // Bundle bundle = getArguments();
        ////  if (bundle!=null){
        //      mCurIndex = bundle.getInt(BANGUMI_INDEX);
        //  }
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisible) return;
        new BangumiAdapter(getActivity().getSupportFragmentManager(), mStlBangumi, mVpBangumi);
        mStlBangumi.showDot(0);
        mStlBangumi.showDot(1);
        mStlBangumi.setCurrentTab(0);
        mStlBangumi.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mStlBangumi.hideMsg(position);
                mVpBangumi.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        isPrepared = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

