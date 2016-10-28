package com.yoyiyi.honglv.ui.adapter.sections;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.Carousel;
import com.yoyiyi.honglv.ui.widget.banner.BannerView;
import com.yoyiyi.honglv.ui.widget.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页轮播图Section
 */
public class HomeBannerSection extends StatelessSection {

    private List<Carousel> banners = new ArrayList<>();

    public HomeBannerSection(List<Carousel> banners) {
        super(R.layout.layout_home_bannar, R.layout.layout_default);
        this.banners = banners;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new BannerViewHolder(view);
    }

    //头部
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        bannerViewHolder.mBannerView.setDelayTime(5).roll(banners);
       // bannerViewHolder.mBannerView.setOnClickListener(v->{
//
       // });
    }
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }


    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        BannerView mBannerView;
        BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
