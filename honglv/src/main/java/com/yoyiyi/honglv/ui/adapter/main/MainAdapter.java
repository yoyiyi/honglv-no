package com.yoyiyi.honglv.ui.adapter.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flyco.tablayout.SlidingTabLayout;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.ui.fragment.bangumi.BangumiFragment;
import com.yoyiyi.honglv.ui.fragment.explore.ExploreFragment;
import com.yoyiyi.honglv.ui.fragment.home.HomeFragment;
import com.yoyiyi.honglv.ui.fragment.news.NewsFragment;
import com.yoyiyi.honglv.ui.fragment.topic.TopicFragment;
import com.yoyiyi.honglv.ui.widget.NoScrollViewPager;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;

/**
 * Created by yoyiyi on 2016/10/17.
 */
public class MainAdapter extends FragmentPagerAdapter {
    private SlidingTabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    //private ViewPager mViewPager;
    private Context mContext;
    private ArrayList<ViewPagerInfo> info = new ArrayList<>();

    public MainAdapter(FragmentManager fm, SlidingTabLayout tabLayout, NoScrollViewPager pager) {
        super(fm);
        mContext = pager.getContext();
        this.mTabLayout = tabLayout;
        this.mViewPager = pager;
        initInfo();
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(this);
        mViewPager.setCurrentItem(0);
        mTabLayout.setTextsize(16);
        mTabLayout.setViewPager(mViewPager, TDevice.getStringArray(R.array.main_arrays));
    }

    private void initInfo() {
        info.add(new ViewPagerInfo(0, "首页", HomeFragment.newInstance(0)));
        info.add(new ViewPagerInfo(1, "番剧", BangumiFragment.newInstance(1)));
        info.add(new ViewPagerInfo(2, "专题", TopicFragment.newInstance(2)));
        info.add(new ViewPagerInfo(3, "发现", ExploreFragment.newInstance(3)));
        info.add(new ViewPagerInfo(4, "新闻", NewsFragment.newInstance(4)));

    }

    @Override
    public Fragment getItem(int position) {
        ViewPagerInfo pagerInfo = info.get(position);
        Fragment fragment = null;
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext, pagerInfo.fragment.getClass().getName());
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return info.size();
    }
}
