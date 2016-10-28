package com.yoyiyi.honglv.ui.adapter.another;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.HotNew;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class HotMoreAdapter extends BaseQuickAdapter<HotNew.ChildBean.OtherListBean> {
    public HotMoreAdapter(List<HotNew.ChildBean.OtherListBean> data) {
        super(R.layout.item_hot_more, data);
    }


    @Override
    protected void convert(BaseViewHolder vh, HotNew.ChildBean.OtherListBean bean) {
        int pos = vh.getAdapterPosition();
        vh.setText(R.id.name, pos + "、" + bean.getName());
        vh.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", bean.getName());
            bundle.putString("url", bean.getUrl());
           // TDevice.launch(mContext, bundle);
            TDevice.launchBangumiDetail(mContext,bundle);

        });

    }


}
