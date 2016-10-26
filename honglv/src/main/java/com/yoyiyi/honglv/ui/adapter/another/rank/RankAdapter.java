package com.yoyiyi.honglv.ui.adapter.another.rank;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.Ranking;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class RankAdapter extends BaseQuickAdapter<Ranking.ListBean> {

    public RankAdapter(List<Ranking.ListBean> data) {
        super(R.layout.item_rank, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Ranking.ListBean listBeen) {
        int pos = viewHolder.getAdapterPosition();
        viewHolder.setText(R.id.name, pos + "、" + listBeen.getName())
                .setText(R.id.list, listBeen.getEpisode());
        viewHolder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", listBeen.getName());
            bundle.putString("url", listBeen.getUrl());
            TDevice.launch(mContext, bundle);
        });
    }
}
