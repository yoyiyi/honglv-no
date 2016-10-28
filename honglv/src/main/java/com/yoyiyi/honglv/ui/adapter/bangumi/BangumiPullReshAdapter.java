package com.yoyiyi.honglv.ui.adapter.bangumi;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.AnimaList;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;


/**
 * Created by yoyiyi on 2016/10/21.
 */
public class BangumiPullReshAdapter extends BaseQuickAdapter<AnimaList.ListBean> {
   // private Context mContext;

    public BangumiPullReshAdapter(List<AnimaList.ListBean> animas) {
        super(R.layout.item_bangumi_recycler, animas);
       // mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, AnimaList.ListBean listBean) {
        holder.setText(R.id.m_update, "状态：" + listBean.getEpisode())
                .setText(R.id.m_mupdate, listBean.getEpisode())
                .setText(R.id.m_time, "时间：" + listBean.getUpdateTime())
                .setText(R.id.m_down, "下载：" + listBean.getDownloadCount())
                .setText(R.id.m_name, "番名：" + listBean.getName())
                .addOnClickListener(R.id.m_card);
        Glide.with(mContext)
                .load(Uri.parse(listBean.getImg()))
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ico_user_default)//占位资源
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.m_cover));
       holder.itemView.setOnClickListener(v->{
           Bundle bundle = new Bundle();
           bundle.putString("title",listBean.getName());
           bundle.putString("url",listBean.getUrl());
         //  TDevice.launch(mContext, bundle);
           TDevice.launchBangumiDetail(mContext,bundle);

       });
    }
}
