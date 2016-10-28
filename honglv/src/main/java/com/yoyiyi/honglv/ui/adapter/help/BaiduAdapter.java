package com.yoyiyi.honglv.ui.adapter.help;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.BangumiDetail;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/28.
 */
public class BaiduAdapter extends BaseQuickAdapter<BangumiDetail.DownloadBean.ListBean> {
    public BaiduAdapter(List<BangumiDetail.DownloadBean.ListBean> data) {
        super(R.layout.item_help, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BangumiDetail.DownloadBean.ListBean listBean) {
        holder.setText(R.id.help, listBean.getName());
       /* holder.itemView.setOnClickListener(v -> {
            //TODO 百度网盘 浏览器
            Bundle bundle = new Bundle();
            bundle.putString("title", listBean.getName());
            bundle.putString("url", listBean.getUrl());
            TDevice.launch(mContext, bundle);
        });*/
        holder.setOnClickListener(R.id.help,v->{
            Bundle bundle = new Bundle();
            bundle.putString("title", listBean.getName());
            bundle.putString("url", listBean.getUrl());
            TDevice.launch(mContext, bundle);
        });
    }
}
