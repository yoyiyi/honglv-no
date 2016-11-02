package com.yoyiyi.honglv.ui.adapter.news;

import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.Zhihu;
import com.yoyiyi.honglv.ui.activity.another.NewsAnotherActivity;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/26.
 */
public class NewsAnotherAdapter extends BaseQuickAdapter<Zhihu.StoriesBean> {

    public NewsAnotherAdapter(List<Zhihu.StoriesBean> data) {
        super(R.layout.item_news_ano, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Zhihu.StoriesBean listBean) {
        holder.setText(R.id.title, listBean.getTitle());
                //  .setText(R.id.des, listBean.get())
                //.setText(R.id.time, listBean.getType());
        Glide.with(mContext)
                .load(Uri.parse(listBean.getImages().get(0)))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ico_user_default)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.cover));
        holder.itemView.setOnClickListener(v -> {
            //  TDevice.launch(mContext, bundle);
            // TDevice.launchNewsDetail(mContext, bundle);
            Intent  intent = new Intent(mContext, NewsAnotherActivity.class);
            intent.putExtra("title",listBean.getTitle());
            intent.putExtra("id",listBean.getId());
            mContext.startActivity(intent);
        });
    }
}
