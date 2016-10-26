package com.yoyiyi.honglv.ui.adapter.another.rank;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class RankLayoutAdapter extends FragmentStatePagerAdapter {
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private Context mContext;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mStringTitle;

    public RankLayoutAdapter(FragmentManager fm, SlidingTabLayout tab, String[] title,
                             ViewPager viewPager, List<Fragment> list) {
        super(fm);
        this.mContext = viewPager.getContext();
        this.mSlidingTabLayout = tab;
        this.mViewPager = viewPager;
        this.mStringTitle = title;
      //  addListener();
        this.mFragments = list;
        mViewPager.setAdapter(this);
      //  mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);
        mSlidingTabLayout.setViewPager(mViewPager, mStringTitle);

    }

    private void addListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSlidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSlidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext,
                    mFragments.get(position)
                            .getClass().getName());
        }
        return fragment;
    }

    /*@Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }*/

    @Override
    public int getCount() {
        return mFragments.size();
    }

  /*  @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }*/

     @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Yet another bug in FragmentStatePagerAdapter that destroyItem is called on fragment that hasnt been added. Need to catch
        try {
            super.destroyItem(container, position, object);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
