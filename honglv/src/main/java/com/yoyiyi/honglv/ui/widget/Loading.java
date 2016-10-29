package com.yoyiyi.honglv.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoyiyi.honglv.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoyiyi on 2016/10/21.
 */
public class Loading extends RelativeLayout implements View.OnClickListener {

    private static final int TYPE_NOT_DATA = 5;
    @BindView(R.id.c_progress)
    net.qiujuer.genius.ui.widget.Loading mCProgress;
    @BindView(R.id.c_loading)
    TextView mCLoading;
    private Context mContext;

    private static final int TYPE_HIDE = 1;//隐藏
    private static final int TYPE_ERROR = 2;//加载错误
    private static final int TYPE_NO_DATA = 3;//没有更多数据
    private static final int TYPE_LOADING = 4;//正在加载
    private int mCurrent;
    private boolean isEnable;

    public Loading(Context context) {
        this(context, null);
    }

    public Loading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Loading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View.inflate(context, R.layout.custom_loadding, this);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        setOnClickListener(this);
        //startProgress();
        dismiss();
    }

    public int getCurrent() {
        return mCurrent;
    }

    public void setType(int type) {
        setVisibility(VISIBLE);
        switch (type) {
            case TYPE_HIDE:
                dismiss();
                break;
            case TYPE_ERROR:
                mCurrent = TYPE_ERROR;
                setMsg("发生未知错误!!");
                isEnable = true;
                mCProgress.setVisibility(GONE);
                break;
            case TYPE_LOADING:
                mCurrent = TYPE_LOADING;
                setMsg("少女祈愿中...");
                startProgress();
                isEnable = false;
                break;
            case TYPE_NO_DATA:
                mCurrent = TYPE_ERROR;
                setMsg("没有更多数据!!");
                mCProgress.stop();
                mCProgress.setVisibility(GONE);
                isEnable = false;
                break;
            case TYPE_NOT_DATA:
                mCurrent = TYPE_ERROR;
                setMsg("没有数据!!");
                mCProgress.stop();
                mCProgress.setVisibility(GONE);
                isEnable = false;
                break;
        }

    }

    public boolean isLoading() {
        return mCurrent == TYPE_LOADING;
    }

    public boolean isError() {
        return mCurrent == TYPE_ERROR;
    }

    private void startProgress() {
        mCProgress.setAutoRun(true);
        mCProgress.start();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null && isEnable) {
            this.mListener.reLoad();
        }
    }

    public void dismiss() {
        mCurrent = TYPE_HIDE;
        setVisibility(GONE);
    }

    public void setMsg(String msg) {
        mCLoading.setText(msg);
    }

    private OnItemCLickListener mListener;

    public interface OnItemCLickListener {
        void reLoad();
    }

    public void setOnItemClick(OnItemCLickListener listener) {
        this.mListener = listener;
    }

}
