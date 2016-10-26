package com.yoyiyi.honglv.ui.adapter.topic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.TopicList;
import com.yoyiyi.honglv.ui.activity.TopicActivity;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/23.
 */
public class TopicListAdapter extends BaseQuickAdapter<TopicList> {
    private Context mContext;

    public TopicListAdapter(List<TopicList> topicList, Context context) {
        super(R.layout.item_topic_recycler, topicList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, TopicList topicList) {
        holder.setText(R.id.t_title, topicList.getTitle());
        Glide.with(mContext)
                .load(Uri.parse(topicList.getImg()))
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.placeholder(R.drawable.ico_user_default)//占位资源
                // .dontAnimate()
                .into((ImageView) holder.getView(R.id.t_cover));
        holder.itemView.setOnClickListener(v -> {
            //  Bundle bundle = new Bundle();
            //  bundle.putString("title", topicList.getTitle());
            //  bundle.putString("url", "http://www.hltm.tv" + topicList.getUrl());
            // TDevice.launch(mContext, bundle);
            Intent intent = new Intent(mContext, TopicActivity.class);
            intent.putExtra("url", "http://www.hltm.tv" + topicList.getUrl());
            intent.putExtra("title", topicList.getTitle());
            mContext.startActivity(intent);
        });

    }
}
