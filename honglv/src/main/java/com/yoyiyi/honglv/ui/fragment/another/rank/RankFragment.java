package com.yoyiyi.honglv.ui.fragment.another.rank;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.Ranking;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;

import net.qiujuer.genius.ui.widget.Loading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class RankFragment extends BaseFragment {

    private static final String INDEX = "RankFragment_Index";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
  /*  @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabs;*/

    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    /*  @BindView(R.id.view_pager)
      ViewPager mViewPager;*/
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.sliding_tabs)
    SegmentTabLayout mSlidingTabs;
    @BindView(R.id.fl_pnew)
    FrameLayout mFlPnew;
    private List<Ranking> mRankings = new ArrayList<>();
    //private Map<String, Fragment> mFragments = new HashMap<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitle;
    private int mCurrentTab;

    public static RankFragment newInstance(String index) {
        Bundle bundle = new Bundle();
        bundle.putString(INDEX, index);
        RankFragment fragment = new RankFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_index;
    }

    @Override
    protected void finishCreateView(Bundle state) {
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared) return;
        requestData();
        isPrepared = false;
    }

    @Override
    protected void initWidget(View root) {
        mLoading.setAutoRun(true);
        mLoading.start();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setTitle("排行榜");
        mToolbar.setNavigationOnClickListener(v -> getActivity().finish());
    }

    private void requestData() {
        clearData();
        mLoading.setAutoRun(true);
        mLoading.start();
        new Handler().post(() ->
                doHttpxConnection()
        );
    }

    private void clearData() {
        mRankings.clear();
    }

    private void finishTask() {
        mLoading.stop();
        mLoading.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
    }

    private void initFragment() {
        if (mRankings != null && mRankings.size() != 0) {
            mTitle = new String[mRankings.size()];
            for (int i = 0; i < mRankings.size(); i++) {
                mFragments.add(RankDetailFragment.newInstance(i, mRankings.get(i)));
                mTitle[i] = mRankings.get(i).getTab();

            }
        }
        getChildFragmentManager().beginTransaction()
                .add(R.id.fl_pnew, mFragments.get(0))
                .show(mFragments.get(0)).commit();
        mSlidingTabs.setTabData(mTitle);
        mCurrentTab = mSlidingTabs.getCurrentTab();
        mSlidingTabs.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        mSlidingTabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                FragmentTransaction bt =
                        getChildFragmentManager().beginTransaction();
                bt.hide(mFragments.get(mCurrentTab));
                if (!mFragments.get(position).isAdded()) {
                    bt.add(R.id.fl_pnew, mFragments.get(position));
                }
                bt.show(mFragments.get(position)).commit();
                mCurrentTab = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mSlidingTabs.setOnClickListener(v->{
           // Toast.makeText(getActivity(),"")
        });
    }


    private void showEmptyView() {
        mEmpty.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        mEmpty.setEmptyTv("加载错误啦！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
        mEmpty.setOnItemClickLisener(() -> {
                    mLoading.start();
                    mLoading.setVisibility(View.VISIBLE);
                    mEmpty.setVisibility(View.GONE);
                    requestData();
                }
        );
    }

    private void doHttpxConnection() {
        HttpManager.getHttpManager().getHttpService()
                .getRanking().compose(bindToLifecycle())
                .map(rankings -> {
                    if (rankings != null && rankings.size() != 0) {
                        return rankings;
                    }
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rankings -> {
                    mRankings.addAll(rankings);
                    finishTask();
                    initFragment();
                }, e -> {
                    showEmptyView();
                    Logger.d("加载错误");
                });
    }


}
