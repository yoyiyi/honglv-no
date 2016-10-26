package com.yoyiyi.honglv.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yoyiyi on 2016/10/21.
 */
public class AutoScrollText extends TextView {

    public AutoScrollText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoScrollText(Context context) {
        super(context);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
