package com.yoyiyi.honglv.ui.fragment.another.rank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.Ranking;
import com.yoyiyi.honglv.ui.activity.TopicActivity;
import com.yoyiyi.honglv.ui.adapter.another.rank.RankAdapter;
import com.yoyiyi.honglv.ui.anim.BaseAnimator;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class RankDetailFragment extends BaseFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    private static Ranking mRanking;
    private RankAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rank;
    }

    public static RankDetailFragment newInstance(int i, Ranking rank) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("DATA", rank);
        // mRanking = rank;
        RankDetailFragment rankFragment = new RankDetailFragment();
        rankFragment.setArguments(bundle);
        return rankFragment;

    }

    @Override
    protected void finishCreateView(Bundle state) {
        mRanking = getArguments().getParcelable("DATA");
        // Logger.d(mRanking);
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared && mRanking == null) return;
        initAdapter();

    }


    private void initAdapter() {
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RankAdapter(new ArrayList<>());
        // addHeader();
        mRecycle.setAdapter(mAdapter);
        //判断时候有
        if (null == mRanking.getUrl()) {
            // 不加头部
        } else {
            // 加头部
            addHeader();
        }
        mAdapter.setNewData(mRanking.getList());
    }

    private void addHeader() {
        View view = View.inflate(getActivity(), R.layout.item_ranking_header, null);
        mAdapter.addHeaderView(view);
        mAdapter.openLoadAnimation(new BaseAnimator());
        view.setOnClickListener(v -> {
            //TODO 排行查看详情

            Logger.d("url" + mRanking.getUrl());
            if (mRanking.getUrl().contains("http://")) {
                Bundle bundle = new Bundle();
                bundle.putString("title", mRanking.getTab());
                bundle.putString("url", mRanking.getUrl());
                TDevice.launch(getActivity(), bundle);
            } else {
                //TODO 跳转到TopicActivity
                Intent intent = new Intent(getActivity(), TopicActivity.class);
                intent.putExtra("title", mRanking.getTab());
                intent.putExtra("url", "http://www.hltm.tv" + mRanking.getUrl());
                startActivity(intent);
            }
        });
    }


}
