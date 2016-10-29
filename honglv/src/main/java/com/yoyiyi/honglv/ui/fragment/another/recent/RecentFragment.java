package com.yoyiyi.honglv.ui.fragment.another.recent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.RecentUpdate;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.adapter.another.RecentAdapter;
import com.yoyiyi.honglv.ui.anim.BaseAnimator;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;

import net.qiujuer.genius.ui.widget.Loading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class RecentFragment extends BaseFragment
        implements /*SwipeRefreshLayout.OnRefreshListener,*/ RecyclerView.OnTouchListener {

    private static final String INDEX = "RankFragment_Index";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.loading)
    Loading mLoading;

    private List<RecentUpdate> mRecentUpdates = new ArrayList<>();
    private int mTotalCount;
    private SpannableString mSpannableString;
    private RecentAdapter mAdapter;
    private TextView mTotal;
    private boolean isReshing;
    private View view;

    public static RecentFragment newInstance(String index) {
        Bundle bundle = new Bundle();
        bundle.putString(INDEX, index);
        RecentFragment fragment = new RecentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recent;
    }

    @Override
    protected void finishCreateView(Bundle state) {
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared) return;
        initRecyclerView();
        addHeader();
        requestData();
        isPrepared = false;
    }

    @Override
    protected void initWidget(View root) {
        initSwiperReshLayoutColor();
        mEmpty.setVisibility(View.GONE);
        view = View.inflate(getActivity(), R.layout.item_bangumi_header, null);
        mTotal = (TextView) view.findViewById(R.id.total);
        mLoading.setAutoRun(true);
        mLoading.start();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setTitle("最近更新");
        mToolbar.setNavigationOnClickListener(v -> getActivity().finish());
    }

    private void requestData() {
        setReshing(true);
        mLoading.setAutoRun(true);
        mLoading.start();
        clearData();
        mRecycler.post(() ->
                    doHttpxConnection()

        );
    }

    private void clearData() {
        mTotalCount = 0;
        mRecentUpdates.clear();
    }

    private void finishTask() {
        mLoading.stop();
        mLoading.setVisibility(View.GONE);
        mRefresh.setRefreshing(false);
        mEmpty.setVisibility(View.GONE);
    }

    private void initAdapter() {
        mTotalCount = mRecentUpdates.size();
        mAdapter.setNewData(mRecentUpdates);
        setTextType();
    }

    private void initRecyclerView() {
        mAdapter = new RecentAdapter(new ArrayList<>());
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mAdapter);
    }

    private void showEmptyView() {
        mEmpty.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        mEmpty.setEmptyTv("加载错误啦！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
        mEmpty.setOnItemClickLisener(() -> {
                    mLoading.start();
                    mLoading.setVisibility(View.VISIBLE);
                    mEmpty.setVisibility(View.GONE);
                    requestData();
                }
        );
    }

    private void addHeader() {
        mAdapter.addHeaderView(view);
        mAdapter.openLoadAnimation(new BaseAnimator());

    }

    private void doHttpxConnection() {
        HttpManager.getHttpManager()
                .getHttpService()
                .getRecentUpdate()
                .compose(bindToLifecycle())
                .map(rankings -> {
                    if (rankings != null && rankings.size() != 0) {
                        return rankings;
                    }
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rankings -> {
                    mTotalCount = rankings.size();
                    mRecentUpdates.addAll(rankings);
                    finishTask();
                    initAdapter();
                }, e -> {
                   showEmptyView();
                    //Logger.d("加载错误");
                });
    }

    /**
     * 设置文字样式
     */
    private void setTextType() {
        mTotal.setText("");
        String text = mTotalCount + "";
        mSpannableString = new SpannableString("一共" + text + "部番剧更新");
        mSpannableString.setSpan(
                new ForegroundColorSpan(Color.RED), 2, 2 + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTotal.append(mSpannableString);

    }


    //设置颜色
    private void initSwiperReshLayoutColor() {
        mRefresh.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

    }

   /* //刷新事件
    @Override
    public void onRefresh() {
        setReshing(true);
        requestData();

    }*/

    //设置刷新
    public void setReshing(boolean isReshing) {
        this.isReshing = isReshing;
        mRefresh.setRefreshing(isReshing);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return isReshing;
    }
}
