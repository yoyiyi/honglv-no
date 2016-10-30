package com.yoyiyi.honglv.ui.widget.banner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.Carousel;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class BannerView extends FrameLayout implements BannerAdapter.ViewPagerOnItemClickListener {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.points)
    LinearLayout mPoints;
    private Context mContext;
    private List<ImageView> iv;
    private List<Carousel> mCarousels;
    private int selectRes = R.drawable.shape_dots_select;//选中
    private int defaultRes = R.drawable.shape_dots_default;//非选中
    private long delayTime = 10;//设置轮播时间
    private boolean isStopScroll = false;
    CompositeSubscription compositeSubscription;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_banner, this, true);
        ButterKnife.bind(this);
        iv = new ArrayList<>();
    }

    //设置轮播时间
    public BannerView setDelayTime(long delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    //设置轮播资源
    public void setPointRes(int defaultRes, int selectRes) {
        this.defaultRes = defaultRes;
        this.selectRes = selectRes;
    }

    //设置轮播
    public void roll(List<Carousel> carousel) {
        destroy();
        if (carousel.size() == 0 || carousel == null) {
            this.setVisibility(GONE);
            return;
        }
        mCarousels = carousel;
        int pointSize;
        pointSize = mCarousels.size();
        if (pointSize == 1) {//只有一张图片时候
            //不需要轮播
            isStopScroll = true;
            // stopScroll();
            //设置图片 设置标题
        }
        //判断是否清空指示器
        if (mPoints.getChildCount() != 0) {
            //清空所有指示器
            mPoints.removeAllViews();
            mPoints.removeAllViewsInLayout();
        }
        //初始化与个数相同的指示器点
        for (int i = 0; i < pointSize; i++) {
            View dot = new View(mContext);
            dot.setBackgroundResource(defaultRes);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) TDevice.dp2px(5),
                    (int) TDevice.dp2px(5));
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            //设置不可用
            dot.setEnabled(false);
            //添加视图
            mPoints.addView(dot);
        }
        //设置选中时候的背景
        mPoints.getChildAt(0).setBackgroundResource(selectRes);

        for (int i = 0; i < mCarousels.size(); i++) {
            ImageView mImageView = new ImageView(mContext);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (mCarousels.get(i).getImg() == null) {
                mImageView.setImageResource(mCarousels.get(i).getImgId());
           } else {
                Glide.with(mContext)
                        .load(mCarousels.get(i).getImg())
                        //.centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mImageView);
            }
            iv.add(mImageView);
        }
        //监听图片轮播，改变指示器状态
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                pos = pos % pointSize;
                for (int i = 0; i < mPoints.getChildCount(); i++) {
                    mPoints.getChildAt(i).setBackgroundResource(defaultRes);
                }
                mPoints.getChildAt(pos).setBackgroundResource(selectRes);
                mTitle.setText(mCarousels.get(pos).getTitle());
                // Logger.d(mCarousels.get(pos).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll) {
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopScroll();
                        //解绑
                        compositeSubscription.unsubscribe();
                        break;
                }
            }
        });

        BannerAdapter bannerAdapter = new BannerAdapter(iv);
        mViewPager.setAdapter(bannerAdapter);
        // mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(5000);
        bannerAdapter.notifyDataSetChanged();
        bannerAdapter.setViewPagerOnItemClickListener(this);
        //图片开始轮播
        startScroll();

    }

    private void stopScroll() {
        isStopScroll = true;
    }


    private void destroy() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    private void startScroll() {
        compositeSubscription = new CompositeSubscription();
        isStopScroll = false;
        Subscription subscription = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> {
                    if (isStopScroll) {
                        return;
                    }
                    isStopScroll = true;
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            position = mCarousels.size() - 1;
        } else {
            position -= 1;
        }
        //设置图片点击事件
        if (mCarousels.get(position).getUrl() == null) {

        } else {
            Bundle bundle = new Bundle();
            bundle.putString("title", mCarousels.get(position).getTitle());
            bundle.putString("url", mCarousels.get(position).getUrl());
            //TDevice.launch( mContext, bundle);
            TDevice.launchNewsDetail(mContext, bundle);
        }
        // BrowserActivity.launch((Activity) context, bannerList.get(position).link, bannerList.get(position).title);
    }
}
