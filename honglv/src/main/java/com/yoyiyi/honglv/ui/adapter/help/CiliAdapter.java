package com.yoyiyi.honglv.ui.adapter.help;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.AppConfig;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.BangumiDetail;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/28.
 */
public class CiliAdapter extends BaseQuickAdapter<BangumiDetail.DownloadBean.ListBean> {


    public CiliAdapter(List<BangumiDetail.DownloadBean.ListBean> data) {
        super(R.layout.item_help, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BangumiDetail.DownloadBean.ListBean listBean) {
        holder.setText(R.id.help, listBean.getName());
        holder.setOnClickListener(R.id.help, v -> {
            if (!AppConfig.isPkgInstalled("com.xunlei.downloadprovider")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setIcon(R.drawable.anim_week);
                builder.setTitle("主人的手机没有安装X雷");
                builder.setPositiveButton("先去下X雷", (dia, c) ->
                        TDevice.gotoMarket(mContext, "com.xunlei.downloadprovider")
                );
                builder.setNegativeButton("残忍滴拒绝", null);
                builder.show();
                //TDevice.showToast("弹出框点击不会下载X雷，需要手动");
            }
            //TODO磁力链接
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(listBean.getUrl()));
            intent.addCategory("android.intent.category.DEFAULT");
            mContext.startActivity(intent);
        });
    }
}
