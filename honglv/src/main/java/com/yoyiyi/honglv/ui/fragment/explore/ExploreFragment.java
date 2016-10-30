package com.yoyiyi.honglv.ui.fragment.explore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;
import com.yoyiyi.honglv.bean.TypeBean;
import com.yoyiyi.honglv.ui.activity.another.AnotherActivity;
import com.yoyiyi.honglv.utils.KeyBoardUtil;
import com.yoyiyi.honglv.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 发现
 * Created by yoyiyi on 2016/10/19.
 */
public class ExploreFragment extends BaseFragment{

    private static final String EXPLORE_INDEX = "explore_index";
    @BindView(R.id.search_back)
    ImageView mSearchBack;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_text_clear)
    ImageView mSearchTextClear;
    @BindView(R.id.search_img)
    ImageView mSearchImg;
    @BindView(R.id.search_card_view)
    CardView mSearchCardView;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private ArrayList<TypeBean> mTypeBean = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_layout;
    }


    public static ExploreFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXPLORE_INDEX, index);
        ExploreFragment fragment = new ExploreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void finishCreateView(Bundle state) {
        if (!isPrepared) return;
        loadData();
        mSearchEdit.setOnClickListener(v->KeyBoardUtil.openKeybord(mSearchEdit,getActivity()));
        isPrepared = false;
    }

    @Override
    protected void loadData() {
        initRecycler();
        initSearchEdit();
    }

    @Override
    protected void initWidget(View root) {
        //    initSearchView();
        initTypeBean();
    }

    private void initSearchEdit() {
        mSearchEdit.setFocusable(true);
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                KeyBoardUtil.openKeybord(mSearchEdit, getActivity());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //initEdit();
                if (count == 0) {
                    mSearchTextClear.setVisibility(View.GONE);
                } else {
                    mSearchTextClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        KeyBoardUtil.openKeybord(mSearchEdit, getActivity());
        mSearchImg.setOnClickListener(v -> onSearch());
        mSearchTextClear.setOnClickListener(v -> mSearchEdit.setText(""));
        mSearchEdit.setOnClickListener(v->KeyBoardUtil.openKeybord(mSearchEdit,getActivity()));
        mSearchEdit.setOnEditorActionListener((v, id, e) -> {
            //判断输入法按钮  搜索
            if (id == EditorInfo.IME_ACTION_SEARCH
                    || id == EditorInfo.IME_ACTION_UNSPECIFIED) {
                onSearch();
                return true;
            }
            return false;
        });
    }

    private void onSearch() {
        if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
            TDevice.showToast("关键词不能为空");
            return;
        }
        KeyBoardUtil.closeKeybord(mSearchEdit, getActivity());
        Intent intent = new Intent(getActivity(), AnotherActivity.class);
        intent.putExtra("key", mSearchEdit.getText().toString());
        intent.putExtra("type", "SearchFragment");
        startActivity(intent);
    }

    private void initRecycler() {
        TypeAdapter typeAdapter = new TypeAdapter(mTypeBean);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycler.setAdapter(typeAdapter);
        typeAdapter.setNewData(mTypeBean);

    }

    private void initTypeBean() {
        mTypeBean.add(new TypeBean("全部", null, R.drawable.a1));
        mTypeBean.add(new TypeBean("TV番组", 1, R.drawable.a2));
        mTypeBean.add(new TypeBean("OVA", 2, R.drawable.a3));
        mTypeBean.add(new TypeBean("OVD", 3, R.drawable.a4));
        mTypeBean.add(new TypeBean("剧场版", 4, R.drawable.a5));
        mTypeBean.add(new TypeBean("特别版", 5, R.drawable.a6));
        mTypeBean.add(new TypeBean("真人版", 6, R.drawable.a7));
        mTypeBean.add(new TypeBean("动画版", 7, R.drawable.a8));
        mTypeBean.add(new TypeBean("其他", 100, R.drawable.a9));

    }


    class TypeAdapter extends BaseQuickAdapter<TypeBean> {
        public TypeAdapter(List<TypeBean> data) {
            super(R.layout.item_explore, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, TypeBean typeBean) {
            holder.setImageResource(R.id.image, typeBean.getImg())
                    .setText(R.id.type, typeBean.getName());
            holder.itemView.setOnClickListener(v -> {
                //TODO 跳转到查找页面
                Intent intent = new Intent(getActivity(), AnotherActivity.class);
                intent.putExtra("title", typeBean.getName());
                intent.putExtra("num", typeBean.getNum());
                intent.putExtra("type", "ExploreFragment");
                startActivity(intent);
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchEdit.setText("");
    }

}
