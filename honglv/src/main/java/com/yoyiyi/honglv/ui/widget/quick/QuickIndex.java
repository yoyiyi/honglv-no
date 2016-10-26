package com.yoyiyi.honglv.ui.widget.quick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yoyiyi on 2016/10/25.
 */
public class QuickIndex extends View {

    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    private Paint mPaint;
    private float mMeasuredWidth;
    private float mCellHeight;
    private int mDownY;
    private OnTouchListener mListener;
    private int mIndex;

    public QuickIndex(Context context) {
        super(context);
        init();
    }

    public QuickIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //消除锯齿
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(27);
        mPaint.setTextAlign(Paint.Align.CENTER);//中心点绘制
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mMeasuredWidth = getMeasuredWidth();
        //
        mCellHeight = getMeasuredHeight() / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = mMeasuredWidth / 2;
        //格子高度一半+文本自身一半+索引*格子个数
        for (int i = 0; i < indexArr.length; i++) {
            float y = mCellHeight / 2 + getTextHeight(indexArr[i]) / 2 +
                    i * mCellHeight;
            mPaint.setColor(lastIndex == i ? Color.BLACK : Color.WHITE);
            canvas.drawText(indexArr[i], x, y, mPaint);
        }
    }

    //获取文本高度方法
    private int getTextHeight(String text) {
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    private int lastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //触摸点的坐标/ 格子高度 cellHeight
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mDownY = (int) event.getY();
                mIndex = (int) (mDownY / mCellHeight);
                if (lastIndex != mIndex
                        && mIndex >= 0
                        && mIndex < 26
                        && mListener != null) {
                    // System.out.println(indexArr[mIndex]);
                    mListener.onTouch(indexArr[mIndex]);
                }
                lastIndex = mIndex;
                break;
            case MotionEvent.ACTION_UP:
               /* if (mListener != null) {
                    mListener.onTouch(indexArr[mIndex]);
                }*/
                lastIndex = -1;
                break;
        }
        invalidate();//重绘
        return true;
    }

    public interface OnTouchListener {
        void onTouch(String text);
    }

    public void setOnTouchListener(OnTouchListener listener) {
        this.mListener = listener;
    }
}
