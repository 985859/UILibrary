package com.mylibrary.api.utils;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;

/**
 * 2019/12/19
 * user  hukui
 **/
public class ViewChangeUtil {
    private float mPosX;// 手指按下x坐标
    private float mPosY; // 手指按下y坐标
    private float mCurPosX;// 手指滑动x坐标
    private float mCurPosY;// 手指滑动y坐标

    private int height;//view 当前的高度
    private float minHeight = 100;//控件要显示的最小高度
    private float maxHeight = 800;//控件要显示的最大高度
    private float criticalHeight = minHeight;//临界高度
    private long duration = 500;//动画执行时间 默认1秒
    private boolean isMOVE = false;
    private boolean isShow = false;
    private View view;//执行动画的View

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setCriticalHeight(float criticalHeight) {
        this.criticalHeight = criticalHeight;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void setView(final View view) {
        this.view = view;
        if (view == null) {
            return;
        }
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isMOVE = false;
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isMOVE = true;
                        mCurPosY = event.getY();
                        float srcoolY = mCurPosY - mPosY;
                        height = view.getLayoutParams().height;
                        height -= srcoolY;
                        ViewGroup.LayoutParams params = view.getLayoutParams();
                        if (height > maxHeight) {
                            height = (int) maxHeight;
                        } else if (height < minHeight) {
                            height = (int) minHeight;
                        }
                        params.height = height;
                        view.setLayoutParams(params);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isMOVE) {
                            if (mCurPosY - mPosY > 0) {
                                //向下滑动
                                if (height > minHeight) {
                                    float h = maxHeight - criticalHeight;
                                    if (height >= h) {
                                        isShow = true;
                                        changHeight(view, height, (int) maxHeight, 500);
                                    } else {
                                        isShow = false;
                                        changHeight(view, height, (int) minHeight, 500);
                                    }
                                }
                            } else if (mCurPosY - mPosY < 0) {
                                //向上滑动
                                if (height < maxHeight) {
                                    if (height >= criticalHeight + minHeight) {
                                        isShow = true;
                                        changHeight(view, (int) height, (int) maxHeight, 500);
                                    } else {
                                        changHeight(view, (int) height, (int) minHeight, 500);
                                        isShow = false;
                                    }
                                }
                            }
                        } else {
                            if (isShow) {
                                changHeight(view, (int) maxHeight, (int) minHeight, 500);
                                isShow = false;
                            } else {
                                changHeight(view, (int) minHeight, (int) maxHeight, 500);
                                isShow = true;
                            }
                        }

                        break;
                }
                return true;
            }
        });
    }

    /**
     * @param height1  frome height
     * @param height2  to height
     * @param duration 动画时长
     * @return
     * @author: hukui
     * @date: 2019/12/19
     * @description 动态改变view 的高度 的过度动画
     */
    public void changHeight(final View view, int height1, int height2, long duration) {
        ValueAnimator animator = ValueAnimator.ofInt(height1, height2);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (view != null) {
                    ViewGroup.LayoutParams params = view.getLayoutParams();
                    if (value > maxHeight) {
                        value = (int) maxHeight;
                    } else if (value < minHeight) {
                        value = (int) minHeight;
                    }
                    params.height = value;
                    view.setLayoutParams(params);
                }

            }
        });
        animator.start();
    }



    public static void expandTouchArea(View view) {
        expandTouchArea(view, 20);
    }

    public static void expandTouchArea(View view, int size) {
        View parentView = (View) view.getParent();
        parentView.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                view.getHitRect(rect);

                rect.top -= size;
                rect.bottom += size;
                rect.left -= size;
                rect.right += size;

                parentView.setTouchDelegate(new TouchDelegate(rect, view));
            }
        });
    }

}
