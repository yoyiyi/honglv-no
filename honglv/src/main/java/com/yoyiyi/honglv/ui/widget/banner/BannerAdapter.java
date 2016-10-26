package com.yoyiyi.honglv.ui.widget.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 *
 */
public class BannerAdapter extends PagerAdapter {

    private List<ImageView> mList;

    private int pos;


    BannerAdapter(List<ImageView> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= mList.size();
        if (position < 0) {
            position = mList.size() + position;
        }
        ImageView image = mList.get(position);
        pos = position;
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        //如果View已经在之前添加到了一个父组件，
        // 则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = image.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(image);
        }
        image.setOnClickListener(click -> {
            if (mList != null) {
                mListener.onItemClick(pos);
            }
        });
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      //  container.removeView((View) object);
    }


    private ViewPagerOnItemClickListener mListener;

    void setViewPagerOnItemClickListener(ViewPagerOnItemClickListener listener) {
        this.mListener = listener;
    }

    interface ViewPagerOnItemClickListener {
        void onItemClick(int position);
    }
}
