package com.mylibrary.api.utils;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/1/19 15:48
 */
public class ViewUtil {
    /**
     * @param height1  frome height
     * @param height2  to height
     * @param duration 动画时长
     * @return
     * @author: hukui
     * @date: 2019/12/19
     * @description 动态改变view 的高度 的过度动画
     */
    public static void changHeight(final View view, int height1, int height2, long duration) {
        ValueAnimator animator = ValueAnimator.ofInt(height1, height2);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (view != null) {
                    ViewGroup.LayoutParams params = view.getLayoutParams();
                    params.height = value;
                    view.setLayoutParams(params);
                }

            }
        });
        animator.start();
    }
}
