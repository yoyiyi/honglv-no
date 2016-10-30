package com.yoyiyi.honglv.ui.fragment.help;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.BangumiDetail;
import com.yoyiyi.honglv.ui.adapter.help.CiliAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 磁力链接
 * Created by yoyiyi on 2016/10/28.
 */
public class CiliFragment extends BaseFragment {
    private BangumiDetail.DownloadBean data;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private CiliAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_help;
    }

    public static CiliFragment newInstance(BangumiDetail.DownloadBean data) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("down", data);
        CiliFragment fragment = new CiliFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected void finishCreateView(Bundle state) {
        data = getArguments().getParcelable("down");
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared && !isVisible) return;
        initAdapter();
        isPrepared = false;
    }

    private void initAdapter() {
        mAdapter = new CiliAdapter(new ArrayList<>());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setNewData(data.getList());

    }


}
