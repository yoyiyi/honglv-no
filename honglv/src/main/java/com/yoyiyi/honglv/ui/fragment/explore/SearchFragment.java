package com.yoyiyi.honglv.ui.fragment.explore;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.AnimaList;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.adapter.bangumi.BangumiPullReshAdapter;
import com.yoyiyi.honglv.ui.widget.Loading;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 分区
 * Created by yoyiyi on 2016/10/27.
 */
public class SearchFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener
        , Loading.OnItemCLickListener, RecyclerView.OnTouchListener, View.OnClickListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.search)
    Button mSearch;
    @BindView(R.id.sy)
    ImageButton mSy;
    @BindView(R.id.loading)
    Loading mLoading;

    private Integer mVer;
    private Integer mCurrentPage = 1;
    private BangumiPullReshAdapter mAdapter;
    private boolean isLoadingMore = false;
    private Integer sort = null;//排序方式
    private Integer tab = null;//类型
    private PopupWindow mPopupWindow;
    private String mTitle;
    private RadioGroup mType;
    private RadioGroup mSort;
    private boolean isReshing = false;
    private List<AnimaList.ListBean> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    public static SearchFragment newInstance(Integer ver, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("ver", ver);
        bundle.putString("title", title);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }



    private void initPopuWindow() {
        View view = View.inflate(getActivity(), R.layout.layout_popup_window, null);
        mPopupWindow = new PopupWindow(view, (int)TDevice.dp2px(180),
                (int)TDevice.dp2px(120));
        mType = (RadioGroup) view.findViewById(R.id.type);
        mSort = (RadioGroup) view.findViewById(R.id.sort);
        //  mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        ColorDrawable drawable = new ColorDrawable(Color.WHITE);
        mPopupWindow.setBackgroundDrawable(drawable);
        mPopupWindow.showAsDropDown(mSy);
        initClick();
    }

    @Override
    protected void finishCreateView(Bundle state) {
        mVer = getArguments().getInt("ver");
        mTitle = getArguments().getString("title");
      //  mLoading.setType(4);
        initToolbar();
        isPrepared = true;
        loadData();
    }

    private void initToolbar() {
        mToolbar.setTitle(mTitle);
//        Logger.d(mTitle);
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setNavigationOnClickListener(v -> getActivity().finish());
    }

    @Override
    protected void initWidget(View root) {
        mSy.setOnClickListener(this);
        mSearch.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        if (!isPrepared) return;
        initRecyclerView();
        requestData();
        isPrepared = false;

    }

    private void requestData() {
        isReshing = true;
        mRecycler.post(() -> {
            clearData();
            doHttpConnection();
        });
    }

    private void clearData() {
        // sort = null;
        //  tab = null;
        mCurrentPage = 1;
        isLoadingMore = false;

    }

    private void doHttpConnection() {
        HttpManager.getHttpManager().getHttpService()
                .getBangumi(mVer, null, sort, tab, mCurrentPage)
                .compose(bindToLifecycle())
                .map(animas -> {
                            return animas;
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animas -> {
                    if (animas.getTotalPage() == 0) {
                        mAdapter.loadComplete();
                        isLoadingMore = false;
                        finishTask();
                        //   showEmptyView();
                        Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (animas.getTotalPage() < mCurrentPage) {
                        //停止请求
                        showEndView();
                    } else {
                        finishTask();
                        mList = animas.getList();
                        mAdapter.addData(mList);
                    }
                }, e -> {
                    if (isLoadingMore) {
                        showErrorView();
                    } else {
                        showEmptyView();
                    }
                });

    }

    private void showEmptyView() {
        //没有数据
        Loading noData = new Loading(getActivity());
        noData.setType(5);
        mAdapter.addFooterView(noData);//设置没有更多数据


    }

    private void showErrorView() {
        mAdapter.loadComplete();
        mLoading.dismiss();
        Loading error = new Loading(getActivity());
        error.setType(3);
        isLoadingMore = false;
    }

    private void showEndView() {
        mAdapter.loadComplete();//请求结束
        mLoading.dismiss();
        Loading noData = new Loading(getActivity());
        noData.setType(3);
        mAdapter.addFooterView(noData);//设置没有更多数据
    }

    private void finishTask() {
        mLoading.dismiss();
        isReshing = false;
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage++;
        isLoadingMore = true;
        doHttpConnection();
    }

    private void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BangumiPullReshAdapter(new ArrayList());
        mRecycler.setAdapter(mAdapter);
        addHeader();
        Loading loading = new Loading(getContext());
        loading.setType(4);
        mAdapter.setLoadingView(loading);
        mAdapter.setOnLoadMoreListener(this);
        mLoading.setOnItemClick(this);
    }

    private void addHeader() {
        mLoading = new Loading(getActivity());
        mLoading.setType(4);
        mAdapter.setLoadingView(mLoading);
    }

    @Override
    public void reLoad() {
        onLoadMoreRequested();
    }

    private void initClick() {
        mType.setOnCheckedChangeListener((group, id) -> {
            int buttonId = group.getCheckedRadioButtonId();
            switch (buttonId) {
                case R.id.wj:
                    tab = 18;
                    break;
                case R.id.lz:
                    tab = 7;
                    break;
            }
            Logger.d(tab);
        });
        mSort.setOnCheckedChangeListener((group, id) -> {
            int buttonId = group.getCheckedRadioButtonId();
            switch (buttonId) {
                case R.id.rm:
                    sort = 1;
                    break;
                case R.id.zj:
                    sort = null;
                    break;
            }
            Logger.d(sort);
        });


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return isReshing;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sy:
                initPopuWindow();
                Logger.d("popup");
                break;

            case R.id.search:
                requestData();
                mAdapter.setNewData(mList);
                break;
        }
    }

}
