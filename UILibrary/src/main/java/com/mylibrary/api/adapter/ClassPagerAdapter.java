package com.mylibrary.api.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myuser on 2017/11/2.
 */

public class ClassPagerAdapter extends PagerAdapter {

    List<View> views = new ArrayList<>();

    public ClassPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        //获得size
        return views == null ? 0 : views.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position <views.size()) {
            //销毁Item
            (container).removeView(views.get(position));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //实例化Item
        (container).addView(views.get(position));
        return views.get(position);
    }
}




