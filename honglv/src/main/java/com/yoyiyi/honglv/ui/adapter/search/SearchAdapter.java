package com.yoyiyi.honglv.ui.adapter.search;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.Search;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;


/**
 * Created by yoyiyi on 2016/10/23.
 */
public class SearchAdapter extends BaseQuickAdapter<Search.ListBean> {
    // private Context mContext;

    public SearchAdapter(List<Search.ListBean> data) {
        super(R.layout.item_bangumi_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Search.ListBean topicDetail) {
        holder.setVisible(R.id.m_local, true)
                .setVisible(R.id.m_type, true)
                .setText(R.id.m_local, "地区：" + topicDetail.getLocal())
                .setText(R.id.m_type, "类型：" + topicDetail.getType())
                .setText(R.id.m_update, "状态：" + topicDetail.getLastest())
                .setText(R.id.m_mupdate, topicDetail.getLastest())
                .setVisible(R.id.m_time, false)
                //.setText(R.id.m_time, "时间：" + topicDetail.get())
                .setText(R.id.m_down, "播放：" + topicDetail.getDownload())
                .setText(R.id.m_name, "番名：" + topicDetail.getName());
        Glide.with(mContext)
                .load(Uri.parse(topicDetail.getImg()))
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
               // .placeholder(R.drawable.ico_user_default)//占位资源
                //.dontAnimate()
                .into((ImageView) holder.getView(R.id.m_cover));
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", topicDetail.getName());
            bundle.putString("url", topicDetail.getUrl());
            // TDevice.launch(mContext, bundle);
            TDevice.launchBangumiDetail(mContext, bundle);

        });
    }
}
