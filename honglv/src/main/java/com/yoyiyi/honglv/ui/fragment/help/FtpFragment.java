package com.yoyiyi.honglv.ui.fragment.help;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.BangumiDetail;
import com.yoyiyi.honglv.ui.adapter.help.FtpAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Ftp下载
 * Created by yoyiyi on 2016/10/28.
 */
public class FtpFragment extends BaseFragment {
    @BindView(R.id.select)
    Button mSelect;
    @BindView(R.id.down)
    Button mDown;
    private BangumiDetail.DownloadBean data;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private FtpAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_help;
    }

    public static FtpFragment newInstance(BangumiDetail.DownloadBean data) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        FtpFragment fragment = new FtpFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected void finishCreateView(Bundle state) {
        data = getArguments().getParcelable("data");
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared && !isVisible) return;
        mDown.setVisibility(View.GONE);
        mSelect.setVisibility(View.GONE);
        initAdapter();
        isPrepared = false;
    }


    private void initAdapter() {
        mAdapter = new FtpAdapter(new ArrayList<>());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setNewData(data.getList());
        mDown.setOnClickListener(v -> {
            mAdapter.setOnItemCheckListener((pos, title, url) -> {
                Toast.makeText(getActivity(), "pos:" + pos + "title:" + title + "url:" + url, Toast.LENGTH_LONG).show();

            });

        });
        mSelect.setOnClickListener(v -> {
            if (mSelect.getText().equals("全选")) {
                mSelect.setText("取消");
            } else {
                mSelect.setText("全选");

            }

        });

    }

}
