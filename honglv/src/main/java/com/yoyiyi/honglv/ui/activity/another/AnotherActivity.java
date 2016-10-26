package com.yoyiyi.honglv.ui.activity.another;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.ui.fragment.another.rank.RankFragment;
import com.yoyiyi.honglv.ui.fragment.another.week.WeekFragment;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class AnotherActivity extends BaseActivity {
    private String mType;
    private Fragment mFragment;

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
        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
    }


}
