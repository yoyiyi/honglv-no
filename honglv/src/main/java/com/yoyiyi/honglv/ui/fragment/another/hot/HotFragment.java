package com.yoyiyi.honglv.ui.fragment.another.hot;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.HotNew;
import com.yoyiyi.honglv.ui.adapter.another.HotAdapter;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;
import com.yoyiyi.honglv.ui.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yoyiyi on 2016/10/23.
 */
public class HotFragment extends BaseFragment {

    private static final String FAT = "FAT";
    private static List<HotNew.ChildBean> mChildBeen;
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    private HotAdapter mHotAdapter;

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;

    public static HotFragment newInstance(int i, List<HotNew.ChildBean> childBeen) {
        HotFragment hotFragment = new HotFragment();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();
        list.add(childBeen);
        bundle.putParcelableArrayList(FAT, list);
        hotFragment.setArguments(bundle);
        return hotFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_fragment_hot;
    }

    @Override
    protected void initWidget(View root) {

    }

    @Override
    protected void loadData() {
        if (!isPrepared) return;
        //初始化数据
        initRecyclerView();
        //填充数据
        isPrepared = false;

    }

    private void initRecyclerView() {
        GridLayoutManager lay = new GridLayoutManager(getActivity(), 2);
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        // 添加多个布局
        lay.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecycle.setLayoutManager(lay);
        mRecycle.setAdapter(mSectionedRecyclerViewAdapter);
        for (int i = 0; i < mChildBeen.size(); i++) {
            mHotAdapter = new HotAdapter(
                    mChildBeen.get(i).getList(),
                    mChildBeen.get(i).getTab(),
                    mChildBeen.get(i).getOtherList(),
                    getActivity());
            mSectionedRecyclerViewAdapter
                    .addSection(mHotAdapter);

        }
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        // mRecycler.setAdapter(mSectionedRecyclerViewAdapter);
    }

    private void clearData() {

    }

    @Override
    protected void finishCreateView(Bundle state) {
        ArrayList list = getArguments().getParcelableArrayList(FAT);
        Logger.d(list.size());
        mChildBeen = (List<HotNew.ChildBean>) list.get(0);
        isPrepared = true;
        loadData();
    }


}
