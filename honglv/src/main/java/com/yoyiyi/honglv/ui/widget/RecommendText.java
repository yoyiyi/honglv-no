package com.yoyiyi.honglv.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.utils.TDevice;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/27.
 */
public class RecommendText extends FrameLayout {

    @BindView(R.id.sort)
    TextView mSort;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.url)
    TextView mUrl;
    private String mTitle;
    private String murl;
    private Context mContext;

    public RecommendText(Context context) {
        this(context, null);
    }

    public RecommendText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecommendText(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        mContext = context;

        View view = View.inflate(context, R.layout.layout_recommend, this);
        ButterKnife.bind(this, view);
        this.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", mName.getText().toString());
            bundle.putString("url", mUrl.getText().toString());
           // Logger.d(mTitle);
            TDevice.launchBangumiDetail(mContext, bundle);
        });

    }

    public void setInfo(String name, String sort, String count, String url) {
        mName.setText(name);
        mSort.setText(sort);
        mCount.setText(count);
        mUrl.setText(url);
    }

    public String setTitle(String name) {
        return name;
    }

    public String setUrl(String url) {
        return url;
    }

    public void setTitleAndUrl(String title, String url) {
        this.mTitle = title;
        this.murl = url;

    }


    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onRecommend();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

    //  @Override
    //public void setOnClickListener(OnClickListener l) {
    //    Bundle bundle = new Bundle();
    //     bundle.putString("title", mTitle);
    //     bundle.putString("url", murl);
    //     TDevice.launchBangumiDetail(mContext, bundle);
//
    //  }

}
