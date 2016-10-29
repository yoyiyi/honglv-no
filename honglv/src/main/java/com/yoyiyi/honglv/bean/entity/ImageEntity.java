package com.yoyiyi.honglv.bean.entity;

import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.bean.Carousel;

import java.util.ArrayList;

/**
 * Created by yoyiyi on 2016/10/29.
 */
public class ImageEntity {
    private static ArrayList<Carousel> mCarousels = new ArrayList<>();

    public static ArrayList<Carousel> getCarousels() {
        mCarousels.add(new Carousel("夏日祭", R.drawable.car1,null));
        mCarousels.add(new Carousel("阴阳师", R.drawable.car2,null));
        mCarousels.add(new Carousel("妖刀地", R.drawable.car3,null));
        mCarousels.add(new Carousel("螺旋境界线", R.drawable.car4,null));
        return mCarousels;
    }
}
