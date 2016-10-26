package com.yoyiyi.honglv.ui.fragment;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;

/**
 * Created by yoyiyi on 2016/10/26.
 */
public class TextFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    public static TextFragment newIntence() {
        return new TextFragment();
    }
}
