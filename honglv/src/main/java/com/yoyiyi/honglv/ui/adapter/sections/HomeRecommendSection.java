package com.yoyiyi.honglv.ui.adapter.sections;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.Recommend;
import com.yoyiyi.honglv.ui.widget.sectioned.StatelessSection;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class HomeRecommendSection extends StatelessSection {


    private List<Recommend.ListBean> mRecommends;
    private Context mContext;
    private String mTitle;
    protected String mUrl;
    private static final int[] img = {
            R.drawable.ic_new_home_1,
            R.drawable.ic_new_home_2,
            R.drawable.ic_new_home_3,
            R.drawable.ic_new_home_4,
    };

    public HomeRecommendSection(Context context, String title,
                                String url,
                                List<Recommend.ListBean> recommends) {
        super(R.layout.layout_home_header, R.layout.layout_home_body);
        mContext = context;
        mRecommends = recommends;
        this.mTitle = title;
        this.mUrl = url;
    }

    @Override
    public int getContentItemsTotal() {
        return mRecommends.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new BodyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        BodyViewHolder bodyViewHolder = (BodyViewHolder) holder;
        Recommend.ListBean listBean = mRecommends
                .get(position);
        Glide.with(mContext)
                .load(Uri.parse(listBean.getImg()))
                //  .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.placeholder()//占位资源
                .dontAnimate()
                .into(bodyViewHolder.mIvBodyConver);
        bodyViewHolder.mTvBodyCount.setText(listBean.getUpdate());
        bodyViewHolder.mTvBodyTitle.setText(listBean.getName());
        bodyViewHolder.mCardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", listBean.getName());
            bundle.putString("url", listBean.getUrl());
            // TDevice.launch(mContext, bundle);
            TDevice.launchBangumiDetail(mContext, bundle);

        });

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        //headerViewHolder.mTvHomeTitle.setText();
        headerViewHolder.mTvHomeTitle.setText(mTitle);
        setTypeIcon(headerViewHolder);
        headerViewHolder.mRlHome.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", mTitle);
            bundle.putString("url", mUrl);
            //TDevice.launch(mContext, bundle);
            TDevice.launchBangumiDetail(mContext, bundle);
        });
    }

    private void setTypeIcon(HeaderViewHolder holder) {
        switch (mTitle) {
            case "新增连载动漫":
                holder.mIvHomeTitle.setImageResource(img[0]);
                break;
            case "新增完结动漫":
                holder.mIvHomeTitle.setImageResource(img[1]);
                break;
            case "推荐连载动漫":
                holder.mIvHomeTitle.setImageResource(img[2]);
                break;
            case "推荐完结动漫":
                holder.mIvHomeTitle.setImageResource(img[3]);
                break;
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_home_title)
        ImageView mIvHomeTitle;
        @BindView(R.id.tv_home_title)
        TextView mTvHomeTitle;
        @BindView(R.id.rl_home)
        RelativeLayout mRlHome;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class BodyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_body_conver)
        ImageView mIvBodyConver;
        @BindView(R.id.tv_body_count)
        TextView mTvBodyCount;
        @BindView(R.id.tv_body_title)
        TextView mTvBodyTitle;
        @BindView(R.id.card)
        CardView mCardView;

        public BodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
