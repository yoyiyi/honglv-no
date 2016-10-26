package com.yoyiyi.honglv.ui.fragment.explore;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class ExploreFragment extends BaseFragment {

    private static final String EXPLORE_INDEX = "explore_index";
 //   @BindView(R.id.toolbar)
 //   Toolbar mToolbar;
    @BindView(R.id.search_back)
    ImageView mSearchBack;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_text_clear)
    ImageView mSearchTextClear;
    @BindView(R.id.search_img)
    ImageView mSearchImg;
   // @BindView(R.id.search_view)
   // MaterialSearchView mSearchView;
    @BindView(R.id.search_card_view)
    CardView mSearchCardView;

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
    protected void initWidget(View root) {
    //    initSearchView();
    }

   /* //初始化SearchView
    protected void initSearchView() {
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
       // mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }*/
}
