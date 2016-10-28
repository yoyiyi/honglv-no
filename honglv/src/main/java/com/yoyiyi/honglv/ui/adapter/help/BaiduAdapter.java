package com.yoyiyi.honglv.ui.adapter.help;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.BangumiDetail;

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
        holder.itemView.setOnClickListener(v -> {
            //TODO 百度网盘 浏览器

        });
    }
}
