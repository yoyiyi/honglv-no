package com.yoyiyi.honglv.ui.widget.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yoyiyi.honglv.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/20.
 */
public class EmptyLayout extends LinearLayout {
    @BindView(R.id.empty_iv)
    ImageView mEmptyIv;
    @BindView(R.id.empty_tv)
    TextView mEmptyTv;
    @BindView(R.id.empty_bt)
    Button mEmptyBt;
    private Context mContext;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_empty, this, true);
        ButterKnife.bind(this);
        this.setVisibility(GONE);

    }

    public void setEmptyTv(String title) {
        mEmptyTv.setText(title);
    }

    public void setEmptyIv(int resId) {
        mEmptyIv.setBackgroundResource(resId);
    }

    public void setView(int id) {
        this.setVisibility(id);
    }

    public void HideButton() {
        mEmptyBt.setVisibility(GONE);
    }

    public void setOnClick() {
        mEmptyBt.setOnClickListener(v -> {
            if (mListener != null) {
                this.mListener.onReload();
            }
        });
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onReload();
    }

    public void setOnItemClickLisener(OnItemClickListener listener) {
        this.mListener = listener;
        setOnClick();
    }
}
