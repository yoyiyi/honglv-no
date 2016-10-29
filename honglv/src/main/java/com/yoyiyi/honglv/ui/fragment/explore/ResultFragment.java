package com.yoyiyi.honglv.ui.fragment.explore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.adapter.search.SearchAdapter;
import com.yoyiyi.honglv.ui.anim.BaseAnimator;
import com.yoyiyi.honglv.ui.widget.Loading;

import java.util.ArrayList;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoyiyi on 2016/10/28.
 */
public class ResultFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.loading)
    Loading mLoading;
    private String mKey;
    private SearchAdapter mAdapter;
    private TextView mTotal;
    private int mTotalCount;
    private SpannableString mSpannableString;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_result;
    }

    public static ResultFragment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("key", name);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void finishCreateView(Bundle state) {
        mKey = getArguments().getString("key");
        initToolbar();
        mLoading.setType(4);
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared) return;
        initRecyclerView();
        requestData();
        isPrepared = false;
    }

    private void initToolbar() {
        mToolbar.setTitle(mKey);
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setNavigationOnClickListener(v -> getActivity().finish());

    }

    private void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SearchAdapter(new ArrayList());
        mRecycler.setAdapter(mAdapter);
       addHeader();
    }

    private void addHeader() {
        View view = View.inflate(getActivity(), R.layout.item_bangumi_header, null);
        mTotal = (TextView) view.findViewById(R.id.total);
        mAdapter.addHeaderView(view);
       // mAdapter.setLoadingView(mLoading);
        //添加动画
        mAdapter.openLoadAnimation(new BaseAnimator());
    }

    private void requestData() {
        HttpManager.getHttpManager().getHttpService()
                .getSearchResult(mKey)
                .compose(bindToLifecycle())
                .map(list -> list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    mTotalCount = list.getList().size();
                    finishTask();
                    //addHeader();
                    mAdapter.setNewData(list.getList());
                }, e -> showNodata());

    }

    private void finishTask() {
        mLoading.dismiss();
        setTextType();
    }

    private void showNodata() {
        mLoading.setType(5);
    }

    private void setTextType() {
        mTotal.setText("");
        String text = mTotalCount + "";
        mSpannableString = new SpannableString("一共" + text + "结果");
        mSpannableString.setSpan(
                new ForegroundColorSpan(Color.RED), 2, 2 + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTotal.append(mSpannableString);

    }
}
