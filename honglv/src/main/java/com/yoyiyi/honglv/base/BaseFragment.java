package com.yoyiyi.honglv.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.yoyiyi.honglv.utils.KeyBoardUtil;
import com.yoyiyi.honglv.utils.TDevice;

import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/16.
 */
public abstract class BaseFragment extends RxFragment {
    protected View mRoot;
    protected Bundle mBundle;
    protected boolean hasInternet;
    protected FragmentActivity mActivity;
    protected boolean isVisible;
    protected boolean isPrepared;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);
            }
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            onBindViewBefore(mRoot);
            ButterKnife.bind(this, mRoot);
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    protected void initData() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }


    /**
     * 查找控件
     *
     * @param <T>
     * @return
     */
    protected <T extends View> T $(@IdRes int id) {
        return (T) mRoot.findViewById(id);
    }

    protected void initWidget(View root) {
    }

    protected void onRestartInstance(Bundle savedInstanceState) {
    }


    protected void onBindViewBefore(View root) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        finishCreateView(savedInstanceState);
        hasInternet = TDevice.hasInternet();
    }

    protected void finishCreateView(Bundle state) {
    }

    protected abstract int getLayoutId();

    //数据懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            loadData();//加载数据
        } else {
            isVisible = false;
        }
    }

    protected void loadData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        KeyBoardUtil.HideKeyboard(mRoot);
    }


}
