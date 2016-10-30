package com.yoyiyi.honglv.ui.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.Carousel;
import com.yoyiyi.honglv.bean.Recommend;
import com.yoyiyi.honglv.bean.entity.ImageEntity;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.adapter.sections.HomeBannerSection;
import com.yoyiyi.honglv.ui.adapter.sections.HomeRecommendSection;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;
import com.yoyiyi.honglv.ui.widget.sectioned.SectionedRecyclerViewAdapter;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 主界面
 * Created by yoyiyi on 2016/10/19.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String HOME_INDEX = "home_index";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    private boolean isReshing = false;
    private boolean isPrepared = false;
    private SectionedRecyclerViewAdapter mSectionedAdapter;
    //   private boolean hasLoad = false;//是否加载过数据了

    private List<Carousel> mBanners = new ArrayList<>();
    private List<Recommend> mResult = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    public static HomeFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(HOME_INDEX, index);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void finishCreateView(Bundle state) {
        super.finishCreateView(state);
        //  Bundle bundle = getArguments();
        //  if (bundle != null) {
        //      mCurIndex = bundle.getInt(HOME_INDEX);
        //  }
        isPrepared = true;
        loadData();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initSwiperReshLayoutColor();
    }

    //设置颜色
    private void initSwiperReshLayoutColor() {
        mRefresh.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
    }

    @Override
    protected void loadData() {
        if (!isPrepared) return;
        //请求数据
        requestData();
        initRecylcerView(); //完成数据填充
        isPrepared = false;//加载完成
    }

    private void requestData() {
        setReshing(true);
        mRefresh.post(() -> {
                    doHttpConnection();
                }
        );

    }

    private void clearData() {
        // hasLoad = false;
        mBanners.clear();
        mResult.clear();
        //必须调用否则会出现重复的布局
        mSectionedAdapter.removeAllSections();
    }


    //结束加载
    private void finishTask() {
        setReshing(false);
        mEmpty.setVisibility(View.GONE);
        mSectionedAdapter.addSection(new HomeBannerSection(mBanners));
        /// }
        for (Recommend rd : mResult) {
            HomeRecommendSection homeRecommendSection =
                    new HomeRecommendSection(getActivity(),
                            rd.getTab().getTitle(),
                            rd.getTab().getUrl(),
                            rd.getList());
            mSectionedAdapter.addSection(homeRecommendSection);
        }


        //加载结束
        mSectionedAdapter.notifyDataSetChanged();
        mRefresh.setOnRefreshListener(this);


    }

    private void showEmptyView() {
        //增加加载错误空布局
        //加载结束
        setReshing(false);
        mEmpty.setVisibility(View.VISIBLE);
        mEmpty.setEmptyTv("加载错误啦！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
        mEmpty.setOnItemClickLisener(() -> {
            setReshing(true);
            doHttpConnection();
        });
        TDevice.showMessage(mRecycler, "数据加载失败,请重新加载或者检查网络是否链接");
    }

    /**
     * 设置是否在加载
     *
     * @param isReshing
     */
    public void setReshing(boolean isReshing) {
        this.isReshing = isReshing;
        mRefresh.setRefreshing(isReshing);
    }

    private void initRecylcerView() {
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        // 添加多个布局
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mSectionedAdapter);
        setRecycleNoScroll();


    }

    //设置正在加载不能滑动滑动
    private void setRecycleNoScroll() {
        mRecycler.setOnTouchListener((v, event) -> isReshing);
    }


    private void doHttpConnection() {
        mBanners.clear();
        HttpManager
                .getHttpManager()
                .getHttpService()
                .getCarousel()
                .compose(this.bindToLifecycle())
                .flatMap(new Func1<List<Carousel>, Observable<List<Recommend>>>() {
                    @Override
                    public Observable<List<Recommend>> call(List<Carousel> carousels) {
                        if (carousels.size() > 0 && carousels != null) {
                            mBanners.addAll(carousels);
                        } else {
                            mBanners = ImageEntity.getCarousels();
                            Logger.d(mBanners.size() + "ssssssss");
                        }
                        return HttpManager
                                .getHttpManager()
                                .getHttpService()
                                .getRecommend();
                    }
                })
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommends -> {
                    mResult.clear();
                    if (recommends.size() > 0 && recommends != null) {
                        mResult.addAll(recommends);
                    }

                    finishTask();
                }, e -> {
                    //  Logger.e("请求失败", "");
                    showEmptyView();
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        clearData();
        doHttpConnection();
    }
}
