package com.yoyiyi.honglv.ui.fragment.explore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
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
 * 查找结果
 * Created by yoyiyi on 2016/10/28.
 */
public class ResultFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, RecyclerView.OnTouchListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.loading)
    Loading mLoading;
    private String mKey;
    private SearchAdapter mAdapter;
    private TextView mTotal;
    private static Integer mCurrentPage = 1;//当前页数
    private int mTotalCount;
    private SpannableString mSpannableString;
    private boolean isReshing = false;
    //  private boolean mIsLoadingMore = false;

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
        addHeader();
        mRecycler.setAdapter(mAdapter);
        mRecycler.setOnTouchListener(this);
        mAdapter.setOnLoadMoreListener(this);
    }

    private void addHeader() {
        View view = View.inflate(getActivity(), R.layout.item_bangumi_header, null);
        mTotal = (TextView) view.findViewById(R.id.total);
        mAdapter.addHeaderView(view);
        Loading loading = new Loading(getContext());
        loading.setType(4);
        mAdapter.setLoadingView(loading);
        //添加动画
        mAdapter.openLoadAnimation(new BaseAnimator());
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage++;
        Logger.d(mCurrentPage);
        doHttpConnection();
    }

    private void requestData() {
        isReshing = true;
        mRecycler.post(() -> {
            doHttpConnection();
        });


    }

    private void doHttpConnection() {
        HttpManager.getHttpManager().getHttpService()
                .getSearchResult(mKey, mCurrentPage)
                .compose(bindToLifecycle())
                .map(list -> list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (list.getTotalPage() < mCurrentPage) {
                        //停止请
                        showEndView();
                    } else {
                        mTotalCount = list.getTotalCount();
                        finishTask();
                        mAdapter.addData(list.getList());
                        //TDevice.showToast("" + mCurrentPage);
                    }
                }, e -> {
                    showErrorView();
                });
    }

    private void showErrorView() {
         mAdapter.loadComplete();
        Loading error = new Loading(getActivity());
        error.setType(3);
        mAdapter.setLoadMoreFailedView(error);

    }

    private void showEndView() {
        // mAdapter.loadComplete();//请求结束
        Loading noData = new Loading(getActivity());
        noData.setType(3);
        mAdapter.addFooterView(noData);//设置没有更多数据
    }

    private void finishTask() {
        isReshing = false;
        mLoading.dismiss();
        setTextType();
    }

    /*  private void showNodata() {
          mLoading.setType(5);
      }
  */
    private void setTextType() {
        mTotal.setText("");
        String text = mTotalCount + "";
        mSpannableString = new SpannableString("一共" + text + "结果");
        mSpannableString.setSpan(
                new ForegroundColorSpan(Color.RED), 2, 2 + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTotal.append(mSpannableString);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return isReshing;
    }
}
