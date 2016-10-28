package com.yoyiyi.honglv.ui.adapter.another;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.HotNew;
import com.yoyiyi.honglv.ui.activity.HotMoreActivity;
import com.yoyiyi.honglv.ui.widget.AutoScrollText;
import com.yoyiyi.honglv.ui.widget.sectioned.StatelessSection;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/24.
 */
public class HotAdapter extends StatelessSection {


    private List<HotNew.ChildBean.ListBean> mList;
    private Context mContext;
    private String tab;
    private List<HotNew.ChildBean.OtherListBean> mOtherListBeen;
    private static final String FAT = "fat";

    public HotAdapter(List<HotNew.ChildBean.ListBean> list,
                      String tab,
                      List<HotNew.ChildBean.OtherListBean> beanList,
                      Context context) {
        super(R.layout.item_hot_header, R.layout.layout_home_body);
        mList = list;
        this.tab = tab;
        mContext = context;
        this.mOtherListBeen = beanList;

    }

    @Override
    public int getContentItemsTotal() {
        return mList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewholder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewholder itemViewholder = (ItemViewholder) holder;
        Glide.with(mContext)
                .load(Uri.parse(mList.get(position).getImg()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ico_user_default)//占位资源
                .dontAnimate()
                .into(itemViewholder.mIvBodyConver);
        itemViewholder.mTvBodyCount.setText(mList.get(position).getEpisode());
        itemViewholder.mTvBodyTitle.setText(mList.get(position).getName());
        itemViewholder.mCard.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", mList.get(position).getName());
            bundle.putString("url", mList.get(position).getUrl());
            //TDevice.launch(mContext, bundle);
            TDevice.launchBangumiDetail(mContext,bundle);
            // Logger.d(mList.get(position).getUrl());
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.mTotal.setText(tab);
        headerViewHolder.mACard.setOnClickListener(v -> {
            //  if (mClistener != null) {
            //  this.mClistener.onAnotherList(tab);
            // }
            Bundle bundle = new Bundle();
            bundle.putString("title", tab);
            ArrayList list = new ArrayList();
            list.add(mOtherListBeen);
            bundle.putParcelableArrayList("list", list);
            Intent intent = new Intent(mContext, HotMoreActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });

    }


    static class ItemViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_body_title)
        AutoScrollText mTvBodyTitle;
        @BindView(R.id.iv_body_conver)
        ImageView mIvBodyConver;
        @BindView(R.id.tv_body_count)
        TextView mTvBodyCount;
        @BindView(R.id.card)
        CardView mCard;

        public ItemViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.total)
        TextView mTotal;
        @BindView(R.id.another)
        TextView mAnother;
        @BindView(R.id.a_card)
        CardView mACard;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private CardItemOnClistener mClistener;

    public interface CardItemOnClistener {
        void onAnotherList(String tab);
    }

    public void setOnCardItemOnClistener(CardItemOnClistener clistener) {
        this.mClistener = clistener;
    }
}
