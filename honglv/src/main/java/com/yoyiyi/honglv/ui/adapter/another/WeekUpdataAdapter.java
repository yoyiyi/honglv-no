package com.yoyiyi.honglv.ui.adapter.another;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.WeekUpdate;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/26.
 */
public class WeekUpdataAdapter extends BaseQuickAdapter<WeekUpdate.ListBean> {

    public WeekUpdataAdapter(List<WeekUpdate.ListBean> data) {
        super(R.layout.item_rank, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, WeekUpdate.ListBean listBean) {
        holder.setText(R.id.name, listBean.getName())
                .setText(R.id.list, listBean.getUpdateDate().trim());
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", listBean.getName());
            bundle.putString("url", listBean.getUrl());
          //  TDevice.launch(mContext, bundle);
            TDevice.launchBangumiDetail(mContext,bundle);

        });
    }
}
