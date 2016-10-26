package com.yoyiyi.honglv.ui.fragment.another.week;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.WeekUpdate;
import com.yoyiyi.honglv.ui.adapter.another.WeekUpdataAdapter;
import com.yoyiyi.honglv.ui.anim.BaseAnimator;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class WeekDetailFragment extends BaseFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    private static WeekUpdate mWeek;
    private WeekUpdataAdapter mAdapter;
    private TextView mTotal;
    private SpannableString mSpannableString;
    private int mTotalCount;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rank;
    }

    public static WeekDetailFragment newInstance(int i, WeekUpdate list) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("DATA", list);
        WeekDetailFragment fragment = new WeekDetailFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected void finishCreateView(Bundle state) {
        mWeek = getArguments().getParcelable("DATA");
        isPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared && mWeek == null) return;
        initAdapter();
        setTextType();

    }


    private void initAdapter() {
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WeekUpdataAdapter(new ArrayList<>());
        mRecycle.setAdapter(mAdapter);
        addHeader();
        mAdapter.setNewData(mWeek.getList());
        mTotalCount = mWeek.getList().size();
    }

    private void addHeader() {
        View view = View.inflate(getActivity(), R.layout.item_bangumi_header, null);
        mAdapter.addHeaderView(view);
        mTotal = (TextView) view.findViewById(R.id.total);
        mAdapter.openLoadAnimation(new BaseAnimator());
    }

    /**
     * 设置文字样式
     */
    private void setTextType() {
        mTotal.setText("");
        String text = mTotalCount + "";
        mSpannableString = new SpannableString("一共更新了" + text + "部番剧");
        mSpannableString.setSpan(
                new ForegroundColorSpan(Color.RED), 5, 5 + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTotal.append(mSpannableString);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTotalCount = 0;
    }
}
