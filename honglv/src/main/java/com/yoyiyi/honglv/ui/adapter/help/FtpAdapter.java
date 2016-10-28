package com.yoyiyi.honglv.ui.adapter.help;

import android.app.AlertDialog;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.BangumiDetail;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/28.
 */
public class FtpAdapter extends BaseQuickAdapter<BangumiDetail.DownloadBean.ListBean> {


    public FtpAdapter(List<BangumiDetail.DownloadBean.ListBean> data) {
        super(R.layout.item_help, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BangumiDetail.DownloadBean.ListBean listBean) {
        holder.setText(R.id.help, listBean.getName());
        holder.setOnClickListener(R.id.help, v -> {
            //TODO 下载界面
            //弹窗
            Logger.d(listBean.getName());
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setIcon(R.drawable.ic_me);
            builder.setTitle("是否下载该集");
            builder.setPositiveButton("是", (dialog, which) -> {

            });
            builder.setNegativeButton("否", null);
            builder.show();

        });
    }

    private OnItemCheckListener mListener;

    public interface OnItemCheckListener {
        void selectCheck(int pos, String title, String url);
    }

    public void setOnItemCheckListener(OnItemCheckListener listener) {
        this.mListener = listener;
    }
}
