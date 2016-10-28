package com.yoyiyi.honglv.ui.adapter.news;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.News;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/26.
 */
public class NewsAdapter extends BaseQuickAdapter<News.ListBean> {

    public NewsAdapter(List<News.ListBean> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, News.ListBean listBean) {
        holder.setText(R.id.title, listBean.getTitle())
                .setText(R.id.des, listBean.getDescription())
                .setText(R.id.time, listBean.getTime());
        Glide.with(mContext)
                .load(Uri.parse(listBean.getImg()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ico_user_default)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.cover));
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", listBean.getTitle());
            bundle.putString("url", listBean.getUrl());
            TDevice.launch(mContext, bundle);
           // TDevice.launchNewsDetail(mContext, bundle);
        });
    }
}
