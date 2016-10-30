package com.yoyiyi.honglv.ui.fragment.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.News;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.adapter.news.NewsAdapter;
import com.yoyiyi.honglv.ui.anim.BaseAnimator;
import com.yoyiyi.honglv.ui.widget.Loading;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 新闻假面
 * Created by yoyiyi on 2016/10/21.
 */
public class NewsFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,
        RecyclerView.OnTouchListener, Loading.OnItemCLickListener {

    private static final String NEWBANGUMI_INDEX = "newbangumi_index";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    TextView mTotal;
    private boolean isReshing = false;
    private boolean isPrepared = false;
    private List<News.ListBean> mNews = new ArrayList<>();
    private NewsAdapter mAdapter;
    private static int mCurrentPage = 1;//当前页数
    private static int mTotalCount;//总共番剧
    private Loading mLoading;

    private static boolean isLoadingMore = false;//判断是否在执行加载更多的操作
    private SpannableString mSpannableString;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    public static NewsFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(NEWBANGUMI_INDEX, index);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initSwiperReshLayoutColor();

    }


    @Override
    protected void finishCreateView(Bundle state) {
        super.finishCreateView(state);
        isPrepared = true;
        loadData();
    }

    //设置颜色
    private void initSwiperReshLayoutColor() {
        mRefresh.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

    }

    //刷新事件
    @Override
    public void onRefresh() {
        setReshing(true);
        initRecyclerView();
        requestData();
        // mAdapter.removeAllFooterView();
        isLoadingMore = false;

    }

    private void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsAdapter(new ArrayList());
        addHeader();
        mRecycler.setAdapter(mAdapter);
        mRecycler.setOnTouchListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mRefresh.setOnRefreshListener(this);
        mLoading.setOnItemClick(this);
    }

    private void showErrorView() {
        mAdapter.loadComplete();
        Loading error = new Loading(getActivity());
        error.setType(3);
        isLoadingMore = false;
    }

    //加载数据
    @Override
    protected void loadData() {
        if (!isPrepared || !isVisible) return;
        initRecyclerView();
        requestData();
        isPrepared = false;


    }

    private void requestData() {
        setReshing(true);
        mRecycler.post(() -> {
            clearData();
            doHttpConnection();
        });
    }

    private void doHttpConnection() {
        HttpManager.getHttpManager().getHttpService()
                .getNews(mCurrentPage)
                .compose(bindToLifecycle())
                .map(news -> {
                            return news;
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                    if (news.getTotalPage() < mCurrentPage) {
                        //停止请求
                        showEndView();
                    } else {
                        mTotalCount = news.getTotalCount();
                        finishTask();
                        mAdapter.addData(news.getList());
                    }
                }, e -> {
                    if (isLoadingMore) {
                        showErrorView();
                    } else {
                        showEmptyView();
                    }
                });

    }

    private void showEndView() {
        mAdapter.loadComplete();//请求结束
        Loading noData = new Loading(getActivity());
        noData.setType(3);
        mAdapter.addFooterView(noData);//设置没有更多数据
    }

    private void finishTask() {
        if (!isLoadingMore) {
            setTextType();
        }
        mEmpty.setVisibility(View.GONE);
        setReshing(false);
    }

    //添加头部
    private void addHeader() {
        View view = View.inflate(getActivity(), R.layout.item_bangumi_header, null);
        mTotal = (TextView) view.findViewById(R.id.total);
        mLoading = new Loading(getContext());
        mLoading.setType(4);
        mAdapter.addHeaderView(view);
        mAdapter.setLoadingView(mLoading);
        //添加动画
        mAdapter.openLoadAnimation(new BaseAnimator());
    }


    /**
     * 设置文字样式
     */
    private void setTextType() {
        String text = mTotalCount + "";
        mSpannableString = new SpannableString("一共" + text + "条新闻");
        mSpannableString.setSpan(
                new ForegroundColorSpan(Color.RED), 2, 2 + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTotal.append(mSpannableString);

    }

    //设置刷新
    public void setReshing(boolean isReshing) {
        this.isReshing = isReshing;
        mRefresh.setRefreshing(isReshing);

    }


    //设置滑动监听
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return isReshing;
    }


    private void showEmptyView() {
        mLoading.setType(1);
        setReshing(false);
        mEmpty.setVisibility(View.VISIBLE);
        mEmpty.setEmptyTv("加载错误啦！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
        mEmpty.setOnItemClickLisener(() -> {
            setReshing(true);
            doHttpConnection();
            requestData();
        });
        TDevice.showMessage(mRecycler, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    public void clearData() {
        mTotal.setText("");
        isLoadingMore = false;
        mNews.clear();
        mCurrentPage = 1;
        mTotalCount = 0;
    }

    @Override
    public void reLoad() {
        onLoadMoreRequested();
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage++;
        isLoadingMore = true;
        doHttpConnection();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
