package com.mylibrary.api.managelayout;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class TopLinearLayoutManager extends LinearLayoutManager {

    public TopLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }


    private float MILLISECONDS_PER_INCH = 0.1f;

    public TopLinearLayoutManager(Context context) {
        super(context);
        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * 0.01f;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return TopLinearLayoutManager.this
                        .computeScrollVectorForPosition(targetPosition);
            }
            @Override
            protected float calculateSpeedPerPixel
            (DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.density;
                //返回滑动一个pixel需要多少毫秒
            }
            //重写这个方法,保证滑动到指定条目的顶部
            @Override
            protected int getVerticalSnapPreference() {
                return SNAP_TO_START;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
