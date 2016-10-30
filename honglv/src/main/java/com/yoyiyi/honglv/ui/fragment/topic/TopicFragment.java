package com.yoyiyi.honglv.ui.fragment.topic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.TopicList;
import com.yoyiyi.honglv.network.server.HttpServer;
import com.yoyiyi.honglv.ui.anim.BaseAnimator;
import com.yoyiyi.honglv.ui.adapter.topic.TopicListAdapter;
import com.yoyiyi.honglv.ui.widget.WrapContentLinearLayoutManager;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * 专题
 * Created by yoyiyi on 2016/10/21.
 */
public class TopicFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        RecyclerView.OnTouchListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    TextView mTotal;
    private boolean isReshing = false;
    private boolean isPrepared = false;
    private List<TopicList> mTopicList = new ArrayList<>();
    private TopicListAdapter mAdapter;
    private static int mTotalCount;//总共
    private SpannableString mSpannableString;
    private View view;

    @Override
    protected int getLayoutId() {
//        Logger.d("onCreateView");
        return R.layout.fragment_base_recycler;

    }

    public static TopicFragment newInstance(int index) {
        TopicFragment fragment = new TopicFragment();
        return fragment;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        view = View.inflate(getActivity(), R.layout.item_bangumi_header, null);
        mTotal = (TextView) view.findViewById(R.id.total);
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
        requestData();
        //mAdapter.removeAllFooterView();

    }


    //加载数据
    @Override
    protected void loadData() {
        if (!isPrepared || !isVisible) return;
        //请求数据
        requestData();
        //填充数据
        initRecyclerView();
        isPrepared = false;
    }


    private void initRecyclerView() {
        mRecycler.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

    }

    private void requestData() {
        setReshing(true);
        mRecycler.post(() -> {
            clearData();
            doHttpConnection();
        });
    }

    //添加头部
    private void addHeader() {

        mAdapter.addHeaderView(view);
        //添加动画
        mAdapter.openLoadAnimation(new BaseAnimator());
    }

    private void doHttpConnection() {
        mTopicList.clear();
        HttpServer.getTopicList(bindToLifecycle(), new Subscriber<List<TopicList>>() {
            @Override
            public void onCompleted() {
                initAdapter();
            }

            @Override
            public void onError(Throwable e) {
                showEmptyView();
            }

            @Override
            public void onNext(List<TopicList> topicLists) {
                mTotalCount = topicLists.size();
                mTopicList.addAll(topicLists);
            }
        });
    }

    private void initAdapter() {
        setReshing(false);
        mAdapter = new TopicListAdapter(mTopicList, getActivity());
        addHeader();
        mRecycler.setAdapter(mAdapter);
        mRecycler.setOnTouchListener(this);
        mRefresh.setOnRefreshListener(this);
        mAdapter.notifyDataSetChanged();
        setTextType();
    }


    /**
     * 设置文字样式
     */
    private void setTextType() {
        String text = mTotalCount + "";
        mSpannableString = new SpannableString("一共" + text + "专题");
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
        //增加加载错误空布局
        //加载结束 加载错误
        setReshing(false);
        mEmpty.setVisibility(View.VISIBLE);
        mEmpty.setEmptyTv("加载错误啦！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
        mEmpty.setOnItemClickLisener(() -> {
            setReshing(true);
            requestData();
        });
        TDevice.showMessage(mRecycler, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    public void clearData() {
        mTotal.setText("");
        mTopicList.clear();
        mTotalCount = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
