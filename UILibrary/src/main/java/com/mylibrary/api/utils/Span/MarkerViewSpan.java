package com.mylibrary.api.utils.Span;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/25 9:45
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MarkerViewSpan extends ReplacementSpan {
    private int mMarginLeft;
    private int mMarginRight;
    protected View view;

    public MarkerViewSpan(View view) {
        super();
        this.view = view;

        this.view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public MarkerViewSpan(View view, int marginLeft, int marginRight) {
        super();
        this.view = view;
        this.view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        if (fm != null) {
            int height = view.getMeasuredHeight();
            fm.ascent = fm.top = -height / 2;
            fm.descent = fm.bottom = height / 2;
        }
        return mMarginLeft + view.getRight() + mMarginRight;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - view.getMeasuredHeight() / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);
        view.draw(canvas);
        canvas.restore();
    }

}
