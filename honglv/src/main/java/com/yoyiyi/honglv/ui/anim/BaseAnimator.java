package com.yoyiyi.honglv.ui.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

/**
 * Created by yoyiyi on 2016/10/21.
 */
public class BaseAnimator implements BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {

        return new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 0.6f, 1.3f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 0.6f, 1.3f, 1),
                ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1),
        };
    }
}
