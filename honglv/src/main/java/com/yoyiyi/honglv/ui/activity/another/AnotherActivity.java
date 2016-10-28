package com.yoyiyi.honglv.ui.activity.another;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.ui.fragment.another.rank.RankFragment;
import com.yoyiyi.honglv.ui.fragment.another.recent.RecentFragment;
import com.yoyiyi.honglv.ui.fragment.another.week.WeekFragment;
import com.yoyiyi.honglv.ui.fragment.explore.ResultFragment;
import com.yoyiyi.honglv.ui.fragment.explore.SearchFragment;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class AnotherActivity extends BaseActivity {
    private String mType;
    private Fragment mFragment;
    private Intent mIntent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_week;
    }

    @Override
    protected void initWidget() {
        getThisIntent();
        initAdapter();

    }

    private void initAdapter() {

        switch (mType) {
            case "RankFragment":
                mFragment = RankFragment.newInstance(mType);
                break;
            case "WeekFragment":
                mFragment = WeekFragment.newInstance(mType);
                break;
            case "RecentFragment":
                mFragment = RecentFragment.newInstance(mType);
                break;
            case "ExploreFragment":
                mFragment = SearchFragment.newInstance(
                        mIntent.getIntExtra("num", 0),
                        mIntent.getStringExtra("title"));
                break;
            case "SearchFragment":
                mFragment = ResultFragment.newInstance(mIntent.getStringExtra("key"));
                break;


        }
        final FragmentTransaction trans =
                getSupportFragmentManager()
                        .beginTransaction();
        if (!mFragment.isAdded()) {
            trans.add(R.id.fl_new, mFragment);
        }
        trans.show(mFragment).commit();
    }

    public void getThisIntent() {
        mIntent = getIntent();
        mType = mIntent.getStringExtra("type");
    }


}
