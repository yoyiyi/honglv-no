package com.yoyiyi.honglv.ui.activity.another;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.HotNew;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.fragment.another.hot.HotFragment;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;
import com.yoyiyi.honglv.utils.TDevice;

import net.qiujuer.genius.ui.widget.Loading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoyiyi on 2016/10/23.
 */
public class HotActivity extends BaseActivity /*implements SwipeRefreshLayout.OnRefreshListener*/ {

    @BindView(R.id.stl_bangumi)
    SegmentTabLayout mStlBangumi;
    //  @BindView(R.id.refresh)
    //  SwipeRefreshLayout mRefresh;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    // @BindView(R.id.vp)
    // ViewPager mVp;
    @BindView(R.id.fl_hot)
    FrameLayout mFlContainer;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    @BindView(R.id.c_loading)
    Loading mCLoading;
    // private boolean isReshing = false;
    private List<HotNew> mHotNewList = new ArrayList<>();
    private String[] mTitle = new String[4];
    private List<BaseFragment> mFragments = new ArrayList<>();
    private int mTab = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }

    @Override
    protected void initWidget() {
        mCLoading.setAutoRun(true);
        mCLoading.start();
        initToolbar();
        requestData();

    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setTitle("热门番剧");
        mToolbar.setNavigationOnClickListener(v -> finish());
    }


    private void initFragment() {
        for (int i = 0; i < mHotNewList.size(); i++) {
            HotFragment hotFragment = HotFragment.newInstance(i, mHotNewList.get(i).getChild());
            mFragments.add(hotFragment);
        }
        for (Fragment f : mFragments) {
            int ye = f.getArguments().getInt("ye");
            Logger.d(ye);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_hot, mFragments.get(0))
                .show(mFragments.get(0))
                .commit();
    }

    private void switchFragment() {
        mStlBangumi.setCurrentTab(0);
        Logger.d(mStlBangumi.getCurrentTab());
        mTab = mStlBangumi.getCurrentTab();
        mStlBangumi.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                FragmentTransaction bt =
                        getSupportFragmentManager()
                                .beginTransaction();
                bt.hide(mFragments.get(mTab));
                if (!mFragments.get(position).isAdded()) {
                    bt.add(R.id.fl_hot, mFragments.get(position));
                }
                bt.show(mFragments.get(position)).commit();
                mTab = position;
                Logger.d(mTab);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void requestData() {
        HttpManager
                .getHttpManager()
                .getHttpService()
                .getNewHotAnimate()
                .compose(bindToLifecycle())
                .map(hotNews -> hotNews)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hotNews -> {
                    if (hotNews.size() != 0 && hotNews != null) {
                        mHotNewList.addAll(hotNews);
                        initAdapter();
                    }
                }, e -> showEmptyView());
    }

    private void initAdapter() {
        initTab();
        initFragment();
        switchFragment();
        mCLoading.start();
        mCLoading.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        //增加加载错误空布局
        mEmpty.setVisibility(View.VISIBLE);
        mEmpty.setEmptyTv("加载错误啦！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
        mEmpty.setOnItemClickLisener(() -> {
            requestData();
        });
        TDevice.showMessage(mFlContainer, "数据加载失败,请重新加载或者检查网络是否链接");
    }


    private void initTab() {
        for (int i = 0; i < mHotNewList.size(); i++) {
            //初始化标题
            mTitle[i] = mHotNewList.get(i).getTab();
        }
        //填充标题
        mStlBangumi.setTabData(mTitle);
    }

}
