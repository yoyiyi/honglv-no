package com.yoyiyi.honglv.ui.adapter.another;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.RecentUpdate;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/27.
 */
public class RecentAdapter extends BaseQuickAdapter<RecentUpdate> {

    public RecentAdapter(List<RecentUpdate> data) {
        super(R.layout.item_recent, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, RecentUpdate recentUpdate) {
        holder.setText(R.id.name, recentUpdate.getSort() + "、" + recentUpdate.getName())
                .setText(R.id.type, recentUpdate.getType())
                .setText(R.id.time, recentUpdate.getTime())
                .setText(R.id.esp, recentUpdate.getEpisode());
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", recentUpdate.getName());
            bundle.putString("url", recentUpdate.getUrl());
           // TDevice.launch(mContext, bundle);
            TDevice.launchBangumiDetail(mContext,bundle);

        });


    }
}
