package com.yoyiyi.honglv.ui.adapter.bangumi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.yoyiyi.honglv.ui.adapter.main.ViewPagerInfo;
import com.yoyiyi.honglv.ui.fragment.bangumi.EndBangumiFragment;
import com.yoyiyi.honglv.ui.fragment.bangumi.NewBangumiFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyiyi on 2016/10/21.
 */
public class BangumiAdapter extends FragmentStatePagerAdapter {
    private SegmentTabLayout stl;
    private ViewPager vp;
    private Context mContext;
    private List<ViewPagerInfo> mInfos = new ArrayList<>();
    private static final String[] title = {"连载动漫", "完结动漫"};

    public BangumiAdapter(FragmentManager fm, SegmentTabLayout stl, ViewPager vp) {
        super(fm);
        this.mContext = vp.getContext();
        this.stl = stl;
        this.vp = vp;
        initPagerInfo();
        this.vp.setOffscreenPageLimit(2);
        this.vp.setAdapter(this);
        this.stl.setTabData(title);
        this.vp.setCurrentItem(0);
        this.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                stl.setCurrentTab(position);
                stl.hideMsg(position == 0 ? 1 : 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public Fragment getItem(int position) {
        ViewPagerInfo pagerInfo = mInfos.get(position);
        Fragment fragment = null;
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext,
                    pagerInfo.fragment.getClass().getName());
        }
        return fragment;
    }

    public void initPagerInfo() {
        mInfos.add(new ViewPagerInfo(0, "连载动漫", NewBangumiFragment.newInstance(0)));
        mInfos.add(new ViewPagerInfo(1, "完结动漫", EndBangumiFragment.newInstance(1)));
    }


    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
    }
}
